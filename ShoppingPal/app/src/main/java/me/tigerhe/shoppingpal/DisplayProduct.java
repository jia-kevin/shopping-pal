package me.tigerhe.shoppingpal;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DisplayProduct extends AppCompatActivity {

    public String name;
    public double price;
    public double rating;
    public Image picture;

    private Button addButton;
    private Button discardButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product);


        addButton = (Button) findViewById(R.id.add_item);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void goCartEmpty() {
        Intent intent = new Intent(this, Cart.class);
        startActivity(intent);
    }

    public void getGoCartFull() {

    }

    public void initialize() {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.picture = picture;
    }

}
