package me.tigerhe.shoppingpal;

import android.util.Log;

import com.amazon.advertising.api.sample.SignedRequestsHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;

/**
 * Created by kzhan_000 on 2016-12-30.
 */

public class AmazonCart {
    String checkout, cartID, HMAC;
    boolean status = true;

    public AmazonCart(AmazonProduct input, Integer quantity){
        final AmazonProduct product = input;
        final Integer number = quantity;
        Thread thread = new Thread(){
            public void run(){
                try{
                    createCart(product, number);
                    while(status == true) {
                        Thread.sleep(200);
                    }
                }catch (InterruptedException e){
                    Log.d("Interrupted", e.toString());
                }
            }
        };
        thread.start();
        try{
            thread.join();
            status = true;
        }catch (InterruptedException e){
            Log.d("Interrupted", e.toString());
        }
    }

    protected void createCart(AmazonProduct input, Integer quantity){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("Operation", "CartCreate");
        map.put("Item.1.ASIN", input.ASIN);
        map.put("Item.1.Quantity", quantity.toString());

        SignedRequestsHelper url = new SignedRequestsHelper();
        final String query = url.sign(map);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(query, new AsyncHttpResponseHandler(){
            @Override
            public void onStart() {
                Log.d("Starting", "create");
                //Log.d("[HTTP REQUEST]", query);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                Log.d("Success", "create");
                String searchResult = new String(response);
                Log.d("Create Cart", searchResult);
                int index1, index2;
                index1 = searchResult.indexOf("<PurchaseURL>")+13;
                index2 = searchResult.indexOf("</PurchaseURL>");
                checkout = searchResult.substring(index1, index2);
                index1 = searchResult.indexOf("<CartId>")+8;
                index2 = searchResult.indexOf("</CartId>");
                cartID = searchResult.substring(index1, index2);
                index1 = searchResult.indexOf("<HMAC>")+6;
                index2 = searchResult.indexOf("</HMAC>");
                HMAC = searchResult.substring(index1, index2);
                status = false;
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
    }

    public void add(AmazonProduct input, Integer quantity){
        final AmazonProduct product = input;
        final Integer number = quantity;
        Thread thread = new Thread(){
            public void run(){
                try{
                    addProduct(product, number);
                    while(status == true) {
                        Thread.sleep(200);
                    }
                }catch (InterruptedException e){
                    Log.d("Interrupted", e.toString());
                }
            }
        };
        thread.start();
        try{
            thread.join();
            status = true;
        }catch (InterruptedException e){
            Log.d("Interrupted", e.toString());
        }
    }

    protected void addProduct(AmazonProduct input, Integer quantity) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("CartId", cartID);
        map.put("HMAC", HMAC);
        map.put("Operation", "CartAdd");
        map.put("Item.1.ASIN", input.ASIN);
        map.put("Item.1.Quantity", quantity.toString());

        SignedRequestsHelper url = new SignedRequestsHelper();
        final String query = url.sign(map);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(query, new AsyncHttpResponseHandler(){
            @Override
            public void onStart() {
                Log.d("Starting", "add");
                //Log.d("[HTTP REQUEST]", query);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                Log.d("Success", "add");
                String searchResult = new String(response);
                Log.d("add", searchResult);
                int index1, index2;
                index1 = searchResult.indexOf("<PurchaseURL>")+13;
                index2 = searchResult.indexOf("</PurchaseURL>");
                checkout = searchResult.substring(index1, index2);
                status = false;
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
    }

}
