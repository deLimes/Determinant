package com.example.delimes.determinant;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 14.11.2017.
 */

public class Size extends View {
    Context context;
    Activity activity;
    Paint p;
    static TextView tvLength;
    static TextView tvDistance;
    public static ImageView ivLargerImage;
    static ImageView ivPhoto;
    public List<Point> points = new ArrayList<Point>();
    // переменные для перетаскивания
    float x = 0;
    float y = 0;
    public static Point point1;
    public static Point point2;
    boolean dragPpoint1 = false;
    boolean dragPpoint2 = false;
    float dragX = 0;
    float dragY = 0;
    static int side = 25;
    static int doubleSide = side * 2;
    public static Bitmap drawingCache;


    public Size(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.activity = (Activity) context;;

        setDrawingCacheEnabled(true);

        p = new Paint();
        point1 = new Point(100,300);
        point2 = new Point(200,300);

        points.add(point1);
        points.add(point2);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        // настройка кисти
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.BLACK);
        // толщина линии = 10
        p.setStrokeWidth(5);
        for (Point point:points) {
            canvas.drawCircle(point.x, point.y, 15, p);
        }

        p.setStyle(Paint.Style.FILL);
        /*
        p.setColor(Color.RED);
        p.setStrokeWidth(1);
        for (Point point:points) {
            canvas.drawCircle(point.x, point.y, 5, p);
        }
        */

