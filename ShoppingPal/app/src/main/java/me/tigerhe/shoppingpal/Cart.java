package me.tigerhe.shoppingpal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Cart extends AppCompatActivity {

    // OnSwipeTouchListener onSwipeTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        /*
        onSwipeTouchListener = new OnSwipeTouchListener(Cart.this) {
            @Override
            public void onSwipeLeft() {
                System.exit(0);
                switchToCamera();
            }
        };

        onSwipeTouchListener.onSwipeLeft();
        */

    }

    public void switchToCamera() {
        Intent intent = new Intent(this, Camera.class);
        startActivity(intent);
    }


}