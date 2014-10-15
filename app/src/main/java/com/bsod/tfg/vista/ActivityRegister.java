package com.bsod.tfg.vista;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bsod.tfg.ActivityMain;
import com.bsod.tfg.R;
import com.bsod.tfg.modelo.Constants;
import com.bsod.tfg.modelo.Facultad;
import com.bsod.tfg.modelo.GenericType;
import com.bsod.tfg.modelo.Provincia;
import com.bsod.tfg.modelo.Session;
import com.bsod.tfg.modelo.Universidad;
import com.bsod.tfg.utils.EmailChecker;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.JsonHttpResponseHandlerCustom;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityRegister extends Activity implements AdapterView.OnItemSelectedListener, View.OnFocusChangeListener, View.OnClickListener {

    private static final String TAG = "ActivityRegister";
    private ArrayList<GenericType> listOfProvincias = new ArrayList<GenericType>();
    private Spinner spinnerProvincias;
    private Spinner spinnerUniversidad;
    private Spinner spinnerFacultad;
    private EditText editTextUsuario;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private ActivityRegister thisactivity = this;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrate);

        spinnerProvincias = (Spinner) findViewById(R.id.spinnerlocation);
        spinnerUniversidad = (Spinner) findViewById(R.id.spinneruniversidad);
        spinnerFacultad = (Spinner) findViewById(R.id.spinnerfacultad);

        editTextUsuario = (EditText) findViewById(R.id.register_usuario);
        editTextUsuario.setOnFocusChangeListener(this);

        editTextPassword = (EditText) findViewById(R.id.register_password);
        editTextPassword.setText(getText(R.string.passlenght));
        editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        editTextPassword.setOnFocusChangeListener(this);

        buttonRegister = (Button) findViewById(R.id.registar_button);
        buttonRegister.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.register_email);
        editTextEmail.setOnFocusChangeListener(this);


        RequestParams params = new RequestParams();
        HttpClient.get(Constants.HTTP_GET_PROVINCIAS, params, new JsonHttpResponseHandlerCustom(this) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // If the response is JSONObject instead of expected JSONArray
                        try {

                            int error = Integer.parseInt(response.get("error").toString());
                            if (error == 200) {

                                processJSONData(response, listOfProvincias, Provincia.class);

                                spinnerProvincias.setAdapter(new ArrayAdapter<GenericType>(thisactivity, android.R.layout.simple_spinner_item, listOfProvincias));
                                spinnerProvincias.setOnItemSelectedListener(thisactivity);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                    }
                }

        );


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_registrate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView == spinnerProvincias) {
            //Toast.makeText(this, adapterView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

            Provincia p = (Provincia) adapterView.getItemAtPosition(position);
            if (p.getId() > 0) {
                RequestParams params = new RequestParams();
                params.add("id", p.getId().toString());
                final ArrayList<GenericType> listOfUniversitys = new ArrayList<GenericType>();
                HttpClient.get(Constants.HTTP_GET_UNIS, params, new JsonHttpResponseHandlerCustom(this) {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            int error = Integer.parseInt(response.get("error").toString());

                            if (error == 200) {
                                processJSONData(response, listOfUniversitys, Universidad.class);
                            }
                            spinnerUniversidad.setAdapter(new ArrayAdapter<GenericType>(thisactivity, android.R.layout.simple_spinner_item, listOfUniversitys));
                            spinnerUniversidad.setOnItemSelectedListener(thisactivity);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        } else {

            if (adapterView == spinnerUniversidad) {
                Universidad u = (Universidad) adapterView.getItemAtPosition(position);
                if (u.getId() > 0) {
                    RequestParams params = new RequestParams();
                    params.add("id", u.getId().toString());
                    final ArrayList<GenericType> listOfFacultys = new ArrayList<GenericType>();
                    HttpClient.get(Constants.HTTP_GET_FACULTYS, params, new JsonHttpResponseHandlerCustom(this) {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                int error = Integer.parseInt(response.get("error").toString());

                                if (error == 200) {
                                    processJSONData(response, listOfFacultys, Facultad.class);
                                }
                                spinnerFacultad.setAdapter(new ArrayAdapter<GenericType>(thisactivity, android.R.layout.simple_spinner_item, listOfFacultys));
                                spinnerFacultad.setOnItemSelectedListener(thisactivity);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * Function used to decode the JSON , because Provincia , Universidad and Facultad has id and name
     *
     * @param result
     * @param listOfObjects
     * @throws JSONException
     */
    private void processJSONData(JSONObject result, List<GenericType> listOfObjects, Class<? extends GenericType> obj) throws Exception {

        GenericType generic = obj.newInstance();
        generic.setName(generic.selectOneText());
        generic.setId(-1);
        listOfObjects.add(generic);
        JSONArray genericData = (JSONArray) result.get("data");
        for (int i = 0; i < genericData.length(); i++) {
            generic = obj.newInstance();
            JSONObject genericObject = (JSONObject) genericData.get(i);
            JSONObject data = genericObject.getJSONObject("fields");
            generic.setName(data.getString("nombre"));
            generic.setId(genericObject.getInt("pk"));
            listOfObjects.add(generic);
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (view == editTextPassword) {
            if (hasFocus) {
                if (editTextPassword.getText().toString().equals(getString(R.string.passlenght))) {
                    editTextPassword.setText("");
                    editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            } else {
                if (editTextPassword.getText().toString().equals("")) {
                    editTextPassword.setText(getText(R.string.passlenght));
                    editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    editTextPassword.setTransformationMethod(null);
                } else {
                    if (checkValidity(editTextPassword)) {
                        editTextPassword.setTextColor(Color.GREEN);
                    } else {
                        editTextPassword.setTextColor(Color.RED);
                    }
                }
            }
        } else if (view == editTextUsuario) {
            if (!hasFocus) {
                if (checkValidity(editTextUsuario)) {
                    editTextUsuario.setTextColor(Color.GREEN);
                } else {
                    editTextUsuario.setTextColor(Color.RED);
                }
            }
        } else if (view == editTextEmail) {

            if (!hasFocus) {
                if (checkValidity(editTextEmail))
                    editTextEmail.setTextColor(Color.GREEN);
                else {
                    editTextEmail.setTextColor(Color.RED);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister) {
            //Better be safe than sorry , double check
            Boolean b = checkValidity(editTextUsuario) && checkValidity(editTextEmail) && checkValidity(editTextPassword) && checkValidity(spinnerFacultad) && checkValidity(spinnerProvincias)
                    && checkValidity(spinnerUniversidad);
            if (b) {
                RequestParams params = new RequestParams();
                params.put("user", editTextUsuario.getText().toString());
                params.put("pass", editTextPassword.getText().toString());
                params.put("email", editTextEmail.getText().toString());
                params.put("university", ((GenericType) spinnerUniversidad.getSelectedItem()).getId());
                params.put("faculty", ((GenericType) spinnerFacultad.getSelectedItem()).getId());


                HttpClient.get(Constants.HTTP_REGISTER_USER, params, new JsonHttpResponseHandlerCustom(this) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        int error = 0;
                        try {
                            error = Integer.parseInt(response.get("error").toString());
                            if (error == 200) {
                                // TODO: Takes more data from the server and put it here
                                Session.getSession().setUser(editTextUsuario.getText().toString());
                                Session.getSession().setToken(response.get("token").toString());
                                Universidad i = new Universidad();
                                i.setId(1);
                                i.setName("Unv. Complutensis Madritensis.");
                                Session.getSession().setUniversidad(i);
                                Session.persistPreferences();

                                Intent intent = new Intent(thisactivity, ActivityMain.class);
                                // Closing all the Activities from stack
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                // Add new Flag to start new Activity
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            } else {
                Toast.makeText(this, "Psst! Hay algun parametro que esta mal!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkValidity(View v) {
        Boolean validity = false;

        if (v == editTextPassword) {
            validity = (editTextPassword.getText().length() > 5);

        } else if (v == editTextUsuario) {
            validity = editTextUsuario.getText().length() > 3;
        } else if (v == editTextEmail) {
            validity = editTextEmail.getText().length() > 4 && new EmailChecker().validate(editTextEmail.getText().toString());
        } else if (v == spinnerUniversidad || v == spinnerProvincias || v == spinnerFacultad) {
            validity = ((GenericType) ((Spinner) v).getSelectedItem()).getId() > 0;
        }
        return validity;
    }
}