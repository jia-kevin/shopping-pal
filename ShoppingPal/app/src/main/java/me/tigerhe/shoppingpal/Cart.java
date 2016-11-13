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

    OnSwipeTouchListener onSwipeTouchListener;


    private Button mSwitchToCameraButton;

    private ListView mList;
    private ItemListAdapter mAdapter;

    private List<String> mProductList;

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

        mList = (ListView) findViewById(R.id.list);
        mProductList = new ArrayList<>();
        mAdapter = new ItemListAdapter(this, mProductList);



//        onSwipeTouchListener = new OnSwipeTouchListener(Cart.this) {
//            @Override
//            public void onSwipeLeft() {
//                System.exit(0);
//                switchToCamera();
//            }
//        };

    }

    public void switchToCamera() {
        mProductList.add("wow");
        mAdapter.updateList(mProductList);

        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        startActivity(intent);
    }


}