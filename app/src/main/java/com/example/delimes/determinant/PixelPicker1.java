package com.example.delimes.determinant;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Created by User on 08.12.2017.
 */

public class PixelPicker1 extends ConstraintLayout {


    public static Activity activity;
    private Context context;
    private int textColor = Color.BLACK;
    private long repeatDeley = 100;


    private int elementHeight = 120;

    private int elementWidth = elementHeight; // you're all squares, yo


    private static int minimum = 0;
    private static int maximumHeight = 0;
    private static int maximumWidth = 0;

    private int textSize = 10;

    ImageButton up;
    ImageButton down;
    ImageButton left;
    ImageButton right;
    public EditText valueText;

    private Handler repeatUpdateHandler = new Handler();

    private boolean autoIncrement = false;
    private boolean autoDecrement = false;

    class VerticalRepetetiveUpdater implements Runnable {
        public void run() {
            if( autoIncrement ){
                verticalIncrement();
                repeatUpdateHandler.postDelayed( new VerticalRepetetiveUpdater(), repeatDeley );
            } else if( autoDecrement ){
                verticalDecrement();
                repeatUpdateHandler.postDelayed( new VerticalRepetetiveUpdater(), repeatDeley );
            }
        }
    }

    class HorizontalRepetetiveUpdater implements Runnable {
        public void run() {
            if( autoIncrement ){
                horizontalIncrement();
                repeatUpdateHandler.postDelayed( new HorizontalRepetetiveUpdater(), repeatDeley );
            } else if( autoDecrement ){
                horizontalDecrement();
                repeatUpdateHandler.postDelayed( new HorizontalRepetetiveUpdater(), repeatDeley );
            }
        }
    }
    public PixelPicker1(Context context, AttributeSet attributeSet ) {
        super(context, attributeSet);

        this.context = context;
        this.setLayoutParams( new LinearLayout.LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT ) );

        //setOrientation(LinearLayout.VERTICAL);

        ConstraintLayout.LayoutParams elementParams = new ConstraintLayout.LayoutParams( elementHeight, elementWidth );
        initDownButton( context );
        initUpButton( context );
        initLeftButton( context );
        initRightButton( context );


        //setOrientation(LinearLayout.VERTICAL);
        addView( up, elementParams );
        addView( down, elementParams );
        addView( left, elementParams );
        addView( right, elementParams );

//        Button equalizer = new Button(context);
//        equalizer.postOnAnimationDelayed(new Runnable() {
//            @Override
//            public void run() {

                //down
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams( elementHeight, elementWidth );
                //params.topToBottom = R.id.leftL;
                params.leftToRight = R.id.leftL;
                params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
                params.rightToLeft = R.id.rightL;
                down.setLayoutParams(params);

                //left
                params = new ConstraintLayout.LayoutParams( elementHeight, elementWidth );
                params.topToBottom = R.id.upL;
                params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
                params.rightToLeft = R.id.downL;
                left.setLayoutParams(params);

                //right
                params = new ConstraintLayout.LayoutParams( elementHeight, elementWidth );
                params.topToBottom = R.id.upL;
                params.leftToRight = R.id.downL;
                params.bottomToTop = R.id.downL;
                right.setLayoutParams(params);

