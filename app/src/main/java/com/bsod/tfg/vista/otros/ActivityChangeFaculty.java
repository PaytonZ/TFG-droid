package com.bsod.tfg.vista.otros;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.modelo.universidades.FacultadRegistro;
import com.bsod.tfg.modelo.universidades.GenericType;
import com.bsod.tfg.modelo.universidades.ProvinciaRegistro;
import com.bsod.tfg.modelo.universidades.UniversidadRegistro;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.JsonHttpResponseHandlerCustom;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityChangeFaculty extends Activity implements AdapterView.OnItemSelectedListener, View.OnFocusChangeListener, View.OnClickListener {

    private Spinner spinnerProvincias;
    private Spinner spinnerUniversidad;
    private Spinner spinnerFacultad;
    private ActivityChangeFaculty thisactivity = this;
    private ArrayList<GenericType> listOfProvincias = new ArrayList<GenericType>();
    private EditText actualPassword;
    private Button buttonChangeFaculty;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_faculty);

        spinnerProvincias = (Spinner) findViewById(R.id.spinnerlocation);
        spinnerUniversidad = (Spinner) findViewById(R.id.spinneruniversidad);
        spinnerFacultad = (Spinner) findViewById(R.id.spinnerfacultad);
        actualPassword = (EditText) findViewById(R.id.change_faculty_password);
        buttonChangeFaculty = (Button) findViewById(R.id.change_faculty_button);
        buttonChangeFaculty.setOnClickListener(this);

        context = this;

        RequestParams params = new RequestParams();
        HttpClient.get(Constants.HTTP_GET_PROVINCIAS, params, new JsonHttpResponseHandlerCustom(this) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // If the response is JSONObject instead of expected JSONArray
                        try {

                            int error = Integer.parseInt(response.get("error").toString());
                            if (error == 200) {

                                processJSONData(response, listOfProvincias, ProvinciaRegistro.class);
                                spinnerProvincias.setAdapter(new ArrayAdapter<>(thisactivity, android.R.layout.simple_spinner_item, listOfProvincias));
                                spinnerProvincias.setOnItemSelectedListener(thisactivity);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }

        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_activity_change_faculty, menu);
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

    /**
     * Function used to decode the JSON , because Provincia , Universidad and Facultad has id and name
     *
     * @param result
     * @param listOfObjects
     * @throws org.json.JSONException
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
                            spinnerUniversidad.setAdapter(new ArrayAdapter<>(thisactivity, android.R.layout.simple_spinner_item, listOfUniversitys));
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
                    final ArrayList<GenericType> listOfFacultys = new ArrayList<>();
                    HttpClient.get(Constants.HTTP_GET_FACULTYS, params, new JsonHttpResponseHandlerCustom(this) {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                int error = Integer.parseInt(response.get("error").toString());

                                if (error == 200) {
                                    processJSONData(response, listOfFacultys, FacultadRegistro.class);
                                }
                                spinnerFacultad.setAdapter(new ArrayAdapter<>(thisactivity, android.R.layout.simple_spinner_item, listOfFacultys));
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

    @Override
    public void onClick(View view) {
        if (view == buttonChangeFaculty) {

            String password = actualPassword.getText().toString();
            int idFacultad = ((FacultadRegistro) spinnerFacultad.getSelectedItem()).getId();
            if (password.length() > 0 && idFacultad > 0) {

                RequestParams params = new RequestParams();
                params.put("token", Session.getSession().getToken().getToken());
                params.put("pass", password);
                params.put("idnewfaculty", idFacultad);
                HttpClient.get(Constants.HTTP_CHANGE_FACULTY, params, new JsonHttpResponseHandlerCustom(this) {
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            int error = Integer.parseInt(response.get("error").toString());

                            if (error == 200) {
                                Toast.makeText(context, getString(R.string.change_faculty_satisfactory), Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 1000);
                            } else {
                                Toast.makeText(context, R.string.register_bad_parameters, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(context, R.string.register_bad_parameters, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }
}
