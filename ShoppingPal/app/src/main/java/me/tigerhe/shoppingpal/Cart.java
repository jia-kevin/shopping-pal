package me.tigerhe.shoppingpal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    private Button mSwitchToCameraButton;

    // list of product names
    private List<String> mProductList;

    // list view and adapter for data
    private ListView mList;
    private ItemListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mSwitchToCameraButton = (Button) findViewById(R.id.camera_button);
        mSwitchToCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToCamera();
            }
        });

        mProductList = new ArrayList<>();
        //**************** temp ************
        mProductList.add("wow");

        mList = (ListView) findViewById(R.id.list);
        mAdapter = new ItemListAdapter(this, mProductList);

    }

    public void switchToCamera() {
        mProductList.add("wow");
        mAdapter.updateList(mProductList);

        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        startActivity(intent);
    }


}