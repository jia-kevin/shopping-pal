package me.tigerhe.shoppingpal;

import android.support.v7.app.AppCompatActivity;

public class Login extends AppCompatActivity {
//    /**
//     * ATTENTION: This was auto-generated to implement the App Indexing API.
//     * See https://g.co/AppIndexing/AndroidStudio for more information.
//     */
//    private GoogleApiClient client;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
//    }
//
//    /**
//     * ATTENTION: This was auto-generated to implement the App Indexing API.
//     * See https://g.co/AppIndexing/AndroidStudio for more information.
//     */
//    public Action getIndexApiAction() {
//        Thing object = new Thing.Builder()
//                .setName("Login Page") // TODO: Define a title for the content shown.
//                // TODO: Make sure this auto-generated URL is correct.
//                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
//                .build();
//        return new Action.Builder(Action.TYPE_VIEW)
//                .setObject(object)
//                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
//                .build();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        AppIndex.AppIndexApi.start(client, getIndexApiAction());
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        AppIndex.AppIndexApi.end(client, getIndexApiAction());
//        client.disconnect();
//    }

//    protected void onClick(View v) throws IOException {
//        TextView input = (TextView) findViewById(R.id.editText3);
//        TextView output = (TextView) findViewById(R.id.textView);
//
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("ItemId", "B00008OE6I");
//        map.put("Operation","ItemLookup");
//        ItemLookupSample test = new ItemLookupSample();
//        SignedRequestsHelper url = new SignedRequestsHelper();
//        String txt = input.getText().toString();
//        final String query = url.sign(map);
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(query, new AsyncHttpResponseHandler() {
//            @Override
//            public void onStart() {
//                Log.d("[HTTP REQUEST]", query);
//            }
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//                // called when response HTTP status is "200 OK"
//                Log.d("Success", "asdf");
//                String searchResult = new String(response);
//                AmazonProduct cannon = new AmazonProduct(searchResult);
//            }
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//                Log.d("Failure", Integer.toString(statusCode) );
//            }
//            @Override
//            public void onRetry() {
//                // called when request is retried
//                Log.d("Retry", "asdf");
//            }
//        });

//    }
}
