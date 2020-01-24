package com.example.hotelsampleapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    Button button_check_in;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_check_in = findViewById(R.id.login_btn);

        button_check_in.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        if(view==button_check_in)
        {
            startActivity(new Intent(getApplicationContext(), CheckInActivity.class));
        }
    }
}
