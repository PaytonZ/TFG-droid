package com.bsod.tfg.vista.tablon;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.otros.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ActivityImageUserDetail extends Activity {

    private ImageView userImageDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_user_detail);
        userImageDetail = (ImageView) findViewById(R.id.imageviewUserDetail);
        String stringExtra = getIntent().getStringExtra(Constants.INTENT_USER_IMAGE_DETAIL);
        if (!stringExtra.equals("")) {
            ImageLoader im = ImageLoader.getInstance();
            im.displayImage(Constants.BASE_URL.concat(stringExtra), userImageDetail);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_activity_image_user_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
