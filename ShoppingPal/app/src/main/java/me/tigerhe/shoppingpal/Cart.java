package me.tigerhe.shoppingpal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.amazon.advertising.api.sample.SignedRequestsHelper;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    long barcodeValue;
    File path, current, cartlog;
    FileOutputStream fout, fin;

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
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    barcodeValue = Long.parseLong(barcode.displayValue);
                    try {
                        fout = new FileOutputStream(current, false);
                        fout.flush();
                        map.put("IdType", "UPC");
                        map.put("ItemId", Long.toString(barcodeValue));
                        map.put("SearchIndex", "All");
                        SignedRequestsHelper url = new SignedRequestsHelper();
                        final String query = url.sign(map);
                        AsyncHttpClient client = new AsyncHttpClient();
                        client.get(query, new AsyncHttpResponseHandler() {
                            @Override
                            public void onStart() {
                                //Log.d("[HTTP REQUEST]", query);
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                                // called when response HTTP status is "200 OK"
                                int index1, index2;
                                String name, price, rating, ASIN, category, manufacturer, producturl;
                                Log.d("Success", "first");
                                String searchResult = new String(response);

                                index1 = searchResult.indexOf("<Title>")+7;
                                index2 = searchResult.indexOf("</Title>");
                                name = searchResult.substring(index1,index2);
                                index1 = searchResult.indexOf("<ItemId>")+8;
                                index2 = searchResult.indexOf("</ItemId>");
                                ASIN = searchResult.substring(index1,index2);
                                index1 = searchResult.indexOf("<ProductGroup>")+14;
                                index2 = searchResult.indexOf("</ProductGroup>");
                                category = searchResult.substring(index1,index2);
                                index1 = searchResult.indexOf("<Manufacturer>")+14;
                                index2 = searchResult.indexOf("</Manufacturer>");
                                manufacturer = searchResult.substring(index1,index2);
                                index1 = searchResult.indexOf("<DetailPageURL>")+15;
                                index2 = searchResult.indexOf("</DetailPageURL>");
                                producturl = searchResult.substring(index1, index2);
                                index1 = searchResult.indexOf("All Customer Reviews</Description><URL>")+39;
                                String temp = searchResult.substring(index1);
                                index2 = temp.indexOf("</URL>");
                                rating = temp.substring(0, index2);
                                AsyncHttpClient newClient = new AsyncHttpClient();
                                //find star rating
                                newClient.get(rating, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onStart() {
                                        //Log.d("[HTTP REQUEST]", query);
                                    }
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                                        // called when response HTTP status is "200 OK"
                                        Log.d("Success", "second");
                                        int index1, index2;
                                        String searchResult = new String(response);
                                        String stars;
                                        index1 = searchResult.indexOf("Average Customer Rating:&nbsp;(")+31;
                                        index2 = index1+3;
                                        stars = searchResult.substring(index1, index2);
                                        try{
                                            fout.write(("Rating of product "+stars).getBytes());
                                            fout.write(("\n").getBytes());
                                        }catch (IOException a){
                                            Log.d("Writing IO Exception", a.toString());
                                        }
                                    }
                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                                        Log.d("Failure", Integer.toString(statusCode));
                                    }
                                    @Override
                                    public void onRetry() {
                                        // called when request is retried
                                        Log.d("Retry", "asdf");
                                    }
                                });

                                try{
                                    fout.write(("Name of product "+name).getBytes());
                                    fout.write(("\n").getBytes());
                                    fout.write(("ASIN of product "+ASIN).getBytes());
                                    fout.write(("\n").getBytes());
                                    fout.write(("Category of product "+category).getBytes());
                                    fout.write(("\n").getBytes());
                                    fout.write(("Manufacturer of product "+manufacturer).getBytes());
                                    fout.write(("\n").getBytes());
                                    fout.write(("Url of product "+producturl).getBytes());
                                    fout.write(("\n").getBytes());
                                }catch (IOException a){
                                    Log.d("Writing IO exception", a.toString());
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                                Log.d("Failure", Integer.toString(statusCode));
                            }

                            @Override
                            public void onRetry() {
                                // called when request is retried
                                Log.d("Retry", "asdf");
                            }
                        });
                        map.put("ResponseGroup", "Offers");
                        final String pricequery = url.sign(map);
                        client.get(pricequery, new AsyncHttpResponseHandler() {
                            @Override
                            public void onStart() {
                                //Log.d("[HTTP REQUEST]", pricequery);
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                                // called when response HTTP status is "200 OK"
                                int index1, index2;
                                String price, amount;
                                Log.d("Success", "third");
                                String searchResult = new String(response);
                                index1 = searchResult.indexOf("<TotalNew>")+10;
                                index2 = searchResult.indexOf("</TotalNew>");
                                amount = searchResult.substring(index1, index2);
                                if (Integer.valueOf(amount)==0){
                                    amount = "0";
                                    price = "0";
                                }
                                else{
                                    index1 = searchResult.indexOf("<LowestNewPrice>");
                                    String temp = searchResult.substring(index1);
                                    index1 = temp.indexOf("<Amount>");
                                    index2 = temp.indexOf("</Amount>");
                                    amount = temp.substring(index1, index2);
                                    index1 = temp.indexOf("<FormattedPrice>$") + 17;
                                    index2 = temp.indexOf("</FormattedPrice>");
                                    price = temp.substring(index1, index2);
                                }

                                try{
                                    fout.write(("Price of product "+price).getBytes());
                                    fout.write(("\n").getBytes());
                                    fout.write(("Amount of product "+amount).getBytes());
                                    fout.write(("\n").getBytes());
                                }catch (IOException a){
                                    Log.d("Writing IO exception", a.toString());
                                }
                            }
                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                                Log.d("Failure", Integer.toString(statusCode));
                            }
                            @Override
                            public void onRetry() {
                                // called when request is retried
                                Log.d("Retry", "asdf");
                            }
                        });

                        map.remove("ResponseGroup");
                        map.put("ResponseGroup", "Images");
                        final String imgurlquery = url.sign(map);
                        client.get(imgurlquery, new AsyncHttpResponseHandler() {
                            @Override
                            public void onStart() {
                                //Log.d("[HTTP REQUEST]", imgurlquery);
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                                // called when response HTTP status is "200 OK"
                                int index1, index2;
                                String imgurl;
                                Log.d("Success", "fourth");
                                String searchResult = new String(response);
                                index1 = searchResult.indexOf("<MediumImage><URL>")+18;
                                String temp = searchResult.substring(index1);
                                index2 = temp.indexOf("</URL>");
                                imgurl = temp.substring(0, index2);
                                try{
                                    fout.write(("Imgurl of product "+imgurl).getBytes());
                                    fout.write(("\n").getBytes());
                                }catch (IOException a){
                                    Log.d("Write to file", "IO exception.");
                                }
                            }
                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                                Log.d("Failure", Integer.toString(statusCode));
                            }
                            @Override
                            public void onRetry() {
                                // called when request is retried
                                Log.d("Retry", "asdf");
                            }
                        });
                    }catch (FileNotFoundException e){
                        Log.d("Writing FNF Exception", e.toString());
                    }catch (IOException d){
                        Log.d("Writing IO Exception", d.toString());
                    }finally {
                        Log.d("asdf", "asdf");
                    }
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        /*
        try {
            Log.d("closing", "closing wtf");
            fout.flush();
            fout.close();
        }catch (IOException e){
            Log.d("Closing error", e.toString());
        }*/
    }

}