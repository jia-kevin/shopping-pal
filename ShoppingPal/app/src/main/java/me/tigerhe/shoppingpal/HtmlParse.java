package me.tigerhe.shoppingpal;

import android.util.Log;

/**
 * Created by cathywang on 2016-11-12.
 */

public class HtmlParse {
    float rating, price;

    public HtmlParse(String Reviews){
        String SubStr1 = new String("Average Customer Rating:");
        int index1 = Reviews.indexOf(SubStr1) + 31;
        int index2 = index1 + 3;
        String ratingTemp = Reviews.substring(index1, index2);
        rating = Float.valueOf(ratingTemp);
        Log.d("output rating", ratingTemp);
    }
}
