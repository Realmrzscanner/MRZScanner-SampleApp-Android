package com.example.hotelsampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.timessquare.CalendarPickerView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProceedToCheckInActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    CalendarPickerView datePicker;
    Date today;
    Calendar nextYear;
    Button setDate,backBtn,completeBtn;
    FrameLayout frameLayout;
    ScrollView scrollView;
    FloatingActionButton fab;
    String firstDate, lastDate;
    CheckBox cashBox, creditBox;
    LinearLayout linearCredit, linearCash;
    List<Date> selectedDate;
    EditText adavancePay;
    TextView totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed_to_check_in);
        setDate = findViewById(R.id.setDate_btn);
        frameLayout = findViewById(R.id.framelatout_1);
        scrollView = findViewById(R.id.scrollView2);
        fab = findViewById(R.id.floatingActionButton);
        cashBox = findViewById(R.id.cash_ckBox);
        creditBox = findViewById(R.id.creadit_ckBox);
        linearCash = findViewById(R.id.linear_cash);
        linearCredit = findViewById(R.id.linear_credit);
        selectedDate= new ArrayList<>();
        backBtn=findViewById(R.id.back_btn);
        completeBtn=findViewById(R.id.complete_btn);

        backBtn.setOnClickListener(this);
        cashBox.setOnCheckedChangeListener(this);
        creditBox.setOnCheckedChangeListener(this);
        setDate.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    private void launchCalendar() {

        today = new Date();
        nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        datePicker = findViewById(R.id.calendar_view);
        datePicker.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(today);
        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                selectedDate = (ArrayList<Date>) datePicker.getSelectedDates();
                firstDate = selectedDate.get(0).getDate() + "." + selectedDate.get(0).getMonth() + "." + selectedDate.get(0).getYear();
                lastDate = selectedDate.get(selectedDate.size() - 1).getDate() + "." + selectedDate.get(0).getMonth() + "." + selectedDate.get(0).getYear();
                setDate.setText(firstDate + "-" + lastDate);
            }

            @Override
            public void onDateUnselected(Date date) {
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view == setDate) {
            launchCalendar();
            frameLayout.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }
        if (view == fab) {
            frameLayout.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            setDate.setText("From :" + firstDate + "\r\n" +
                    " To " + lastDate);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (compoundButton == cashBox) {
            if (b) {
                linearCash.setVisibility(View.VISIBLE);
                adavancePay = findViewById(R.id.advance_cash_edt);
                totalAmount = findViewById(R.id.totalAmout_txt);
                calculatePrice();
            } else {
                linearCash.setVisibility(View.GONE);
            }
        }
        if (compoundButton == creditBox) {
            if (b) {
                linearCredit.setVisibility(View.VISIBLE);

            } else {
                linearCredit.setVisibility(View.GONE);
            }
        }
    }

    private void calculatePrice() {
        adavancePay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(selectedDate.size() == 0) {
                    adavancePay.setError("Please select range of dates");
                    totalAmount.setText("0");
                }else {
                    String advancePaid = adavancePay.getText().toString();
                    if (advancePaid.matches("")) {
                        return;
                    }
                    int totalPrice = selectedDate.size() * 20;
                    totalAmount.setText(String.valueOf(totalPrice - Integer.parseInt(advancePaid)));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
