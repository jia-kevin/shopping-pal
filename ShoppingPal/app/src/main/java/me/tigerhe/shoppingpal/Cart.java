package me.tigerhe.shoppingpal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cart extends AppCompatActivity {

    private TextView mCountPrice;

    private Button cameraButton;
    AmazonCart cart = null;

    // list of product names
    private List<AmazonProduct> mProductList = new ArrayList<>();
    int items = 0;
    double price = 0;

    // list view and adapter for data
    private ListView mList;
    private ItemListAdapter mAdapter;
    final int RC_BARCODE_CAPTURE = 9001;
    long barcodeValue;
    File path, current, cartlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mCountPrice = (TextView) findViewById(R.id.count_price);

        cameraButton = (Button) findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToCamera();
            }
        });

        final Button addButton = (Button) findViewById(R.id.add_item);
        addButton.setEnabled(false);
        final Button checkout = (Button) findViewById(R.id.checkout);
        checkout.setEnabled(false);
        final TextView quantity = (TextView)findViewById(R.id.quantity);
        quantity.setText("");
        quantity.setFocusable(false);

        Button resetButton = (Button) findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items = 0;
                price = 0;
                cart = null;
                TextView count = (TextView)findViewById(R.id.count_price);
                count.setText("$0.00 : 0 items");
                addButton.setEnabled(false);
                checkout.setEnabled(false);
                quantity.setText("");
                quantity.setFocusable(false);
                TextView output = (TextView)findViewById(R.id.data_output);
                output.setText("");
            }
        });

        mList = (ListView) findViewById(R.id.list);
        mAdapter = new ItemListAdapter(this, mProductList);
        mList.setAdapter(mAdapter);

        path = getApplicationContext().getFilesDir();
        current = new File(path, "currentproduct.txt");
        cartlog = new File(path, "cartlog.txt");
    }

    public void switchToCamera() {
        mAdapter.updateList(mProductList);
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("Operation","ItemLookup");
        ProductSave saver = new ProductSave(current, map);
        String productData = null;
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    barcodeValue = Long.parseLong(barcode.displayValue);

                    saver.mapAdd("IdType", "UPC");
                    saver.mapAdd("ItemId", Long.toString(barcodeValue));
                    saver.mapAdd("SearchIndex", "All");

                    productData = saver.save();
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

        //prints data to work with
        Log.d("Product Data", productData);
        final AmazonProduct retrieved = new AmazonProduct(productData);
        if (retrieved.isValid()) retrieved.print();
        else Log.d("Retrieved", "Invalid!");
        if (retrieved.isValid()){
            AmazonCart sample = new AmazonCart(retrieved, 1);
            Log.d("Checkout URL", sample.checkout);
        }

        TextView output = (TextView)findViewById(R.id.data_output);
        if (retrieved.isValid()){
            output.setText(retrieved.display());
            Button addButton = (Button) findViewById(R.id.add_item);
            addButton.setEnabled(true);
            final TextView quantity = (TextView)findViewById(R.id.quantity);
            quantity.setFocusableInTouchMode(true);
            quantity.setFocusable(true);

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String input = quantity.getText().toString();
                    try{
                        int number = Integer.parseInt(input);
                        if (number > 0) {
                            Button checkout = (Button) findViewById(R.id.checkout);
                            if (cart == null) {
                                cart = new AmazonCart(retrieved, number);
                                checkout.setEnabled(true);
                            } else {
                                cart.add(retrieved, number);
                            }
                            items += number;
                            price += number*retrieved.getPrice();
                            String outputPrice = new DecimalFormat("#.##").format(price);
                            TextView count = (TextView)findViewById(R.id.count_price);
                            count.setText("$" + outputPrice + " : "+Integer.toString(items)+" items");
                            checkout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Uri uriUrl = Uri.parse(cart.checkout);
                                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                    startActivity(launchBrowser);
                                }
                            });
                        }
                    }catch (NumberFormatException e){
                        Log.d("Input Error","Input an integer for quantity");
                    }
                }
            });
        }
        else{
            output.setText("Error - Could not find the associated product on Amazon.");
            Button addButton = (Button) findViewById(R.id.add_item);
            addButton.setEnabled(false);
            TextView quantity = (TextView)findViewById(R.id.quantity);
            quantity.setText("");
            quantity.setFocusable(false);
        }
    }
}