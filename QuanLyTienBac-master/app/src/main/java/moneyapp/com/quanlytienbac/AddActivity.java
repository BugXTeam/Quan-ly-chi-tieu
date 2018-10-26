package moneyapp.com.quanlytienbac;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    EditText edt1,edt2,edt3;
    Button bt1;
    public void mappingID()
    {
        edt1=(EditText) findViewById(R.id.edtMota);
        edt2=(EditText) findViewById(R.id.edtNgay);
        edt3=(EditText) findViewById(R.id.edtGia);
        bt1=(Button) findViewById(R.id.btAdd);
    }
    public static void ChonNgay(final EditText edt, Context context){
        final Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edt.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mappingID();

        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        edt2.setText(simpleDateFormat.format(calendar.getTime()));

        edt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay(edt2,AddActivity.this);
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpName=edt1.getText().toString();
                String tmpDate=edt2.getText().toString();
                String tmpPrice=edt3.getText().toString();
                if (tmpName.equals("") || tmpDate.equals("") || tmpPrice.equals(""))
                {
                    Toast.makeText(AddActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {

                    MainActivity.database.queryData("insert into '"+MainActivity.databaseName+"' values(null,'"+tmpName+"','"+tmpDate+"','"+tmpPrice+"')");
                    Toast.makeText(AddActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    MainActivity.selectData();
                    startActivity(new Intent(AddActivity.this,MainActivity.class));
                }

            }
        });


    }

    public void previous(View view){
        startActivity(new Intent(AddActivity.this, MainActivity.class));
    }


    public void setNameForEditActivity(int id,String mota,String ngay,int gia)
    {
        edt1.setText(mota);
        edt2.setText(ngay);
        edt3.setText(gia+"");
    }



}
