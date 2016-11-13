package me.tigerhe.shoppingpal;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        Log.d("asdf", url.sign(map));
    }
}
