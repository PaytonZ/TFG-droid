package com.bsod.tfg.vista.examenes;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.archivos.Asignatura;
import com.bsod.tfg.modelo.archivos.Tema;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.utils.DateManager;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.JsonHttpResponseHandlerCustom;
import com.bsod.tfg.utils.ProgressDialogCustom;
import com.doomonafireball.betterpickers.datepicker.DatePickerBuilder;
import com.doomonafireball.betterpickers.datepicker.DatePickerDialogFragment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gc.materialdesign.views.Button;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUploadFile extends Fragment implements AdapterView.OnItemSelectedListener, DatePickerDialogFragment.DatePickerDialogHandler, View.OnClickListener,
        ImageChooserListener {

    private static final String TAG = "FragmentUploadFile";
    private View rootView;
    private Spinner spinnersubject;
    private Spinner spinnertheme;
    private Context context;
    private FragmentUploadFile thisfragment = this;
    private Button button_selectyearmonth;
    private TextView textViewMonth;
    private TextView textViewYear;
    private int monthSelected;
    private int yearSelected;
    private RadioButton notesradiobutton;
    private RadioButton examenradiobutton;
    private ImageChooserManager imageChooserManager;

    private int chooserType;
    private String filePath;
    private int numberOfImage;

    private ArrayList<ImageView> imageViewArray;
    private ArrayList<Button> buttonViewArray;
    private ArrayList<ChosenImage> chosenImagesArray;
    private Button button_send;
    private EditText edit_description;
    private List<Asignatura> listofSubjects;


    public FragmentUploadFile() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_upload_file, container, false);

        imageViewArray = new ArrayList<>();
        buttonViewArray = new ArrayList<>();
        chosenImagesArray = new ArrayList<>();
        spinnersubject = (Spinner) rootView.findViewById(R.id.spinnersubject);
        spinnertheme = (Spinner) rootView.findViewById(R.id.spinnertheme);
        button_selectyearmonth = (Button) rootView.findViewById(R.id.button_selectyearmonth);
        textViewMonth = (TextView) rootView.findViewById(R.id.textViewMonth);
        textViewYear = (TextView) rootView.findViewById(R.id.textViewYear);
        button_selectyearmonth.setOnClickListener(this);

        Button buttonSelectimage0 = (Button) rootView.findViewById(R.id.button_selectimage);
        buttonSelectimage0.setOnClickListener(this);
        buttonViewArray.add(buttonSelectimage0);

        imageViewArray.add((ImageView) rootView.findViewById(R.id.imageViewThumb));

        notesradiobutton = (RadioButton) rootView.findViewById(R.id.notesradiobutton);
        examenradiobutton = (RadioButton) rootView.findViewById(R.id.examenradiobutton);

        notesradiobutton.setOnClickListener(this);
        examenradiobutton.setOnClickListener(this);

        button_send = (Button) rootView.findViewById(R.id.button_send);
        button_send.setOnClickListener(this);

        edit_description = (EditText) rootView.findViewById(R.id.edit_description);

        context = getActivity();

        if (listofSubjects == null) {

            RequestParams params = new RequestParams();
            params.put("token", Session.getSession().getToken().getToken());
            params.put("idfaculty", Session.getSession().getFacultad().getId());
            HttpClient.get(Constants.HTTP_GET_ALL_SUBJECTS, params, new JsonHttpResponseHandlerCustom(context) {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    try {
                        int error = Integer.parseInt(response.get("error").toString());
                        if (error == 200) {
                            ObjectMapper mapper = new ObjectMapper();
                            listofSubjects = mapper.readValue(
                                    response.get("data").toString(), new TypeReference<List<Asignatura>>() {
                                    });
                            spinnersubject.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listofSubjects));
                            spinnersubject.setOnItemSelectedListener(thisfragment);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            spinnersubject.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listofSubjects));
            spinnersubject.setOnItemSelectedListener(thisfragment);
        }

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView == spinnersubject) {
            Asignatura a = (Asignatura) adapterView.getSelectedItem();
            RequestParams params = new RequestParams();
            params.put("token", Session.getSession().getToken().getToken());
            params.put("subject", a.getId());
            HttpClient.get(Constants.HTTP_GET_THEMES, params, new JsonHttpResponseHandlerCustom(context) {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        int error = Integer.parseInt(response.get("error").toString());
                        if (error == 200) {
                            ObjectMapper mapper = new ObjectMapper();
                            List<Tema> listofThemes = mapper.readValue(
                                    response.get("data").toString(), new TypeReference<List<Tema>>() {
                                    });

                            Tema t = new Tema();
                            t.setId(-1);
                            t.setNombre((listofThemes.size() == 0) ? "No existen temas de esta asignatura" : "Elige un tema");
                            listofThemes.add(0, t);
                            spinnertheme.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listofThemes));
                            spinnertheme.setOnItemSelectedListener(thisfragment);

                        }
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

    @Override
    public void onClick(View view) {
        if (view == button_selectyearmonth) {

            DatePickerBuilder dpb = new DatePickerBuilder()
                    .setFragmentManager(getActivity().getSupportFragmentManager())
                    .setTargetFragment(this)
                    .setStyleResId(R.style.BetterPickersDialogFragment_Light);
            dpb.show();
        } else {
            int size = buttonViewArray.size();
            for (int i = 0; i < size; i++) {
                if (view == buttonViewArray.get(i)) {
                    chooseImage(i);
                }
            }
        }
        if (view == button_send) {
            uploadFile();
        }

    }

    private void uploadFile() {

        RequestParams params = new RequestParams();
        params.put("token", Session.getSession().getToken().getToken());
        ContentResolver cr = getActivity().getContentResolver();
        ChosenImage ci = chosenImagesArray.get(0);

        /*String type = cr.getType(Uri.parse((ci
                .getFilePathOriginal())));*/
        BitmapFactory.Options o = new BitmapFactory.Options();
        Bitmap yourSelectedImage;
        try {
            yourSelectedImage = BitmapFactory.decodeStream(new FileInputStream(new File(ci.getFilePathOriginal()).toString()), null, o);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            yourSelectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            ByteArrayInputStream bs = new ByteArrayInputStream(byteArray);
            params.put("image", bs, "nameholder.jpg", "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        params.put("idsubject", ((Asignatura) spinnersubject.getSelectedItem()).getId());
        params.put("year", yearSelected);
        params.put("month", monthSelected);
        if (spinnertheme.getSelectedItemPosition() > 0) {
            params.put("idtheme", ((Tema) spinnertheme.getSelectedItem()).getid());
        }
        if (!edit_description.getText().toString().equals("")) {
            params.put("description", edit_description.getText().toString());
        }
        params.put("type", notesradiobutton.isChecked() ? "notes" : "exam");
        params.put("lastone", true);
        HttpClient.post(Constants.HTTP_CREATE_DOCUMENT, params, new JsonHttpResponseHandlerCustom(context) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int error = Integer.parseInt(response.getString("error"));
                    if (error == 200) {

                        Toast.makeText(context, "Subida de Archivo Completada Satisfactoriamente", Toast.LENGTH_SHORT).show();
                        if (response.has("token")) {
                            String token = response.getString("token");
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
        Log.i(TAG, "onDialogExpirationSet");
        monthSelected = monthOfYear;
        yearSelected = year;
        textViewMonth.setText(DateManager.monthToString(monthOfYear + 1));
        textViewYear.setText(String.valueOf(year));
    }

    private void chooseImage(int numberOfImage) {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_PICTURE, "myfolder", true);
        imageChooserManager.setImageChooserListener(this);
        this.numberOfImage = numberOfImage;
        try {
            ProgressDialogCustom.makeDialogLoading(context);
            filePath = imageChooserManager.choose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onImageChosen(final ChosenImage chosenImage) {
        Log.d(TAG, "onImageChosen");
        getActivity().runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            //pbar.setVisibility(View.GONE);
                                            if (chosenImage != null) {
                                                chosenImagesArray.add(numberOfImage, chosenImage);
                                                ImageView iv;

                                                iv = imageViewArray.get(numberOfImage);

                                                if (iv != null) {
                                                    //textViewFile.setText(image.getFilePathOriginal());
                                                    iv.setImageURI(Uri.parse(new File(chosenImage
                                                            .getFileThumbnail()).toString()));
                                                    /*iv.setImageURI(Uri.parse(new File(chosenImage
                                                            .getFilePathOriginal()).toString()));*/
                                                }
                                            }
                                            ProgressDialogCustom.dissmissDialog();
                                        }
                                    }

        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        if (resultCode == Activity.RESULT_OK) /*&& (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) */ {
            if (imageChooserManager == null) {
                reinitializeImageChooser();
            }
            // imageChooserManager.submit(requestCode, data);
            imageChooserManager.submit(chooserType, data);
        } else {
            ProgressDialogCustom.dissmissDialog();
        }
    }

    // Should be called if for some reason the ImageChooserManager is null (Due
    // to destroying of activity for low memory situations)

    private void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(this, chooserType,
                "myfolder", true);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.reinitialize(filePath);
    }

    @Override
    public void onError(String s) {

    }


}
