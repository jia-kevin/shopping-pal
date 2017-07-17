package me.tigerhe.shoppingpal;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.squareup.picasso.Picasso;

/**
 * Created by kzhan_000 on 2017-03-25.
 */

public final class DisplayImageActivity extends AppCompatActivity{

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_display_product);
        final Intent intent = getIntent();
        ImageView picture = (ImageView)findViewById(R.id.imgbox);
        Button backButton = (Button) findViewById(R.id.backToTitle);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Button addButton = (Button) findViewById(R.id.add_product);
        final EditText editText = (EditText) findViewById(R.id.numOrder);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = String.valueOf(editText.getText());
                if (input == null || input.equals("")) {
                    Toast.makeText(mContext, "Enter a quantity!", Toast.LENGTH_SHORT).show();
                } else {
                    int quantity = Integer.parseInt(input);
                    if (quantity > Integer.parseInt(intent.getStringExtra("Quantity"))) {
                        Toast.makeText(mContext, "Quantity unavailable!", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent data = new Intent();
                        data.putExtra("Quantity", quantity);
                        setResult(CommonStatusCodes.SUCCESS, data);
                        finish();
                    }
                }
            }
        });
        Picasso.with(this)
                .load(intent.getStringExtra("Imgurl"))
                .placeholder(R.drawable.logo_square)
                .error(R.drawable.logo_square)
                .fit()
                .centerInside()
                .into(picture);
        TextView title = (TextView)findViewById(R.id.productTitle);
        title.setText(intent.getStringExtra("Name"));
        TextView price = (TextView)findViewById(R.id.price);
        price.setText("Price: " + intent.getStringExtra("Price"));
        TextView rating = (TextView)findViewById(R.id.rating);
        rating.setText("Rating: " + intent.getStringExtra("Rating") + " / 5");
        TextView manufacturer = (TextView)findViewById(R.id.manufacturer);
        manufacturer.setText("Manufacturer: " + intent.getStringExtra("Manufacturer"));
        TextView quantity = (TextView)findViewById(R.id.quantity);
        quantity.setText("Amount Available: " + intent.getStringExtra("Quantity"));

        TextView url = (TextView)findViewById(R.id.url);
        //url.setText("More Information: " + intent.getStringExtra("Url"));
        if (Build.VERSION.SDK_INT >= 24) {
            url.setText(Html.fromHtml(
                    "<a href=\""+intent.getStringExtra("Url")+"\">More Information</a>", Html.FROM_HTML_MODE_LEGACY));
        }
        else {
            url.setText(Html.fromHtml(
                    "<a href=\""+intent.getStringExtra("Url")+"\">More Information</a>"));
        }
        url.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
