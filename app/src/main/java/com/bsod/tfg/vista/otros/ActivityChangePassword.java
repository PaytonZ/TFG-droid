package com.bsod.tfg.vista.otros;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.JsonHttpResponseHandlerCustom;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class ActivityChangePassword extends Activity implements View.OnClickListener {

    private TextView oldpassword;
    private TextView newpassword1;
    private TextView newpassword2;
    private Button changePassword;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        oldpassword = (TextView) findViewById(R.id.old_password);
        newpassword1 = (TextView) findViewById(R.id.new_password_1);
        newpassword2 = (TextView) findViewById(R.id.new_password_2);
        changePassword = (Button) findViewById(R.id.change_password_button);
        changePassword.setOnClickListener(this);
        context = this;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_activity_change_password, menu);
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

    @Override
    public void onClick(View view) {
        if (view == changePassword) {

            if (newpassword1.getText().length() > 6 && newpassword1.getText().equals(newpassword2.getText())) {

                RequestParams params = new RequestParams();
                params.put("token", Session.getSession().getToken().getToken());
                params.put("oldpass", oldpassword.getText());
                params.put("newpass", newpassword1.getText());

                HttpClient.get(Constants.HTTP_CHANGE_PASSWORD, params, new JsonHttpResponseHandlerCustom(this) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        int error;
                        try {
                            error = Integer.parseInt(response.get("error").toString());
                            if (error == 200) {
                                Toast.makeText(context, "Cambio de contraseña satisfactorio", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(context, "¡ Cambio de contraseña ERRÓNEO! ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


            } else {

                Toast.makeText(this, R.string.register_bad_parameters, Toast.LENGTH_SHORT).show();

            }


        }
    }
}
