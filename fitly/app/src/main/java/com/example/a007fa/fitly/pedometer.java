package com.example.a007fa.fitly;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class pedometer extends Activity implements SensorEventListener {

    private SensorManager sManager ;
    private Sensor stepSensor;
    private float stepsFirst;
    private float steps;
    private boolean first;

    @Override
    public void onCreate(Bundle z){
        super.onCreate(z);
        sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        first = true;
        ((TextView)findViewById(R.id.StepCountText)).setText("Started");
    }

    public void onAccuracyChanged(Sensor x, int y){

    }

    public float getSteps() {
        return steps;
    }

    public void onSensorChanged(SensorEvent event){
        if(!first) {
            stepsFirst = event.values[0];
            ((TextView)findViewById(R.id.StepCountText)).setText("0");
        }
        else {
            steps = event.values[0] - stepsFirst;
            ((TextView)findViewById(R.id.StepCountText)).setText(Float.toString(steps));
        }
    }

}
