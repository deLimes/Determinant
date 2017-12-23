package com.example.delimes.determinant;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by User on 08.12.2017.
 */

public class PixelPicker2 extends ConstraintLayout {


    private Context context;
    private int textColor = Color.BLACK;
    private long repeatDeley = 50;


    private int elementHeight = 100;

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
    public PixelPicker2(Context context, AttributeSet attributeSet ) {
        super(context, attributeSet);

        this.context = context;
        this.setLayoutParams( new LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT ) );

        //setOrientation(LinearLayout.VERTICAL);

        LayoutParams elementParams = new LayoutParams( elementHeight, elementWidth );
        initDownButton( context );
        initUpButton( context );
        initLeftButton( context );
        initRightButton( context );


        //setOrientation(LinearLayout.VERTICAL);
        addView( up, elementParams );
        addView( down, elementParams );
        addView( left, elementParams );
        addView( right, elementParams );

        Button equalizer = new Button(context);
        equalizer.post(new Runnable() {
            @Override
            public void run() {

                //right
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams( elementHeight, elementWidth );
                params.topToBottom = R.id.upR;
                params.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
                params.leftToRight = R.id.downR;
                params.bottomToTop = R.id.downR;
                right.setLayoutParams(params);

                //down
                params = new ConstraintLayout.LayoutParams( elementHeight, elementWidth );
                //params.topToBottom = R.id.rightR;
                params.rightToLeft = R.id.rightR;
                params.leftToRight = R.id.leftR;
                params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
                down.setLayoutParams(params);

                //left
                params = new ConstraintLayout.LayoutParams( elementHeight, elementWidth );
                params.topToBottom = R.id.upR;
                params.rightToLeft = R.id.downR;
                params.bottomToTop = R.id.downR;
                left.setLayoutParams(params);

                //up
                params = new ConstraintLayout.LayoutParams( elementHeight, elementWidth );
                //params.leftToRight = R.id.leftR;
                params.bottomToTop = R.id.rightR;
                params.rightToLeft = R.id.rightR;
                up.setLayoutParams(params);

            }
        });
    }

    private void initUpButton(Context context){
        up = new TouchableButton( context );
        up.setId(R.id.upR);
        up.setImageResource(R.drawable.up);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            up.setBackground(null);
        }
        up.setScaleType(ImageView.ScaleType.FIT_CENTER);

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
        down.setId(R.id.downR);
        down.setImageResource(R.drawable.down);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            down.setBackground(null);
        }
        down.setScaleType(ImageView.ScaleType.FIT_CENTER);

        down.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                verticalIncrement();
            }
        });


        down.setOnLongClickListener(
                new OnLongClickListener(){
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
        left.setId(R.id.leftR);
        left.setImageResource(R.drawable.left);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            left.setBackground(null);
        }
        left.setScaleType(ImageView.ScaleType.FIT_CENTER);
        // Increment once for a click
        left.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                horizontalDecrement();
            }
        });

        left.setOnLongClickListener(
                new OnLongClickListener(){
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
        right.setId(R.id.rightR);
        right.setImageResource(R.drawable.right);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            right.setBackground(null);
        }
        right.setScaleType(ImageView.ScaleType.FIT_CENTER);
        // Increment once for a click
        right.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                horizontalIncrement();
            }
        });

        right.setOnLongClickListener(
                new OnLongClickListener(){
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

        if( Size.point2.y < maximumHeight ){
            Size.point2.y = Size.point2.y + 1;
        }else{
            Size.point2.y = minimum;
        }

        MainActivity.viewSize.invalidate();
        MainActivity.viewSize.destroyDrawingCache();
        Size.drawingCache = MainActivity.viewSize.getDrawingCache();
        Size.adapt(Size.point2);
        Size.showResult();
    }

    public void verticalDecrement(){

        Size.drawingCache = MainActivity.viewSize.getDrawingCache();
        Size.adapt(Size.point1);

        if( Size.point2.y > minimum ){
            Size.point2.y = Size.point2.y - 1;
        }else{
            Size.point2.y = maximumHeight;
        }

        MainActivity.viewSize.invalidate();
        MainActivity.viewSize.destroyDrawingCache();
        Size.drawingCache = MainActivity.viewSize.getDrawingCache();
        Size.adapt(Size.point2);
        Size.showResult();
    }


    public void horizontalIncrement(){

        Size.drawingCache = MainActivity.viewSize.getDrawingCache();
        Size.adapt(Size.point1);

        if( Size.point2.x < maximumWidth ){
            Size.point2.x = Size.point2.x + 1;
        }else{
            Size.point2.x = minimum;
        }

        MainActivity.viewSize.invalidate();
        MainActivity.viewSize.destroyDrawingCache();
        Size.drawingCache = MainActivity.viewSize.getDrawingCache();
        Size.adapt(Size.point2);
        Size.showResult();
    }

    public void horizontalDecrement(){

        Size.drawingCache = MainActivity.viewSize.getDrawingCache();
        Size.adapt(Size.point1);

        if( Size.point2.x > minimum ){
            Size.point2.x = Size.point2.x - 1;
        }else{
            Size.point2.x = maximumWidth;
        }

        MainActivity.viewSize.invalidate();
        MainActivity.viewSize.destroyDrawingCache();
        Size.drawingCache = MainActivity.viewSize.getDrawingCache();
        Size.adapt(Size.point2);
        Size.showResult();
    }

    class TouchableButton extends android.support.v7.widget.AppCompatImageButton {

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
                            Size.ivLargerImage.setVisibility(View.GONE);
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
        PixelPicker2.minimum = minimum;
    }

    public static void setMaximumHeight(int maximumHeight) {
        PixelPicker2.maximumHeight = maximumHeight;
    }

    public static void setMaximumWidth(int maximumWidth) {
        PixelPicker2.maximumWidth = maximumWidth;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
