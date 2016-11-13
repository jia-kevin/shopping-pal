package me.tigerhe.shoppingpal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    private Button mSwitchToCameraButton;

    //***
    private Button addItem;

    // list of product names
    private List<String> mProductList;

    // list view and adapter for data
    private ListView mList;
    private ItemListAdapter mAdapter;
    final int RC_BARCODE_CAPTURE = 9001;
    int barcodeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mSwitchToCameraButton = (Button) findViewById(R.id.camera_button);
        mSwitchToCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToCamera();
            }
        });

        //***
        addItem = (Button) findViewById(R.id.add_item);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mProductList.add("wowza");
                    mAdapter.updateList(mProductList);
            }
        });


        mProductList = new ArrayList<>();

        //**************** temp ************
        mProductList.add("wow");

        mList = (ListView) findViewById(R.id.list);
        mAdapter = new ItemListAdapter(this, mProductList);

    }

    public void switchToCamera() {
        mProductList.add("wow");
        mAdapter.updateList(mProductList);
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);

                    //barcodeValue = Integer.parseInt(barcode.displayValue);
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}