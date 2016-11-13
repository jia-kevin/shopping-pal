package me.tigerhe.shoppingpal;

/**
 * Created by kzhan_000 on 2016-11-12.
 */

public class AmazonProduct {
    String name;
    Float price;
    Float rating;
    String picture;
    String ASIN;
    String category;
    String manufacturer;
    String url;

    public AmazonProduct(String XmlInput){
        int index1, index2;
        //for name
        index1 = XmlInput.indexOf("<Title>")+7;
        index2 = XmlInput.indexOf("</Title>");
        name = XmlInput.substring(index1,index2);
        index1 = XmlInput.indexOf("<ItemId>")+8;
        index2 = XmlInput.indexOf("</ItemId>");
        ASIN = XmlInput.substring(index1,index2);
        index1 = XmlInput.indexOf("<ProductGroup>")+14;
        index2 = XmlInput.indexOf("</ProductGroup>");
        category = XmlInput.substring(index1,index2);
        index1 = XmlInput.indexOf("<Manufacturer>")+14;
        index2 = XmlInput.indexOf("</Manufacturer>");
        manufacturer = XmlInput.substring(index1,index2);
        index1 = XmlInput.indexOf("<URL>")+5;
        index2 = XmlInput.indexOf("</URL>");
        url = XmlInput.substring(index1,index2);
        /*
        //go to main page to find picture url
        AsyncHttpClient client = new AsyncHttpClient();
        picture = client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                Log.d("[HTTP REQUEST]", url);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                Log.d("Success", "asdf");
                String searchResult = new String(response);
                int first = searchResult
                return image;
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
        });*/
    }
}
