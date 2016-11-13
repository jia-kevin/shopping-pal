package me.tigerhe.shoppingpal;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amazon.advertising.api.sample.ItemLookupSample;
import com.amazon.advertising.api.sample.SignedRequestsHelper;

import java.util.HashMap;

import org.w3c.dom.Text;

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

    protected void testQuery(View v) {
        TextView editTest = (TextView) findViewById(R.id.emailbox);


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("ItemID", "B00008OE6I");
        map.put("Operation", "ItemLookup");

        AWS4Signer test = new AWS4Signer();
        test.setRegionName("US");
        test.setServiceName("AWSECommerceService");
        BasicAWSCredentials cred = new BasicAWSCredentials(login, keypassword); //fill in with AWS credentials

        AmazonWebServiceRequest amazonWebServiceRequest = new AmazonWebServiceRequest() {
        };

        Request request = new DefaultRequest(amazonWebServiceRequest, "AWSECommerceService");
        request.setHttpMethod(HttpMethodName.GET);
        request.setParameters(map);

        test.sign(request, cred);

        editTest.setText("asdf");
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
    protected void onClick(View v){
        TextView input = (TextView) findViewById(R.id.editText);
        TextView output = (TextView) findViewById(R.id.textView);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("ItemId", "B00008OE6I");
        map.put("Operation","ItemLookup");
        ItemLookupSample test = new ItemLookupSample();
        SignedRequestsHelper url = new SignedRequestsHelper();
        String txt = input.getText().toString();
        output.setText(url.sign(map));
    }
}