        p.setColor(Color.RED);
        p.setStrokeWidth(5);
        //canvas.drawPath(makeArrow(400,200), p);
        canvas.drawLine(point1.x, point1.y, point2.x, point2.y, p);

    }

    /**
     * Найти расстояние между точками A(-1, 3) и B(6,2).
     Решение.

     AB = √(xb - xa)2 + (yb - ya)2 = √(6 - (-1))2 + (2 - 3)2 = √72 + 12 = √50 = 5√2
     *
     */
    private static double determineLength() {

        double length = 0;
        length = Math.sqrt(Math.pow(point2.x - point1.x, 2) + Math.pow(point2.y - point1.y, 2));
        return length;
    }


    private Path makeArrow(float length, float height) { //=height/2
        Path path = new Path();
        path.reset();
        path.moveTo(0.0f, height * 0.5f);
        path.lineTo(length, height * 0.5f);
        path.lineTo(length*0.97f, height * 0.35f);
        path.lineTo(length*1.2f, height*0.5f);
        path.lineTo(length*0.97f, height*0.65f);
        path.lineTo(length, height * 0.5f);
        path.close();
        return path;
    }

    public static void adapt(Point point){

        if(ivLargerImage == null) {
            ivLargerImage = (ImageView) MainActivity2.activity.findViewById(R.id.ivLargerImage);
            ivPhoto = (ImageView) MainActivity2.activity.findViewById(R.id.ivPhoto);
            ivPhoto.setDrawingCacheEnabled(true);
            PixelPicker1.setMinimum(side + 1);
            PixelPicker1.setMaximumHeight(ivPhoto.getHeight() - side - 1);
            PixelPicker1.setMaximumWidth(ivPhoto.getWidth() - side - 1);
            PixelPicker2.setMinimum(side + 1);
            PixelPicker2.setMaximumHeight(ivPhoto.getHeight() - side - 1);
            PixelPicker2.setMaximumWidth(ivPhoto.getWidth() - side - 1);
            ////////////////////////setDrawingCacheEnabled(true);
            ivLargerImage.animate().scaleX(5.0f).scaleY(5.0f).setDuration(0);
        }

        //////////////////////////////////////destroyDrawingCache();
        ivPhoto.destroyDrawingCache();
        ivLargerImage.destroyDrawingCache();
        ivLargerImage.setVisibility(View.VISIBLE);
        /////////////////////////////////////////////

        int xCoord = (int)point.x - side;
        int yCoord = (int)point.y - side;

        //Log.d("MyLogs","xCoord:"+ xCoord);
        //Log.d("MyLogs","point.x:"+ point.x);

        Bitmap bitmap = ivPhoto.getDrawingCache();

        Bitmap croppedBitmap = Bitmap.createBitmap(
                bitmap, xCoord, yCoord, doubleSide, doubleSide);

        Bitmap croppedBitmap2 = Bitmap.createBitmap(
                drawingCache, xCoord, yCoord, doubleSide, doubleSide);

        int bitmap1Width = croppedBitmap.getWidth();
        int bitmap1Height = croppedBitmap.getHeight();
        int bitmap2Width = croppedBitmap2.getWidth();
        int bitmap2Height = croppedBitmap2.getHeight();

        float marginLeft = (float) (bitmap1Width * 0.5 - bitmap2Width * 0.5);
        float marginTop = (float) (bitmap1Height * 0.5 - bitmap2Height * 0.5);


        Bitmap overlayBitmap = Bitmap.createBitmap(bitmap1Width, bitmap1Height, croppedBitmap.getConfig());
        //создаем canvas
        Canvas canvas = new Canvas(overlayBitmap);
        //наносим на canvas 1-й битмап
        canvas.drawBitmap(croppedBitmap, new Matrix(), null);
        //сверху наносим 2-й битмап (по центру)
        canvas.drawBitmap(croppedBitmap2, marginLeft, marginTop, null);

        ivLargerImage.setImageBitmap(overlayBitmap);


        ////////////////////////////////////////////
    }

    public static void showResult(){

        if(tvLength == null) {
            tvLength = (TextView) MainActivity2.activity.findViewById(R.id.tvLength);
            tvDistance = (TextView) MainActivity2.activity.findViewById(R.id.tvDistance);
        }
        double length = Math.rint(100.0 * determineLength()) / 100.0;
        tvLength.setText("Length: "+Double.toString(Math.rint(100.0 * length) / 100.0)+" px");

        double distance = MainActivity.distanceReference * MainActivity.reference / length;
        tvDistance.setText("Distance: "+Double.toString(Math.rint(100.0 * distance) / 100.0));


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float radius = 30;
        // координаты Touch-события
        float evX = event.getX();
        float evY = event.getY();



        switch (event.getAction()) {
            // касание началось
            case MotionEvent.ACTION_DOWN:
                //positionOfTouchX = evX;
                //positionOfTouchY = evY;

                invalidate();
                destroyDrawingCache();
                drawingCache = getDrawingCache();

                if ((point1.x - radius <= evX && point1.x + radius >= evX) &&
                        (point1.y - radius <= evY && point1.y + radius >= evY)) {

                    // включаем режим перетаскивания
                    dragPpoint1 = true;

                    // разница между левым верхним углом квадрата и точкой касания
                    dragX = evX - point1.x;
                    dragY = evY - point1.y;

                    adapt(point1);

                }

                if ((point2.x - radius <= evX && point2.x + radius >= evX) &&
                        (point2.y - radius <= evY && point2.y + radius >= evY) && !dragPpoint1) {

                    // включаем режим перетаскивания
                    dragPpoint2 = true;

                    // разница между левым верхним углом квадрата и точкой касания
                    dragX = evX - point2.x;
                    dragY = evY - point2.y;

                    adapt(point2);
                }

                break;
            // тащим
            case MotionEvent.ACTION_MOVE:

                // если режим перетаскивания включен
                if (dragPpoint1) {

                    Bitmap bitmap = ivPhoto.getDrawingCache();
                    int xCoord = (int)(evX - dragX - side < 0 ? 0 :evX - dragX - side);
                    int yCoord = (int)(evY - dragY - side < 0 ? 0 :evY - dragY - side);

                    if (xCoord + doubleSide <= bitmap.getWidth() &&
                            yCoord + doubleSide <= bitmap.getHeight() &&
                            yCoord > 0 && xCoord > 0) {
                        // определеяем новые координаты
                        point1.x = evX - dragX;
                        point1.y = evY - dragY;

                        destroyDrawingCache();
                        drawingCache = getDrawingCache();

                        adapt(point1);
                    }
                }

                if (dragPpoint2) {

                    Bitmap bitmap = ivPhoto.getDrawingCache();
                    int xCoord = (int)(evX - dragX - side < 0 ? 0 :evX - dragX - side);
                    int yCoord = (int)(evY - dragY - side < 0 ? 0 :evY - dragY - side);


                    if (xCoord + doubleSide <= bitmap.getWidth() &&
                            yCoord + doubleSide <= bitmap.getHeight() &&
                            yCoord > 0 && xCoord > 0) {
                        // определеяем новые координаты
                        point2.x = evX - dragX;
                        point2.y = evY - dragY;

                        destroyDrawingCache();
                        drawingCache = getDrawingCache();

                        adapt(point2);
                    }

                }

                showResult();

                if (dragPpoint1 || dragPpoint2) {
                    invalidate();
                }

                break;
            // касание завершено
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                new CountDownTimer(2000, 2000) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        if (!dragPpoint1 && !dragPpoint2) {
                            ivLargerImage.setVisibility(View.GONE);
                        }
                    }
                }.start();

                // выключаем режим перетаскивания
                dragPpoint1 = false;
                dragPpoint2 = false;

                break;

        }

        return true;
    }

    public class Point
    {
        /**Позиция*/
        public float x;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float y;

    }






}