                //up
                params = new ConstraintLayout.LayoutParams( elementHeight, elementWidth );
                params.leftToRight = R.id.leftL;
                params.bottomToTop = R.id.leftL;
                params.rightToLeft = R.id.rightL;
                up.setLayoutParams(params);

//            }
//        }, 5000);



    }

    private void initUpButton(Context context){

        up = new TouchableButton( context );
        up.setId(R.id.upL);
        /*
        up.setTextSize( textSize );
        up.setText( "⇑" );//▲
        up.setTextColor(textColor);
        */
        up.setImageResource(R.drawable.up);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            up.setBackground(null);
        }
        up.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //up.setAdjustViewBounds(true);
        //up.setImageDrawable(getResources().getDrawable(R.drawable.left));
        //ResourcesCompat.getDrawable(getResources(), R.drawable.tape_measure, null);


        // Increment once for a click
        up.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                verticalDecrement();
            }
        });

        up.setOnLongClickListener(
                new OnLongClickListener(){
                    public boolean onLongClick(View arg0) {
                        autoDecrement = true;
                        repeatUpdateHandler.post( new VerticalRepetetiveUpdater() );
                        return false;
                    }
                }
        );


    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }


    private void initDownButton( Context context){
        down = new TouchableButton( context );
        down.setId(R.id.downL);
        /*
        down.setTextSize( textSize );
        down.setText( "⇓" );//▼
        down.setTextColor(textColor);
        */
        down.setImageResource(R.drawable.down);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            down.setBackground(null);
        }
        down.setScaleType(ImageView.ScaleType.FIT_CENTER);

        down.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                verticalIncrement();
            }
        });


        down.setOnLongClickListener(
                new View.OnLongClickListener(){
                    public boolean onLongClick(View arg0) {
                        autoIncrement = true;
                        repeatUpdateHandler.post( new VerticalRepetetiveUpdater() );
                        return false;
                    }
                }
        );

    }

    private void initLeftButton(Context context){
        left = new TouchableButton( context );
        left.setId(R.id.leftL);
        /*
        left.setTextSize( textSize );
        left.setText( "⇐" );//◄
        left.setTextColor(textColor);
        */
        left.setImageResource(R.drawable.left);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            left.setBackground(null);
        }
        left.setScaleType(ImageView.ScaleType.FIT_CENTER);

        // Increment once for a click
        left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                horizontalDecrement();
            }
        });

        left.setOnLongClickListener(
                new View.OnLongClickListener(){
                    public boolean onLongClick(View arg0) {
                        autoDecrement = true;
                        repeatUpdateHandler.post( new HorizontalRepetetiveUpdater() );
                        return false;
                    }
                }
        );

    }

    private void initRightButton(Context context){
        right = new TouchableButton( context );
        right.setId(R.id.rightL);
        /*
        right.setTextSize( textSize );
        right.setText( "⇒" );//►
        right.setTextColor(textColor);
        */
        right.setImageResource(R.drawable.right);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            right.setBackground(null);
        }
        right.setScaleType(ImageView.ScaleType.FIT_CENTER);


        // Increment once for a click
        right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                horizontalIncrement();
            }
        });

        right.setOnLongClickListener(
                new View.OnLongClickListener(){
                    public boolean onLongClick(View arg0) {
                        autoIncrement = true;
                        repeatUpdateHandler.post( new HorizontalRepetetiveUpdater() );
                        return false;
                    }
                }
        );

    }


    public void verticalIncrement(){

        Size.drawingCache = MainActivity.viewSize.getDrawingCache();
        Size.adapt(Size.point1);

        if( Size.point1.y < maximumHeight ){
            Size.point1.y = Size.point1.y + 1;
        }else{
            Size.point1.y = minimum;
        }

        MainActivity.viewSize.invalidate();
        MainActivity.viewSize.destroyDrawingCache();
        Size.drawingCache = MainActivity.viewSize.getDrawingCache();
        Size.adapt(Size.point1);
        Size.showResult();
    }

    public void verticalDecrement(){

        Size.drawingCache = MainActivity.viewSize.getDrawingCache();
        Size.adapt(Size.point1);

        if( Size.point1.y > minimum ){
            Size.point1.y = Size.point1.y - 1;
        }else{
            Size.point1.y = maximumHeight;
        }

        MainActivity.viewSize.invalidate();
        MainActivity.viewSize.destroyDrawingCache();
        Size.drawingCache = MainActivity.viewSize.getDrawingCache();
        Size.adapt(Size.point1);
        Size.showResult();

    }


    public void horizontalIncrement(){

        Size.drawingCache = MainActivity.viewSize.getDrawingCache();
        Size.adapt(Size.point1);

        if( Size.point1.x < maximumWidth ){
            Size.point1.x = Size.point1.x + 1;
        }else{
            Size.point1.x = minimum;
        }

        MainActivity.viewSize.invalidate();
        MainActivity.viewSize.destroyDrawingCache();
        Size.drawingCache = MainActivity.viewSize.getDrawingCache();
        Size.adapt(Size.point1);
        Size.showResult();

    }

    public void horizontalDecrement(){

        Size.drawingCache = MainActivity.viewSize.getDrawingCache();
        Size.adapt(Size.point1);

        if( Size.point1.x > minimum ){
            Size.point1.x = Size.point1.x - 1;
        }else{
            Size.point1.x = maximumWidth;
        }
       // Log.d("MyLogs"," Size.point1.x:"+  Size.point1.x);

        MainActivity.viewSize.invalidate();
        MainActivity.viewSize.destroyDrawingCache();
        Size.drawingCache = MainActivity.viewSize.getDrawingCache();
        Size.adapt(Size.point1);
        Size.showResult();


    }

    class TouchableButton extends AppCompatImageButton {

        public TouchableButton(Context context) {
            super(context);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if( event.getAction() == MotionEvent.ACTION_UP ){

                autoIncrement = false;
                autoDecrement = false;

                new CountDownTimer(10000, 10000) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        if (!(autoIncrement || autoDecrement)) {
                            Size.ivLargerImage.setVisibility(View.INVISIBLE);
                        }
                    }
                }.start();
            }
            return super.onTouchEvent(event);
        }

    }

    public long getRepeatDeley() {
        return repeatDeley;
    }

    public int getElementHeight() {
        return elementHeight;
    }

    public int getElementWidth() {
        return elementWidth;
    }

    public int getMinimum() {
        return minimum;
    }

    public int getMaximumHeight() {
        return maximumHeight;
    }

    public int getMaximumWidth() {
        return maximumWidth;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setRepeatDeley(long repeatDeley) {
        this.repeatDeley = repeatDeley;
    }

    public void setElementHeight(int elementHeight) {
        this.elementHeight = elementHeight;
    }

    public void setElementWidth(int elementWidth) {
        this.elementWidth = elementWidth;
    }

    public static void setMinimum(int minimum) {
        PixelPicker1.minimum = minimum;
    }

    public static void setMaximumHeight(int maximumHeight) {
        PixelPicker1.maximumHeight = maximumHeight;
    }

    public static void setMaximumWidth(int maximumWidth) {
        PixelPicker1.maximumWidth = maximumWidth;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
