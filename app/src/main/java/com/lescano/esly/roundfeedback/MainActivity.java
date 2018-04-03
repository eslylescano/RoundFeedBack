package com.lescano.esly.roundfeedback;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    //declaring the views objects
    CanvasView canvasView;
    TextView punches;
    TextView speed;
    TextView power;
    ImageView speedImage;
    ImageView powerImage;

    TextView lefthpJabText;
    View lefthpJabProgressBar;
    TextView lefthpHookText;
    View lefthpHookProgressBar;
    TextView lefthpUppercutText;
    View lefthpUppercutProgressBar;

    TextView righthpCrossText;
    View righthpCrossProgressBar;
    TextView righthpHookText;
    View righthpHookProgressBar;
    TextView righthpUppercutText;
    View righthpUppercutProgressBar;
    Button playButton;
    TextView timeCounter;
    ProgressBar progressBarCounter;
    ImageView speedAvgImage;
    ImageView powerAvgImage;

    Boxer boxer = new Boxer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //calling the method for create the views
        createViews();



    }

    //creating the views by the id
    private void createViews(){

        canvasView =(CanvasView)findViewById(R.id.id_canvas_view);
        punches =(TextView) findViewById(R.id.id_punches);
        speed =(TextView) findViewById(R.id.id_speed_avg);
        power =(TextView) findViewById(R.id.id_power_avg_text);
        speedImage =(ImageView) findViewById(R.id.id_speed_avg_image);
        powerImage =(ImageView) findViewById(R.id.id_power_avg_image);

         lefthpJabText = (TextView) findViewById(R.id.id_left_hp_jab_text);
         lefthpJabProgressBar = (View) findViewById(R.id.id_left_hp_jab_progressbar);
         lefthpHookText = (TextView) findViewById(R.id.id_left_hp_hook_text);
         lefthpHookProgressBar = (View) findViewById(R.id.id_left_hp_hook_progressbar);
         lefthpUppercutText = (TextView) findViewById(R.id.id_left_hp_uppercut_text);
         lefthpUppercutProgressBar = (View) findViewById(R.id.id_left_hp_uppercut_progressbar);

         righthpCrossText = (TextView) findViewById(R.id.id_right_hp_cross_text);
         righthpCrossProgressBar = (View) findViewById(R.id.id_right_hp_cross_progressbar);
         righthpHookText = (TextView) findViewById(R.id.id_right_hp_hook_text);
         righthpHookProgressBar = (View) findViewById(R.id.id_right_hp_hook_progressbar);
         righthpUppercutText = (TextView) findViewById(R.id.id_right_hp_uppercut_text);
         righthpUppercutProgressBar = (View) findViewById(R.id.id_right_hp_uppercut_progressbar);
         playButton = (Button) findViewById(R.id.id_play_button);
        timeCounter = (TextView) findViewById(R.id.id_time_counter);
        progressBarCounter = (ProgressBar) findViewById(R.id.id_progressbar_counter);
        speedAvgImage = (ImageView) findViewById(R.id.id_speed_avg_image);
        powerAvgImage = (ImageView) findViewById(R.id.id_power_avg_image);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayData();
            }
        });

    }


    //method that is call when the button is pressed
    private  void displayData()  {
            playButton.setEnabled(false);
            boxer.setData(getApplicationContext(), R.raw.wsbfv_round1);/*seting the data in the object*/
            HashMap<Integer, Float> intensityPunches = boxer.getIntensityPunches(); /*getting the */


            canvasView.setIntensityPunches(intensityPunches);/*setting the data in  the canvas*/
            //seting the data in the textviews
            punches.setText(String.valueOf( boxer.getPunches()));
            speed.setText(String.format("%.1f", boxer.getSpeedAvg()));
            power.setText(String.format("%.1f", boxer.getPowerAvg()));

            lefthpJabText.setText(String.valueOf(boxer.getLeftHandPunchesJab()));
            lefthpHookText.setText(String.valueOf(boxer.getLeftHandPunchesHook()));
            lefthpUppercutText.setText(String.valueOf(boxer.getLeftHandPunchesUppercut()));

            int visibility = 0;

            ViewGroup.LayoutParams params = lefthpJabProgressBar.getLayoutParams();
            params.width = (int) params.width * boxer.getLeftHandPunchesJab() / boxer.getMaxTypePunch();
            lefthpJabProgressBar.setLayoutParams(params);
            visibility = params.width == 0 ? View.GONE : View.VISIBLE;
            lefthpJabProgressBar.setVisibility(visibility);


            params = lefthpHookProgressBar.getLayoutParams();
            params.width = (int) params.width * boxer.getLeftHandPunchesHook() / boxer.getMaxTypePunch();
            lefthpHookProgressBar.setLayoutParams(params);
            visibility = params.width == 0 ? View.GONE : View.VISIBLE;
            lefthpHookProgressBar.setVisibility(visibility);

            params = lefthpUppercutProgressBar.getLayoutParams();
            params.width = (int) params.width * boxer.getLeftHandPunchesUppercut() / boxer.getMaxTypePunch();
            lefthpUppercutProgressBar.setLayoutParams(params);
            visibility = params.width == 0 ? View.GONE : View.VISIBLE;
            lefthpUppercutProgressBar.setVisibility(visibility);


            righthpCrossText.setText(String.valueOf(boxer.getRightHandPunchesCross()));
            righthpHookText.setText(String.valueOf(boxer.getRightHandPunchesHook()));
            righthpUppercutText.setText(String.valueOf(boxer.getRightHandPunchesUppercut()));


            params = righthpCrossProgressBar.getLayoutParams();
            params.width = (int) params.width * boxer.getRightHandPunchesCross() / boxer.getMaxTypePunch();
            righthpCrossProgressBar.setLayoutParams(params);
            visibility = params.width == 0 ? View.GONE : View.VISIBLE;
            righthpCrossProgressBar.setVisibility(visibility);


            params = righthpHookProgressBar.getLayoutParams();
            params.width = (int) params.width * boxer.getRightHandPunchesHook() / boxer.getMaxTypePunch();
            righthpHookProgressBar.setLayoutParams(params);
            visibility = params.width == 0 ? View.GONE : View.VISIBLE;
            righthpHookProgressBar.setVisibility(visibility);

            params = righthpUppercutProgressBar.getLayoutParams();
            params.width = (int) params.width * boxer.getRightHandPunchesUppercut() / boxer.getMaxTypePunch();
            righthpUppercutProgressBar.setLayoutParams(params);
            visibility = params.width == 0 ? View.GONE : View.VISIBLE;
            righthpUppercutProgressBar.setVisibility(visibility);

            String seconds = boxer.getTimerCounter()%60<10 ? "0"+(int) boxer.getTimerCounter()%60 :""+(int) boxer.getTimerCounter()%60;
            timeCounter.setText((int) boxer.getTimerCounter()/60+":"+seconds);


            progressBarCounter.setProgress((int)(boxer.getTimerCounter()/180*100));
            progressBarCounter.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

            speedAvgImage.setVisibility(View.VISIBLE);
            powerAvgImage.setVisibility(View.VISIBLE);

    }




}
