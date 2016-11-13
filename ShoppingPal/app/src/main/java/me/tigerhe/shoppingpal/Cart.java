package me.tigerhe.shoppingpal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

    private TextView mCountPrice;

    private Button mSwitchToCameraButton;

    // list of product names
    private List<Product> mProductList = new ArrayList<>();

    // list view and adapter for data
    private ListView mList;
    private ItemListAdapter mAdapter;
    final int RC_BARCODE_CAPTURE = 9001;
    String barcodeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mCountPrice = (TextView) findViewById(R.id.count_price);

        mSwitchToCameraButton = (Button) findViewById(R.id.camera_button);
        mSwitchToCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToCamera();
            }
        });

        mList = (ListView) findViewById(R.id.list);
        mAdapter = new ItemListAdapter(this, mProductList);
        mList.setAdapter(mAdapter);

    }

    public void switchToCamera() {
        mAdapter.updateList(mProductList);
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("Operation","ItemLookup");
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    barcodeValue = barcode.displayValue;
                    map.put("IdType", "UPC");
                    map.put("ItemId", barcodeValue);
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
                            AmazonProduct.setXml(searchResult);
                            Log.d("values", AmazonProduct.name);
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
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        Log.d("asdf", AmazonProduct.name);
    }



}