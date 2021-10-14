package com.example.emiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {

    //declaring variables created in the activity_main.xml

    //this is the result calculated
    TextView result;
    //these are the 3 input texts
    EditText editP, editIR, editYears;
    //this Button will initiate the calculation
    Button calculateButton;

    //this button will take the user to the second page
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //accessing all variables created in the activity_main.xml
        editP = (EditText) findViewById(R.id.editP);
        editIR = (EditText) findViewById(R.id.editIR);
        editYears = (EditText) findViewById(R.id.editYears);

        calculateButton = (Button) findViewById(R.id.calculateButton);

        result = (TextView) findViewById(R.id.result);

        //implementation of the calculation button
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //converting all received inputs to strings
                String principalAmount = editP.getText().toString();
                String interest = editIR.getText().toString();
                String years = editYears.getText().toString();

                //parsing to convert the strings to float values
                float p = Float.parseFloat(principalAmount);
                float r = Float.parseFloat(interest);
                float n = Float.parseFloat(years);

                //creating variables that will hold values that are created in the functions below, and be used in other functions
                float mpAmount = mortgagePrincipalAmount(p);
                float iRate = interestRate(r);
                float months = numberOfMonths(n);
                float numerator = calculateNumerator(iRate, months);
                float denominator = calculateDenominator(numerator);
                float bracket = calculateBracket(numerator, denominator);
                //Final Result
                float res = mortgagePaymentCalc(mpAmount, iRate, bracket);
                //Calling and showing final result
                result.setText(String.valueOf(res));
            }
        });
        //implementation of the button to send user to the second page
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });
    }
    public void openActivity2() {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    // Mortgage Payment Calculator formula:
    // E = EMI
    // P = Mortgage Principal Amount
    // r = interest rate
    // n = amortization period in number of months
    //
    // E = P * r * (((1 + r)^n)/((1 + r)^n - 1))

    //initiating the p input (mortgage principal amount) to represent the  "P" in the formula
    public float mortgagePrincipalAmount(float p){
        return (float) (p);
    }
    //initiating the r input (interest rate) to represent the  "r" in the formula
    //the interest rate is first divided by 12 to implement the monthly frequency
    //this is then divided by 100 to convert this value to a percentage
    public float interestRate(float r){
        return (float) (r/12/100);
    }
    //initiating the n input (years) to represent "n" in the formula
    //Since the payment frequency is monthly, the number of years will be multiplied by 12
    public float numberOfMonths(float n){
        return (float) (n * 12);
    }
    // Calculating numerator(top) of bracket portion in formula
    // (1 + r) ^ n
    public float calculateNumerator(float iRate,float months ){
        return (float) (Math.pow(1 + iRate, months));
    }
    // Calculating denominator(bottom) of bracket portion in formula
    // ((1 + r) ^ n) - 1
    // Implemented this by just taking the numerator and subtracting 1
    public float calculateDenominator(float numerator){
        return (float) (numerator - 1);
    }
    // Calculating full bracket portion of formula
    // ((1 + r) ^ n) / (((1 + r) ^ n) - 1)
    public float calculateBracket(float numerator, float denominator){
        return (float) (numerator/denominator);
    }
    // Calculating Full Formula
    // E = P * r * (((1 + r)^n)/((1 + r)^n - 1))
    public float mortgagePaymentCalc(float mpAmount, float iRate, float bracket){
        return (float) (mpAmount * iRate * bracket);
    }


}