package me.tigerhe.shoppingpal;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amazon.advertising.api.sample.ItemLookupSample;
import com.amazon.advertising.api.sample.SignedRequestsHelper;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.IOException;
import java.util.HashMap;

public class Login extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    protected void onClick(View v) throws IOException {
        TextView input = (TextView) findViewById(R.id.editText3);
        TextView output = (TextView) findViewById(R.id.textView);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("ItemId", "B00008OE6I");
        map.put("Operation","ItemLookup");
        ItemLookupSample test = new ItemLookupSample();
        SignedRequestsHelper url = new SignedRequestsHelper();
        String txt = input.getText().toString();
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
/*
        AsyncHttpClient asyncClient = new AsyncHttpClient()
        final String fetchURL = "https://www.amazon.com/Canon-PowerShot-Digital-Camera-Optical/dp/tech-data/B00008OE6I%3FSubscriptionId%3DAKIAIJ6PQBW5JNMUZRRQ%26tag%3Dkzzhang-20%26linkCode%3Dxm2%26camp%3D2025%26creative%3D386001%26creativeASIN%3DB00008OE6I";
        final String encoded = Base64.getEncoder().getBytes(StandardCharsets.UTF_8));
        final String body = asyncClient
                .prepareGet(fetchURL)
                .addHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoded)
                .execute()
                .get()
                .getResponseBody(StandardCharsets.UTF_8.name());
        System.out.println(body);*/

    }
}
