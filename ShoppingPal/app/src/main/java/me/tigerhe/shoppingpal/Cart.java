package me.tigerhe.shoppingpal;

import android.content.Intent;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cart extends AppCompatActivity {

    private TextView mCountPrice;

    private Button cameraButton;

    // list of product names
    private List<Product> mProductList = new ArrayList<>();

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
        AmazonProduct retrieved = new AmazonProduct(productData);
        if (retrieved.isValid()) retrieved.print();
        else Log.d("Retrieved", "Invalid!");
    }

}