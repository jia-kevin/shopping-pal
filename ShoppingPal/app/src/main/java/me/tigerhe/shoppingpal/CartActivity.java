package me.tigerhe.shoppingpal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.tigerhe.shoppingpal.adapters.ListAdapter;
import me.tigerhe.shoppingpal.models.AmazonCart;
import me.tigerhe.shoppingpal.models.AmazonProduct;

public class CartActivity extends AppCompatActivity {

    private TextView mCountPrice;

    private Button cameraButton;

    private RecyclerView.LayoutManager mLayoutManager;
    private ListAdapter mAdapter;
    private TextView mEmptyMessage;
    AmazonCart mCart = null;
    AmazonProduct currentProduct = null;

    // list of product names
    private List<AmazonProduct> mProductList = new ArrayList<>();
    int items = 0;
    double price = 0;

    // list view and adapter for data
    private ListView mList;
    final int RC_BARCODE_CAPTURE = 9001;
    final int RC_PRODUCT_DISPLAY = 9002;
    long barcodeValue;
    File path, current, cartlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mCountPrice = (TextView) findViewById(R.id.count_price);
        mEmptyMessage = (TextView) findViewById(R.id.empty_list_message);

        cameraButton = (Button) findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToCamera();
            }
        });

        final TextView quantity = (TextView)findViewById(R.id.quantity);

        Button resetButton = (Button) findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items = 0;
                price = 0;
                mCart = new AmazonCart();

                TextView count = (TextView)findViewById(R.id.count_price);
                count.setText("$0.00 : 0 items");
                mEmptyMessage.setVisibility(View.VISIBLE);
            }
        });

        path = getApplicationContext().getFilesDir();
        current = new File(path, "currentproduct.txt");
        cartlog = new File(path, "cartlog.txt");

        RecyclerView list = (RecyclerView)findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(mLayoutManager);
        resetButton.callOnClick();
        mAdapter = new ListAdapter(this, mCart.getProducts());
        list.setAdapter(mAdapter);
    }

    public void switchToCamera() {
       // mAdapter.updateList(mProductList);
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

                    //prints data to work with
                    Log.d("Product Data", productData);
                    final AmazonProduct retrieved = new AmazonProduct(productData);
//                    if (retrieved.isValid()) retrieved.print();
//                    else Log.d("Retrieved", "Invalid!");
//                    if (retrieved.isValid()){
//                        AmazonCart sample = new AmazonCart(retrieved, 1);
//                        Log.d("Checkout URL", sample.checkout);
//                    }

//                    TextView output = (TextView)findViewById(R.id.data_output);
                    if (retrieved.isValid()){
                        displayObject(retrieved);
//                        output.setText(retrieved.display());
//                        Button addButton = (Button) findViewById(R.id.add_item);
//                        addButton.setEnabled(true);
//                        final TextView quantity = (TextView)findViewById(R.id.quantity);
//                        quantity.setFocusableInTouchMode(true);
//                        quantity.setFocusable(true);
//
//                        addButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                String input = quantity.getText().toString();
//                                try{
//                                    int number = Integer.parseInt(input);
//                                    if (number > 0 && number <= retrieved.getAmount()) {
//                                        Button checkout = (Button) findViewById(R.id.checkout);
//                                        if (mCart == null) {
//                                            mCart = new AmazonCart(retrieved, number);
//                                            checkout.setEnabled(true);
//                                        } else {
//                                            mCart.add(retrieved, number);
//                                        }
//                                        items += number;
//                                        price += number*retrieved.getPrice();
//                                        String outputPrice = new DecimalFormat("#.##").format(price);
//                                        TextView count = (TextView)findViewById(R.id.count_price);
//                                        count.setText("$" + outputPrice + " : "+Integer.toString(items)+" items");
//                                        checkout.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View view) {
//                                                Uri uriUrl = Uri.parse(mCart.checkout);
//                                                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
//                                                startActivity(launchBrowser);
//                                            }
//                                        });
//                                    }
//                                }catch (NumberFormatException e){
//                                    Log.d("Input Error","Input an integer for quantity");
//                                }
//                            }
//                        });
                    }
                    else{
                        Toast.makeText(this, "Error - Could not find the associated product on Amazon", Toast.LENGTH_SHORT).show();
//                        output.setText("Error - Could not find the associated product on Amazon.");
//                        Button addButton = (Button) findViewById(R.id.add_item);
//                        addButton.setEnabled(false);
//                        TextView quantity = (TextView)findViewById(R.id.quantity);
//                        quantity.setText("");
//                        quantity.setFocusable(false);
                    }
                }
            }
        }
        else if (requestCode == RC_PRODUCT_DISPLAY){
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    if (mCart.getProducts().size() > 0) {
                        mEmptyMessage.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    public void displayObject(AmazonProduct product) {
        Intent intent = new Intent(this, DisplayImageActivity.class);
        intent.putExtra("Price", product.getPrice().toString());
        intent.putExtra("Quantity", product.getAmount().toString());
        intent.putExtra("Rating", product.getRating().toString());
        intent.putExtra("Manufacturer", product.getManufacturer());
        intent.putExtra("Imgurl", product.getPicture());
        intent.putExtra("Url", product.getUrl());
        intent.putExtra("Name", product.getName());
        startActivityForResult(intent, RC_PRODUCT_DISPLAY);
    }
}