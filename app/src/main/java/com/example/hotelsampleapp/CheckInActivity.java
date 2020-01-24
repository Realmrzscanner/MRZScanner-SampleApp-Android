package com.example.hotelsampleapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckInActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE = 1;

    Button scanBtn,backBtn,proceedBtn;

    EditText editGivenName, surName, editDocNum, editIssuingCount, editNationallity, editDateOfBirth, editSex, editExporationDate, editOptionalVal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        scanBtn = findViewById(R.id.scan_button);
        proceedBtn = findViewById(R.id.proceede_btn);
        backBtn = findViewById(R.id.back_btn);
        editGivenName = findViewById(R.id.edit_given_name);
        surName = findViewById(R.id.edit_surname);
        editDocNum = findViewById(R.id.edit_document_number);
        editIssuingCount = findViewById(R.id.edit_issuing_country);
        editNationallity = findViewById(R.id.edit_nationality);
        editDateOfBirth = findViewById(R.id.edit_date_of_birth);
        editSex = findViewById(R.id.edit_sex);
        editExporationDate = findViewById(R.id.edit_expiration_date);
        editOptionalVal = findViewById(R.id.edit_optional_values);
//      imgBtnFront=findViewById(R.id.imageButton_scanImage_front);
//      imgBtnBack=findViewById(R.id.imageButton_scanImage_back);

        scanBtn.setOnClickListener(this);
//        imgBtnBack.setOnClickListener(this);
//        imgBtnFront.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        proceedBtn.setOnClickListener(this);
    }

    private void addResultToEditText(JSONObject successfulMrzScan) throws JSONException {
        editGivenName.setText(successfulMrzScan.getString("given_names_readable"));
        surName.setText(successfulMrzScan.getString("surname"));
        editDocNum.setText(successfulMrzScan.getString("document_number"));
        editIssuingCount.setText(successfulMrzScan.getString("issuing_country"));
        editNationallity.setText(successfulMrzScan.getString("nationality"));
        editDateOfBirth.setText(successfulMrzScan.getString("dob_raw"));
        editSex.setText(successfulMrzScan.getString("sex"));
        editExporationDate.setText(successfulMrzScan.getString("expiration_date_raw"));
        editOptionalVal.setText(successfulMrzScan.getString("optionals"));

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1 && resultCode == RESULT_OK) {
                String requiredValue = data.getStringExtra("key");
                JSONObject jsonObj = new JSONObject(requiredValue);

                addResultToEditText(jsonObj);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Activity Result Failed",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == scanBtn) {
            startScanner();
        }
//        if (view==imgBtnFront){
//            REQUEST_IMAGE_SCAN=2;
//            startImageScan();
//        }
//        if ( view==imgBtnBack)
//        {
//            REQUEST_IMAGE_SCAN=3;
//            startImageScan();
//        }
        if (view==backBtn){
            startActivity(new Intent(this,MainActivity.class));
        }
        if (view==proceedBtn){
            startActivity(new Intent(this,ProceedToCheckInActivity.class));
        }
    }
//
//    private void startImageScan() {
//        Intent intent = new Intent(getApplicationContext(),ScannerActivity.class);
//        intent.putExtra("imagekey",REQUEST_IMAGE_SCAN);
//        startActivity(intent);
//    }

    private void startScanner() {
        startActivityForResult(new Intent(getApplicationContext(),ScannerActivity.class),REQUEST_CODE);
    }



}
