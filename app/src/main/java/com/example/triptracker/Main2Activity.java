package com.example.triptracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Main2Activity extends AppCompatActivity {


    public void setTextinFile(String text){
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput("memories.txt", Context.MODE_APPEND);
            outputStream.write(text.getBytes());
            outputStream.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private FusedLocationProviderClient fusedLocationClient;
    private EditText mSearchText;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSearchText = findViewById(R.id.textInputLayout2);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                Toast.makeText(Main2Activity.this, location.toString(),
                        Toast.LENGTH_LONG).show();
                if (location != null) {
                    Toast.makeText(Main2Activity.this, "location doesn't equal null",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final TextView autoDate = findViewById(R.id.auto_date);
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        autoDate.setText(date);

        final Switch dateSwitch = findViewById(R.id.date_switch);
        dateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Switch dateSwitch = findViewById(R.id.date_switch);
                TextView autoDate = findViewById(R.id.auto_date);
                final TextInputLayout customDate = findViewById(R.id.date_layout);
                if (dateSwitch.isChecked() == Boolean.TRUE) {
                    customDate.setVisibility(View.INVISIBLE);
                    autoDate.setVisibility(View.VISIBLE);


                } else {
                    customDate.setVisibility(View.VISIBLE);
                    autoDate.setVisibility(View.INVISIBLE);
                }
            }
        });


        Button createMemory = findViewById(R.id.createMemory);
        createMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText title = findViewById(R.id.title);
                String text = title.getText().toString();
                setTextinFile(text + "\n");

                Switch dateSwitch = findViewById(R.id.date_switch);
                if (dateSwitch.isChecked()){
                    String text2 = autoDate.getText().toString();
                    setTextinFile(text2 + "\n");
                }
                else{
                    TextInputEditText date = findViewById(R.id.custom_date_text);
                    String text2 = date.getText().toString();
                    setTextinFile(text2 + "\n");
                }

                EditText description = findViewById(R.id.description);
                setTextinFile(description.getText().toString() + "\n");



                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteFile("memories.txt");
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        mapButton();
        listButton();
        homeButton();
        cameraButton();
        //init();
    }

    private void init(){
        Log.d(TAG, "init: initializing");
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH
                        || i == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == keyEvent.ACTION_DOWN
                        || keyEvent.getAction() == keyEvent.KEYCODE_ENTER){
                    geoLocate();
                }
                return false;
            }
        });
    }
    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");
        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(Main2Activity.this);
        List<Address> list = new ArrayList<>();
        try{
            list= geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }
        if (list.size() > 0) {
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    private void mapButton() {
        ImageButton mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, MapsActivity.class));
            }
        });
    }

    private void listButton() {
        ImageButton listButton = findViewById(R.id.listButton);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, MemoryListActivity.class));
            }
        });
    }

    private void homeButton() {
        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, MainActivity.class));
            }
        });

    }

    //private void cameraButton() {
      //  Button cameraButton = findViewById(R.id.cameraButton);
      //  cameraButton.setOnClickListener(new View.OnClickListener() {
      //      @Override
       //     public void onClick(View v) {
       //         startActivity(new Intent(Main2Activity.this, CameraActivity.class));
       //     }
      //  });

    //}
    //@Override
   // public boolean onCreateOptionsMenu(Menu menu) {
    //    getMenuInflater().inflate(R.menu.menu_main, menu);
   //     return true;
   // }


    private void cameraButton() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    viewImage.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image from gallery......******************.........", picturePath+"");
                viewImage.setImageBitmap(thumbnail);
            }
        }
    }




}
