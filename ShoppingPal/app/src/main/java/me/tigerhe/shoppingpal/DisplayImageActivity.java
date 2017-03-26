package me.tigerhe.shoppingpal;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by kzhan_000 on 2017-03-25.
 */

public final class DisplayImageActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_display_product);
        Intent intent = getIntent();
        ImageView picture = (ImageView)findViewById(R.id.imgbox);
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
