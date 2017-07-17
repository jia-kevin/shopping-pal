package me.tigerhe.shoppingpal;

import java.util.ArrayList;

import me.tigerhe.shoppingpal.models.AmazonCart;
import me.tigerhe.shoppingpal.models.AmazonProduct;

/**
 * Created by kevin on 2017-07-13.
 */

public class CartManagerSingleton extends AmazonCart {

    private static CartManagerSingleton instance;

    public static CartManagerSingleton getInstance() {
        if (instance == null) {
            synchronized (CartManagerSingleton.class) {
                if (instance == null) {
                    instance = new CartManagerSingleton();
                }
            }
        }
        return instance;
    }

    private CartManagerSingleton() {
        super();
    }
}
