package c346.rp.edu.bmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText weight;
    EditText Height;
    Button btnCal;
    Button btnReset;
    TextView tvDate, tvBMI, tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weight = findViewById(R.id.weight);
        Height = findViewById(R.id.Height);
        btnCal = findViewById(R.id.btnCal);
        btnReset = findViewById(R.id.btnReset);
        tvDate = findViewById(R.id.tvDate);
        tvBMI = findViewById(R.id.tvBMI);
        tvResult = findViewById(R.id.tvResult);
        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Step 1
              Integer inputWeight = Integer.parseInt(weight.getText().toString());
              Float inputHeight = Float.parseFloat(Height.getText().toString());

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor preEdit = prefs.edit();

                preEdit.putInt("Weight", inputWeight);
                preEdit.putFloat("Height", inputHeight);

                preEdit.commit();

                float bmi = inputWeight/(inputHeight*inputHeight);

                //Step 2
                Calendar now = Calendar.getInstance();
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        now.get(Calendar.MONTH) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                tvDate.setText("Last Calculated Date: " + datetime);
                tvBMI.setText("Last Calculated BMI: " + bmi);
                if(bmi < 18.5){
                    tvResult.setText("You are underweight!");
                }
                else if(bmi < 24.9){
                    tvResult.setText("Your BMI is normal");
                }
                else if(bmi >= 25 && bmi <= 29.9){
                    tvResult.setText("You are overweight!");
                }
                else{
                    tvResult.setText("You are obese");

                }

            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               weight.setText("");
               Height.setText("");
               tvDate.setText("Last Calculated Date: ");
               tvBMI.setText("Last Calculated BMI");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        Integer inputWeight = Integer.parseInt(weight.getText().toString());
        Float inputHeight = Float.parseFloat(Height.getText().toString());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor preEdit = prefs.edit();

        preEdit.putInt("Weight", inputWeight);
        preEdit.putFloat("Height", inputHeight);

        preEdit.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Step 2b: Retrieve the saved data with the key "greeting" from the SharedPreference object
        Integer weight = prefs.getInt("Weight", 0);
        Float height = prefs.getFloat("Height", 0);
        float bmi = weight/(height*height);
        Calendar now = Calendar.getInstance();
        String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                now.get(Calendar.MONTH) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);

        tvDate.setText("Last Calculated Date: " + datetime);
        tvBMI.setText("Last Calculated BMI: " + bmi);
    }
}
