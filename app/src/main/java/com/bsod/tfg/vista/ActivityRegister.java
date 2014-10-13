package com.bsod.tfg.vista;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.Constants;
import com.bsod.tfg.modelo.GenericType;
import com.bsod.tfg.modelo.Provincia;
import com.bsod.tfg.modelo.Universidad;
import com.bsod.tfg.utils.HttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityRegister extends Activity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "ActivityRegister";
    private ArrayList<GenericType> listOfProvincias = new ArrayList<GenericType>();
    private Spinner spinnerProvincias;
    private Spinner spinnerUniversidad;
    private Spinner spinnerFacultad;
    private ActivityRegister thisactivity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrate);

        spinnerProvincias = (Spinner) findViewById(R.id.spinnerlocation);
        spinnerUniversidad = (Spinner) findViewById(R.id.spinneruniversidad);
        spinnerFacultad = (Spinner) findViewById(R.id.spinnerfacultad);


        RequestParams params = new RequestParams();
        HttpClient.get(Constants.HTTP_GET_PROVINCIAS, params, new JsonHttpResponseHandler() {
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
            RequestParams params = new RequestParams();
            Provincia p = (Provincia) adapterView.getItemAtPosition(position);
            params.add("id", p.getId().toString());
            final ArrayList<GenericType> listOfUniversitys = new ArrayList<GenericType>();
            HttpClient.get(Constants.HTTP_GET_UNIS, params, new JsonHttpResponseHandler() {
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
    private void processJSONData(JSONObject result, List<GenericType> listOfObjects, Class <? extends GenericType> obj) throws Exception {

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

}
