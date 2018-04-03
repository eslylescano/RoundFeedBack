package com.lescano.esly.roundfeedback;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by esly on 29/3/18.
 */


//Class define a boxer
public class Boxer {

    private List<Punch> punchList = new ArrayList<Punch>(); /*List of punches once the file have been read*/
    private int punches; /* number of punches*/
    private float speedAvg; /*average of the speed in miler per hour*/
    private float powerAvg; /*average of power in gravity units*/
    private float timerCounter; /*store the final time*/
    private int leftHandPunchesJab; /*count the number of left hand punches jab int a round*/
    private int leftHandPunchesHook; /*count the number of left hand punches Hook int a round*/
    private int leftHandPunchesUppercut; /*count the number of left hand punches uppercut int a round*/
    private int rightHandPunchesCross; /*count the number of right hand punches cross int a round*/
    private int rightHandPunchesHook; /*count the number of right hand punches hook int a round*/
    private int rightHandPunchesUppercut; /*count the number of right hand punches uppercut int a round*/
    private int targetPunchesPerInterval=8; /*target of punches per interval (this vlaue has been created for the developer for do the calculates)*/

    //class constructor
    public Boxer() {

    }

    //getter and setters
    public float getTimerCounter() {
        return timerCounter;
    }

    public int getPunches() {
        return punches;
    }

    public float getSpeedAvg() {
        return speedAvg;
    }

    public float getPowerAvg() {
        return powerAvg;
    }


    public int getLeftHandPunchesJab() {
        return leftHandPunchesJab;
    }

    public int getLeftHandPunchesHook() {
        return leftHandPunchesHook;
    }

    public int getLeftHandPunchesUppercut() {
        return leftHandPunchesUppercut;
    }

    public int getRightHandPunchesCross() {
        return rightHandPunchesCross;
    }

    public int getRightHandPunchesHook() {
        return rightHandPunchesHook;
    }

    public int getRightHandPunchesUppercut() {
        return rightHandPunchesUppercut;
    }



    //method for read the data file
    public void setData(Context context, int resource){


        InputStream inputStream = context.getResources().openRawResource(resource);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line="";

        int punchCounter=0;
        float speedSume=0;
        float powerSume=0;
        float ts=0;
        int punch_type_id=0;
        float speed_mph=0;
        float power_g=0;
        try {
            line=reader.readLine();
            while ((line=reader.readLine())!=null){
                String[] tokens = line.split(",");
                Punch punch = new Punch();
                 ts = convertTsStringToFloat(tokens[0]);
                 punch_type_id = Integer.parseInt(tokens[1]);
                 speed_mph = Float.parseFloat(tokens[2]);
                 power_g = Float.parseFloat(tokens[3]);

                punch.setTs(ts);
                punch.setPunchTypeId(punch_type_id);
                punch.setSpeedMph(speed_mph);
                punch.setPowerG(power_g);
                punchList.add(punch);

                speedSume=speedSume+speed_mph;
                powerSume=powerSume+power_g;
                punchCounter++;

                switch (punch_type_id){
                    case 0:
                        leftHandPunchesJab++;
                        break;
                    case 1:
                        leftHandPunchesHook++;
                        break;
                    case 2:
                        leftHandPunchesUppercut++;
                        break;
                    case 3:
                        rightHandPunchesCross++;
                        break;
                    case 4:
                        rightHandPunchesHook++;
                        break;
                    case 5:
                        rightHandPunchesUppercut++;
                        break;

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        punches=punchCounter;
        speedAvg=speedSume/punchCounter;
        powerAvg=powerSume/punchCounter;
        timerCounter=ts;


    }

    //auxiliar method for convert the string data in float data
    private float convertTsStringToFloat(String ts){
        String[] tokens = ts.split(":");
        float secondInHours = Float.parseFloat(tokens[0])*3600;
        float secondInMinuts = Float.parseFloat(tokens[1])*60;
        float seconds = Float.parseFloat(tokens[2]);
        return secondInHours+secondInMinuts+seconds;
    }


    //method that split the the in intervals of 15 seconds and get the average of punches in  that interval
    public HashMap<Integer,Float> getIntensityPunches(){
        HashMap<Integer,Float> intesity = new HashMap<Integer,Float>();/*hashmao for store the time and the average */
        float numberPunches=0;/*counting the number of punches in the interval*/
        int cuurentTime=0; /*variable store the current time*/
        int previousTime=0; /*variable for store the previous time*/
        int intervalValue=15;


        for(Punch punch : punchList){

            cuurentTime=(int) punch.getTs()/intervalValue;

            if(cuurentTime > previousTime){
                intesity.put(new Integer((previousTime+1)*intervalValue),new Float((numberPunches-targetPunchesPerInterval)/targetPunchesPerInterval*100));
                numberPunches=0;
            }
            numberPunches++;
            previousTime=cuurentTime;
        }
        return intesity;
    }




    //function that return the max value of a type of punch
    public int getMaxTypePunch(){

        List<Integer> punchesValues = new ArrayList<Integer>();

        punchesValues.add(0,leftHandPunchesJab);
        punchesValues.add(1,leftHandPunchesHook);
        punchesValues.add(2,leftHandPunchesUppercut);
        punchesValues.add(3,rightHandPunchesCross);
        punchesValues.add(4,rightHandPunchesHook);
        punchesValues.add(5,leftHandPunchesUppercut);

        int maxValue=-1;
        for(int i=0;i<punchesValues.size();i++){
            int currentValue=punchesValues.get(i);
            if(currentValue>maxValue){
                maxValue=currentValue;
            }
        }
    return maxValue;
    }


    /*Auxiliar class that define a punch*/
    private class Punch{
        private float ts; /*variable store time in seconds*/
        private int punchTypeId; /*variable store the id of each punch*/
        private  float speedMph; /*variable store the speed of each punch in miles per hour*/
        private float powerG; /*variable store the power of the punch in gravity units*/
        public float getTs() {
            return ts;
        }
        public void setTs(float ts) {
            this.ts = ts;
        }
        public void setPunchTypeId(int punchTypeId) {
            this.punchTypeId = punchTypeId;
        }
        public void setSpeedMph(float speedMph) {
            this.speedMph = speedMph;
        }
        public void setPowerG(float powerG) {
            this.powerG = powerG;
        }

    }




}
