package com.example.hotelsampleapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.scansolutions.mrzscannerlib.MRZResultModel;
import com.scansolutions.mrzscannerlib.MRZScanner;
import com.scansolutions.mrzscannerlib.MRZScannerListener;
import com.scansolutions.mrzscannerlib.ScannerType;

import org.json.JSONException;
import org.json.JSONObject;


public class ScannerActivity extends AppCompatActivity implements MRZScannerListener {
    MRZScanner mrzScanner;
    JSONObject successfulMrzScan;
    int imageRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        mrzScanner.setMaxThreads(2);
        mrzScanner = (MRZScanner) getSupportFragmentManager().findFragmentById(R.id.scannerFragment);
        getScannerTypeKey();

            }

            public void getScannerTypeKey(){
                Intent intent = getIntent();
                imageRequest = intent.getIntExtra("imagekey",-1);
                if(imageRequest==2 || imageRequest==3)
                     {
                    mrzScanner.setScannerType(ScannerType.SCANNER_TYPE_DOC_IMAGE_ID);
                     }
                else
                    {
                    mrzScanner.setScannerType(ScannerType.SCANNER_TYPE_MRZ);
                    }
            }

            @Override
            public void successfulScanWithResult(MRZResultModel mrzResultModel) {
                successfulMrzScan = new JSONObject();
                try {
                    successfulMrzScan.put("document_type_raw", mrzResultModel.document_type_raw);
                    successfulMrzScan.put("document_type_readable", mrzResultModel.document_type_readable);
                    successfulMrzScan.put("issuing_country", mrzResultModel.issuing_country);
                    successfulMrzScan.put("surname", mrzResultModel.surnamesReadable());
                    successfulMrzScan.put("document_number", mrzResultModel.document_number);
                    successfulMrzScan.put("nationality", mrzResultModel.nationality);
                    successfulMrzScan.put("dob_raw", mrzResultModel.dob_raw);
                    successfulMrzScan.put("dob_readable", mrzResultModel.dob_readable);
                    successfulMrzScan.put("sex", mrzResultModel.sex);
                    successfulMrzScan.put("est_issuing_date_raw", mrzResultModel.est_issuing_date_raw);
                    successfulMrzScan.put("est_issuing_date_readable", mrzResultModel.est_issuing_date_readable);
                    successfulMrzScan.put("expiration_date_raw", mrzResultModel.expiration_date_raw);
                    successfulMrzScan.put("expiration_date_readable", mrzResultModel.expiration_date_readable);
                    successfulMrzScan.put("given_names_readable", mrzResultModel.givenNamesReadable());
                    successfulMrzScan.put("optionals", mrzResultModel.optionalsReadable());

                    sendResultMrz(successfulMrzScan);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void successfulScanWithDocumentImage(Bitmap bitmap) {
//                sendResultImage(bitmap);
            }
           @Override
            public void scanImageFailed() {

            }

            @Override
            public void permissionsWereDenied() {

            }
//            private void sendResultImage(Bitmap bitmap) {
//                Intent intent = new Intent(this, CheckInActivity.class);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                if(imageRequest==2){
//                    intent.putExtra("frontIDImage", byteArray);
//                }
//                if(imageRequest==3){
//                    intent.putExtra("backIDImage", byteArray);
//                }
//                startActivity(intent);
//            }

            private void sendResultMrz(JSONObject successfulMrzScan) {
                Intent intent = getIntent();
                intent.putExtra("key", successfulMrzScan.toString());
                setResult(RESULT_OK, intent);
                finish();
            }

         

            @Override
            public void onBackPressed() {
                super.onBackPressed();
            }


}
