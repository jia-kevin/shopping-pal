package me.tigerhe.shoppingpal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.amazon.advertising.api.sample.ItemLookupSample;
import com.amazon.advertising.api.sample.SignedRequestsHelper;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
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
    long barcodeValue;

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
        mList.setAdapter(mAdapter);

    }

    public void switchToCamera() {
        mProductList.add("wow");
        mAdapter.updateList(mProductList);
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("Operation","ItemLookup");
        AmazonProduct template = new AmazonProduct();
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
<<<<<<< HEAD
                    barcodeValue = Long.parseLong(barcode.displayValue);
                    map.put("IdType", "UPC");
                    map.put("ItemId", Long.toString(barcodeValue));
                    map.put("SearchIndex", "All");
                    ItemLookupSample test = new ItemLookupSample();
                    SignedRequestsHelper url = new SignedRequestsHelper();
                    final String query = url.sign(map);
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.get(query, new AsyncHttpResponseHandler() {
                        @Override
                        public void onStart() {
                            Log.d("[HTTP REQUEST]", query);
                        }
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                            // called when response HTTP status is "200 OK"
                            Log.d("Success", "asdf");
                            String searchResult = new String(response);
                            Log.d("Output", searchResult);
                            AmazonProduct current = new AmazonProduct(searchResult);
                        }
                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                            Log.d("Failure", Integer.toString(statusCode) );
                        }
                        @Override
                        public void onRetry() {
                            // called when request is retried
                            Log.d("Retry", "asdf");
                        }
                    });
=======

                    barcodeValue = Long.parseLong(barcode.displayValue);
>>>>>>> origin/master
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        Log.d("asdf", AmazonProduct.name);
    }
}