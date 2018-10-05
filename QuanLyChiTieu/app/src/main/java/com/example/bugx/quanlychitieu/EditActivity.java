package com.example.bugx.quanlychitieu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    private Spinner spnCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

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
                Toast.makeText(EditActivity.this, spnCategory.getSelectedItem().toString(), Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void previous(View view){
        startActivity(new Intent(EditActivity.this, MainActivity.class));
    }
}
