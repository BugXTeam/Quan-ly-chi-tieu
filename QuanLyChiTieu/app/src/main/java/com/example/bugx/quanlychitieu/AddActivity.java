package com.example.bugx.quanlychitieu;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.SimpleTimeZone;

public class AddActivity extends AppCompatActivity {

    private Spinner spnCategory;
    private TextView editDate;
    private Button buttonDate;
    private TextView editTime;
    private Button buttonTime;
    private String am_pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        spnCategory = (Spinner) findViewById(R.id.spinnerList);

        List<String> list = new ArrayList<>();
        list.add("Ăn uống");
        list.add("Mua sắm");
        list.add("Di chuyển");
        list.add("Hóa đơn điện");
        list.add("Hóa đơn nước");
        list.add("Giải trí");
        list.add("Du lịch");
        list.add("Sức khỏe");
        list.add("Giáo dục");
        list.add("Đầu tư");
        list.add("Bảo hiểm");
        list.add("Internet");
        list.add("Thẻ điện thoại");
        list.add("Làm đẹp");
        list.add("Khoản chi khác");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnCategory.setAdapter(adapter);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(AddActivity.this, spnCategory.getSelectedItem().toString(), Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editDate = (TextView) findViewById(R.id.textDate);
        buttonDate = (Button) findViewById(R.id.button_date);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });
        editTime = (TextView) findViewById(R.id.textTime);
        buttonTime = (Button) findViewById(R.id.button_time);
        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonGio();
            }
        });
    }

    public void previous(View view){
        startActivity(new Intent(AddActivity.this, MainActivity.class));
    }

    private void ChonNgay(){
        final Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                editDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private void ChonGio(){
        final Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                if(hourOfDay >= 12){
                    am_pm = "PM";
                }
                else {
                    am_pm = "AM";
                }
//                calendar.set(hourOfDay, minute);
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                editTime.setText(String.format("%02d:%02d ", hourOfDay, minute) + am_pm);
//                editTime.setText(hourOfDay + ":" + minute);

            }
        }, hourOfDay, minute, false);
        timePickerDialog.show();
    }


}
