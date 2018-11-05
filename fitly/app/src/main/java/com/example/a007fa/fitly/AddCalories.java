package com.example.a007fa.fitly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddCalories extends AppCompatActivity {
    Button submit;
    EditText textData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_calories);

        textData = (EditText) findViewById(R.id.calories);
        textData.setInputType(InputType.TYPE_CLASS_NUMBER);

        submit = (Button) findViewById(R.id.submit);

        final String INTENT_CALORIEINTAKE = "com.app.fitly.ADDCALORIES";

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String calories = textData.getText().toString();
                if(TextUtils.isEmpty(calories)) {
                    textData.setError("The item name cannot be empty");
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), fitlyHandler.class);
                intent.putExtra("calories", Integer.parseInt(calories) );
                intent.setAction(INTENT_CALORIEINTAKE);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

    }
}