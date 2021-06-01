package com.example.delimes.determinant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MyLog";
    final int TYPE_PHOTO = 1;
    final int REQUEST_CODE_PHOTO = 1;

    ImageView ivPhoto;
    EditText etDistanceReference;
    EditText etReference;
    public TextView tvLength;
    public static Double distanceReference = 100.0;
    public static Double reference = 170.0;
    public static Size viewSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        etDistanceReference = (EditText) findViewById(R.id.etDistanceReference);
        etReference = (EditText) findViewById(R.id.etReference);
        tvLength = (TextView) findViewById(R.id.tvLength);
        viewSize = (Size) findViewById(R.id.Size);

        MainActivity2.activity = (Activity) this;
        PixelPicker1.activity =  (Activity) this;

        etReference.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String strReference = editable.toString();
                if (strReference.length()>0) {
                    reference = Double.valueOf(strReference);
                }
            }
        });

        etDistanceReference.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String strDistanceReference = editable.toString();
                if (strDistanceReference.length()>0) {
                    distanceReference = Double.valueOf(strDistanceReference);
                }
            }
        });


    }

    public void onClickPhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_PHOTO));
        startActivityForResult(intent, REQUEST_CODE_PHOTO);
    }

    public void onClickTvDistance(View view) {

        Size.point1.x = 100;
        Size.point1.y = 300;//= new Size.Point(100,300);
        Size.point2.x = 200;
        Size.point2.y = 300;

        viewSize.invalidate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE_PHOTO) {
            if (resultCode == RESULT_OK) {
                if (intent == null) {
                    Log.d(TAG, "Intent is null");
                } else {
                    Log.d(TAG, "Photo uri: " + intent.getData());
                    Bundle bndl = intent.getExtras();
                    if (bndl != null) {
                        Object obj = intent.getExtras().get("data");
                        if (obj instanceof Bitmap) {
                            Bitmap bitmap = (Bitmap) obj;
                            Log.d(TAG, "bitmap " + bitmap.getWidth() + " x "
                                    + bitmap.getHeight());
                            ivPhoto.setImageBitmap(bitmap);
                        }
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "Canceled");
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preference = getSharedPreferences("MAIN_STORAGE", Context.MODE_PRIVATE);
        String strDistanceReference = preference.getString("distanceReference", "");
        if (!strDistanceReference.isEmpty()) {
            distanceReference = Double.valueOf(strDistanceReference);
        }

        String strReference = preference.getString("reference", "");
        if (!strReference.isEmpty()) {
            reference = Double.valueOf(strReference);
        }

        etDistanceReference.setText(Double.toString(Math.rint(100.0 * distanceReference) / 100.0));
        etReference.setText(Double.toString(Math.rint(100.0 * reference) / 100.0));


    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences preference = getSharedPreferences("MAIN_STORAGE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();

        editor.putString("distanceReference", Double.toString(distanceReference));
        editor.putString("reference", Double.toString(reference));
        editor.commit();
    }






}

