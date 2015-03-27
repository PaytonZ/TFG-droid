package com.bsod.tfg.vista.login;

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
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.modelo.sesion.Token;
import com.bsod.tfg.modelo.sesion.User;
import com.bsod.tfg.modelo.universidades.Facultad;
import com.bsod.tfg.modelo.universidades.FacultadRegistro;
import com.bsod.tfg.modelo.universidades.GenericType;
import com.bsod.tfg.modelo.universidades.ProvinciaRegistro;
import com.bsod.tfg.modelo.universidades.UniversidadRegistro;
import com.bsod.tfg.utils.EmailChecker;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.JsonHttpResponseHandlerCustom;
import com.bsod.tfg.utils.Statistics;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.JsonHttpResponseHandler;
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

                                processJSONData(response, listOfProvincias, ProvinciaRegistro.class);

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

            ProvinciaRegistro p = (ProvinciaRegistro) adapterView.getItemAtPosition(position);
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
                                // ObjectMapper om = new ObjectMapper();
                                //JsonNode o = om.valueToTree(response);
                                processJSONData(response, listOfUniversitys, UniversidadRegistro.class);
                                //new ObjectMapper().readValue(o, Universidad.class);
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
                UniversidadRegistro u = (UniversidadRegistro) adapterView.getItemAtPosition(position);
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
                                    processJSONData(response, listOfFacultys, FacultadRegistro.class);
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
            //JSONObject data = genericObject.getJSONObject("fields");
            generic.setName(genericObject.getString("nombre"));
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
                        editTextPassword.setTextColor(getResources().getColor(R.color.green_test));
                        editTextPassword.setError(null);
                    } else {
                        editTextPassword.setTextColor(Color.RED);
                    }
                }
            }
        } else if (view == editTextUsuario) {
            if (hasFocus) {
                editTextUsuario.setTextColor(Color.BLACK);
            } else {
                if (checkValidity(editTextUsuario)) {
                    editTextUsuario.setTextColor(getResources().getColor(R.color.green_test));
                    editTextUsuario.setError(null);
                } else {
                    editTextUsuario.setTextColor(Color.RED);
                }

            }
        } else if (view == editTextEmail) {
            if (hasFocus) {
                editTextEmail.setTextColor(Color.BLACK);
            } else {
                if (checkValidity(editTextEmail)) {
                    editTextEmail.setTextColor(getResources().getColor(R.color.green_test));
                    editTextUsuario.setError(null);
                } else {
                    editTextEmail.setTextColor(Color.RED);

                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister) {
            //Better be safe than sorry , double check
            if (checkValidity(editTextUsuario) && checkValidity(editTextEmail) && checkValidity(editTextPassword) && checkValidity(spinnerFacultad) && checkValidity(spinnerProvincias)
                    && checkValidity(spinnerUniversidad)) {
                RequestParams params = new RequestParams();
                params.put("user", editTextUsuario.getText().toString());
                params.put("pass", editTextPassword.getText().toString());
                params.put("email", editTextEmail.getText().toString());
                params.put("university", ((GenericType) spinnerUniversidad.getSelectedItem()).getId());
                params.put("faculty", ((GenericType) spinnerFacultad.getSelectedItem()).getId());
                Statistics st = new Statistics();
                params.put("model", st.getDeviceName());
                params.put("displaysize", st.getResolution(this));
                params.put("platform", st.getAndroidVersion());

                HttpClient.get(Constants.HTTP_REGISTER_USER, params, new JsonHttpResponseHandlerCustom(this) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        int error;
                        try {
                            error = Integer.parseInt(response.get("error").toString());
                            if (error == 200) {
                                // TODO: Takes more data from the server and put it here
                                ObjectMapper mapper = new ObjectMapper();
                                //mapper.registerModule(new JsonOrgModule());

                                Token t = mapper.readValue(response.get("token").toString(), Token.class);
                                Facultad facultad = mapper.readValue(response.get("faculty").toString(), Facultad.class);
                                Session.getSession().setUser(mapper.readValue(response.get("user").toString(), User.class));
                                Session.getSession().setToken(t);
                                Session.getSession().setFacultad(facultad);
                                Session.persistPreferences();

                                Intent intent = new Intent(thisactivity, ActivityMain.class);
                                // Closing all the Activities from stack
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                // Add new Flag to start new Activity
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Toast.makeText(thisactivity, R.string.successful_register, Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
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

    /**
     * Comprueba si un atributo es válido según determinadas reglas.
     *
     * @param v Vista a ser comprobada
     * @return Si un elemento es válido para usarse para el registro o no.
     */
    private boolean checkValidity(View v) {
        final boolean[] validity = new boolean[2];

        try {

    /* Comprobación del Campo de Contraseña */
            if (v == editTextPassword) {
                validity[0] = (editTextPassword.getText().length() > 5);
    /* Comprobación del Campo de Usuario */
            } else if (v == editTextUsuario) {
                validity[0] = editTextUsuario.getText().length() > 3;
                if (validity[0]) {
                    RequestParams params = new RequestParams();
                    params.put("user", editTextUsuario.getText().toString());
                    HttpClient.get(Constants.HTTP_CHECK_USER, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            int error;
                            try {
                                error = Integer.parseInt(response.get("error").toString());
                                if (error == 200) {
                                    int available = Integer.parseInt(response.get("available").toString());
                                    validity[1] = (available == 1);
                                    validity[0] = validity[0] && validity[1];
                                    if (!validity[0]) {
                                        editTextUsuario.setTextColor(Color.RED);
                                        editTextUsuario.setError(thisactivity.getString(R.string.already_existing_user));
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            }
    /* Comprobación del Campo de Email */
            else if (v == editTextEmail) {
                validity[0] = editTextEmail.getText().length() > 4 && new EmailChecker().validate(editTextEmail.getText().toString());
                if (validity[0]) {
                    RequestParams params = new RequestParams();
                    params.put("email", editTextEmail.getText().toString());
                    HttpClient.get(Constants.HTTP_CHECK_EMAIL, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            int error;
                            try {
                                error = Integer.parseInt(response.get("error").toString());
                                if (error == 200) {
                                    int available = Integer.parseInt(response.get("available").toString());
                                    validity[1] = (available == 1);
                                    validity[0] = validity[0] && validity[1];
                                    if (!validity[0]) {
                                        editTextEmail.setTextColor(Color.RED);
                                        editTextEmail.setError(thisactivity.getString(R.string.already_existing_email));
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            /* Comprobación del Campos de Provincia - Facultad y Universidad  */
            } else if (v == spinnerUniversidad || v == spinnerProvincias || v == spinnerFacultad) {
                validity[0] = ((GenericType) ((Spinner) v).getSelectedItem()).getId() > 0;
            }

        } catch (Exception e) {
            return false;
        }
        return validity[0];
    }
}




