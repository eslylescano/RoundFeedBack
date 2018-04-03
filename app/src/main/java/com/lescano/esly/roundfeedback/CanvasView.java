package com.lescano.esly.roundfeedback;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by esly on 29/3/18.
 */

//class for draw  the data in interval of 15 seconds the number of punches
public class CanvasView extends View {

    private float width=0; /*for store the width*/
    private float height=0; /*for store the height*/
    private boolean dataAvaliable=false; /*for recognize when the data is avalaible*/
    private HashMap<Integer,Float> intensityPunches; /*HashMap that contain the time as a key and the average of punch as a values*/



//constructor of the canvas
    public CanvasView(Context context, @Nullable AttributeSet attrs ) {
        super(context, attrs);


    }


    public void setIntensityPunches(HashMap<Integer,Float> intensityPunches){
        this.intensityPunches=intensityPunches;
        dataAvaliable=true;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(dataAvaliable) {

            width = getWidth(); /*getting the width*/
            height = getHeight(); /*getting the height*/
            canvas.drawColor(Color.TRANSPARENT); /*setting the color for the canvas*/


            Paint p = new Paint(); /*for draw the line*/
            Paint p2 = new Paint(); /*for fill the area*/
            Path actionFillPath = new Path(); /*the path for the area*/

            p.setStrokeWidth(15); /*setting the width of the line*/
            LinearGradient lg = new LinearGradient(0, 0, 0, height,
                    new int[]{Color.GREEN, Color.YELLOW, Color.RED},
                    new float[]{0,.5f,1}, Shader.TileMode.REPEAT);/*declaring the color for the line and the area*/
            p.setShader(lg);/*setting the color for the line*/
            p2.setShader(lg);/*setting the color for the area*/
            p2.setAlpha((int)(p2.getAlpha()*0.2));/*setting the alpha as a 20% of darkness*/


            float previousCoordinateX = 0;/*variable for store the previous x coordinate*/
            float previousCoordinateY = 0;/*variable for store the previous y coordinate*/
            float currentCoodinateX = 0;/*variable for store the curret x coordinate*/
            float currentCoodinateY = 0;/*variable for store the current x coordinate*/



            //loop in the data
            for (Map.Entry<Integer, Float> entry : intensityPunches.entrySet()) {

                currentCoodinateX = ((float) entry.getKey() / (3 * 60)) * width;/*converting the seconds into x coordinates*/


                //converting the average of punches into y coordinates
                if (entry.getValue() >= 0) {
                    currentCoodinateY = height / 2 - entry.getValue() / 100 * height / 2;
                } else {
                    currentCoodinateY = height + entry.getValue() / 100 * height / 2;
                }


                //starting to draw after previousCoordinateX and previousCoordinateY have values
                if (previousCoordinateX != 0 && previousCoordinateY != 0) {


                    //for draw and fill the area
                    actionFillPath.moveTo(previousCoordinateX,previousCoordinateY);
                    actionFillPath.lineTo(previousCoordinateX,height/2);
                    actionFillPath.lineTo(currentCoodinateX,height/2);
                    actionFillPath.lineTo(currentCoodinateX,currentCoodinateY);
                    canvas.drawPath(actionFillPath,p2);
                    actionFillPath.reset();

                    //for draw the darker line
                    canvas.drawLine(previousCoordinateX, previousCoordinateY, currentCoodinateX, currentCoodinateY, p);



                }
                //update the values in the loop
                previousCoordinateX = currentCoodinateX;
                previousCoordinateY = currentCoodinateY;
            }

            //this is when the time is 0 and there is not punches
            actionFillPath.moveTo(currentCoodinateX,previousCoordinateY);
            actionFillPath.lineTo(currentCoodinateX,height/2);
            actionFillPath.lineTo(0,height/2);
            actionFillPath.lineTo(0,height / 2);
            canvas.drawLine(currentCoodinateX, currentCoodinateY, 0, height / 2, p);
            canvas.drawPath(actionFillPath,p2);
            actionFillPath.reset();




        }

    }



}
