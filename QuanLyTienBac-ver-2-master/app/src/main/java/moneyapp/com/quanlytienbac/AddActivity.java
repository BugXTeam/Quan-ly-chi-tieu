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

    //Hàm chọn ngày dạng Dialog
    public static void ChonNgay(final EditText edt, Context context){
        final Calendar calendar = Calendar.getInstance();   //Tạo lịch
        int dayOfMonth = calendar.get(Calendar.DATE);   //Tạo biến ngày, tháng, năm
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {    //Tạo mới sự kiện chọn ngày
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) { //Sự kiện chọn ngày
                calendar.set(year, month, dayOfMonth);  //Khởi tạo ngày tháng năm hiện tại làm mặc định
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy"); //Format ngày, tháng, năm dạng "dd/MM/yyyy"
                edt.setText(simpleDateFormat.format(calendar.getTime()));   //Đưa giá trị ngày, tháng, năm vào EditText
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();    //Hiển thị bảng lịch chọn ngày, tháng, năm
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mappingID();

        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        edt2.setText(simpleDateFormat.format(calendar.getTime()));

        edt2.setOnClickListener(new View.OnClickListener() {    //Tạo sự kiện chọn ngày, tháng, năm
            @Override
            public void onClick(View v) {
                ChonNgay(edt2,AddActivity.this);   //Gọi hàm chọn ngày dialog
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpName=edt1.getText().toString();
                String tmpDate=edt2.getText().toString();
                String tmpPrice=edt3.getText().toString();
                if (tmpName.equals("") || tmpDate.equals("") || tmpPrice.equals(""))    //Nếu 1 trường bị bỏ trống
                {
                    Toast.makeText(AddActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();  //Hiển thị thông báo trên
                }
                else {  //Nếu không có giá trị nào bỏ trống

                    MainActivity.database.queryData("insert into '"+MainActivity.databaseName+"' values(null,'"+tmpName+"','"+tmpDate+"','"+tmpPrice+"')"); //Insert dữ liệu vào database
                    Toast.makeText(AddActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    MainActivity.selectData();  //Refresh dữ liệu ở màn hình MainActivity
                    finish();   //Kết thúc sự kiện thêm
                    startActivity(new Intent(AddActivity.this,MainActivity.class)); //Quay lại giao diện MainActivity
                }

            }
        });


    }

    //Hàm sự kiện của button "Hủy"
    public void previous(View view){
        finish();   //Dừng sự kiện thêm và quay lại giao diện MainActivity
        //startActivity(new Intent(AddActivity.this, MainActivity.class));
    }


    public void setNameForEditActivity(int id,String mota,String ngay,int gia)
    {
        edt1.setText(mota);
        edt2.setText(ngay);
        edt3.setText(gia+"");
    }



}
