package com.bsod.tfg.vista.otros;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.utils.HttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class FragmentSettings extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private static final int IMAGE_SELECTED = 200;
    private static final String TAG = "FragmentSettings";
    private Preference changeImage;
    private Preference changePassword;
    private Preference changeFaculty;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        changeImage = findPreference("change_image");
        changeImage.setOnPreferenceClickListener(this);

        changePassword = findPreference("change_password");
        changePassword.setOnPreferenceClickListener(this);

        changeFaculty = findPreference("change_faculty");
        changeFaculty.setOnPreferenceClickListener(this);


    }


    public void onActivityResult(int requestCode, int resultCode,
                                 Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case IMAGE_SELECTED:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {

                        ContentResolver cR = getActivity().getContentResolver();
                        String type = cR.getType(selectedImage);

                        Bitmap yourSelectedImage = decodeUri(selectedImage);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        yourSelectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                        byte[] byteArray = stream.toByteArray();
                        ByteArrayInputStream bs = new ByteArrayInputStream(byteArray);
                        RequestParams params = new RequestParams();
                        params.put("token", Session.getSession().getToken().getToken());
                        params.put("image", bs, "nameholder.jpg", type);

                        HttpClient.post(Constants.HTTP_UPLOAD_IMAGE, params, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                // If the response is JSONObject instead of expected JSONArray
                                try {
                                    Log.i(TAG, response.toString());
                                    int error = Integer.parseInt(response.get("error").toString());
                                    if (error == 200) {
                                        Toast.makeText(getActivity(), getString(R.string.image_uploaded_succesful), Toast.LENGTH_SHORT).show();
                                        //TODO: Cuando la cache funcione bien , quitar eso
                                        ImageLoader.getInstance().clearMemoryCache();
                                        ImageLoader.getInstance().clearDiskCache();
                                    } else {
                                        Toast.makeText(getActivity(), getString(R.string.error_image_upload), Toast.LENGTH_SHORT).show();
                                    }


                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), getString(R.string.error_image_upload), Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
                    } catch (Exception e) {

                    }
                }
        }

    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 200;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o2);

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference == changeImage) {
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType("image/*");
            startActivityForResult(i, IMAGE_SELECTED);
        } else if (preference == changePassword) {
            Intent i = new Intent(getActivity(), ActivityChangePassword.class);
            startActivityForResult(i, Constants.INTENT_CHANGE_PASSWORD);
        } else if (preference == changeFaculty) {
            Intent i = new Intent(getActivity(), ActivityChangeFaculty.class);
            startActivity(i);
        }
        return true;
    }
}
