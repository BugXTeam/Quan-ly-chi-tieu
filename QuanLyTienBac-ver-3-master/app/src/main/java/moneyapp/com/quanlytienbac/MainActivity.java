package moneyapp.com.quanlytienbac;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static Database database;
    ListView listView;
    static ArrayList<Item> arrayList;
    static ArrayList<Integer> integerArrayList;
    FloatingActionButton floatingActionButton;
    static ItemAdapter itemAdapter;
    static String databaseName="Items6";
    TextView txt1;
    NumberFormat format = new DecimalFormat("#,##0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1=(TextView) findViewById(R.id.txtPriceSum);
        integerArrayList=new ArrayList<>();

        listView = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        itemAdapter = new ItemAdapter(this, R.layout.listview_custom, arrayList);
        listView.setAdapter(itemAdapter);

        //Gọi vị trí button add và khởi chạy AddActivity
        floatingActionButton = findViewById(R.id.floatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });


        //Tạo database
        database = new Database(this, "quanly8", null, 1);
        database.queryData("create table if not exists '"+databaseName+"'(Id integer primary key autoincrement,Ten nvarchar(70),Ngay nvarchar(50),Tien integer)");
        //database.queryData("insert into Item values(null,'khang','20/11',2000)");
        //database.queryData("delete from Item");

        selectData();
        //Sum tổng tiền hiển thị ra trên màn hình MainActivity
        final int tmpPrice=priceSum();
        txt1.setText(format.format(tmpPrice)+" VNĐ");

//        format.setCurrency(Currency.getInstance(Locale.US));

    }

    //Hàm gọi bảng database
    public static void selectData() {
        Cursor dataItem = database.getData("select * from '"+databaseName+"'");
        arrayList.clear();
        while (dataItem.moveToNext()) {
            int id = dataItem.getInt(0);
            String ten = dataItem.getString(1);
            String ngay = dataItem.getString(2);
            String gia = dataItem.getString(3);
            arrayList.add(new Item(id, ten, ngay, gia));

        }
        itemAdapter.notifyDataSetChanged();
    }

    //Hàm sum tổng tiền
    public static int priceSum()
    {
        int total=0;
        //Câu lệnh sum
        Cursor price=database.getData("select sum(Tien) from '"+databaseName+"'");
        while (price.moveToNext())
        {
            total=price.getInt(0);
            integerArrayList.add(total);
        }
        return integerArrayList.get(0);

    }

    //Khởi tạo menu trên thanh Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Khởi tạo chức năng cho menu trên thanh Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuAdd) {   //Nếu vị trí là menuAdd thì khởi chạy AddActivity
            startActivity(new Intent(MainActivity.this, AddActivity.class));
        }
        else if(id == R.id.menuAbout){  //Nếu vị trí là menuAbout thì khởi chạy AboutActivity
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    //Hàm cập nhật thông tin dạng dialog
    public void dialog(final int id, final String ten, final String ngay, final String gia)
    {
        txt1=(TextView) findViewById(R.id.txtPriceSum); //Gọi vị trí TextView txtPriceSum
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit);
        final EditText edt1,edt2,edt3;
        Button bt1;
        edt1=(EditText)dialog.findViewById(R.id.edtMota);   //Gọi vị trí EditText edtMota
        edt2=(EditText) dialog.findViewById(R.id.edtNgay);  //Gọi vị trí EditText edtNgay
        edt3=(EditText)dialog.findViewById(R.id.edtGiaDialog);  //Gọi vị trí EditText edtGiaDialog
        bt1=(Button) dialog.findViewById(R.id.btAddDialog); //Gọi vị trí EditText edtAddDialog

        //SetText các giá trị
        edt1.setText(ten);
        edt2.setText(ngay);
        edt3.setText(gia+"");

        //Sự kiện chọn ngày
        edt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            AddActivity.ChonNgay(edt2,MainActivity.this);
            }
        });

        //Sự kiện cập nhật thông tin
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi=edt1.getText().toString().trim();
                String ngayMoi=edt2.getText().toString().trim();
                String giaMoi=edt3.getText().toString();
                if(tenMoi.equals("") || ngayMoi.equals("") || giaMoi.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Vui lòng cập nhật đủ thông tin", Toast.LENGTH_SHORT).show(); //Thông báo hiển thị khi có 1 trường bị bỏ trống
//                    int x=priceSum();
//                    txt1.setText(format.format(x)+ " VNĐ");

                }
                else
                {
                    database.queryData("update '"+databaseName+"' set Ten='"+tenMoi+"',Ngay='"+ngayMoi+"',Tien='"+giaMoi+"' where Id='"+id+"'");    //query cập nhật dữ liệu vào trong database
                    dialog.dismiss();   //Tắt màn hình cập nhật dạng dialog
                    Toast.makeText(MainActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    selectData();   //Refresh lại dữ liệu trên màn hình
                    Intent intent = getIntent();
                    finish();   //Dừng sự kiện cập nhật
                    startActivity(intent);
                }

            }
        });

        dialog.show();  //Hiển thị màn hình cập nhật dữ liệu

    }

    //Hàm xóa 1 dữ liệu
    public void dialogXoa(String ten, final int id)
    {
        txt1=(TextView) findViewById(R.id.txtPriceSum);
        final AlertDialog.Builder dialogXoa=new AlertDialog.Builder(this);  //Tạo mới 1 message box
        dialogXoa.setCancelable(true);
        dialogXoa.setMessage("Bạn có muốn xóa "+ten+" không?"); //Hiển thị thông báo
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {   //Tại sự kiện click "có"
            @Override
            public void onClick(DialogInterface dialog, int which) {    //Sự kiện xóa dữ liệu trong database
                database.queryData("delete from '"+databaseName+"' where Id='"+id+"'");
                Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show(); //Hiển thị thông báo xóa thành công
                selectData();   //Refresh lại dữ liệu trên màn hình
//                int x=priceSum();
//                txt1.setText(format.format(x)+ " VNĐ");
                Intent intent = getIntent();
                finish();   //Dừng sự kiện xóa
                startActivity(intent);

            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {    //Tại sự kiện click "không"
            @Override
            public void onClick(DialogInterface dialog, int which) {    //Không làm gì cả
                dialog.cancel();
            }
        });
        //dialogXoa.show();   //Hiển thị màn hình dialogXoa
        AlertDialog xoa = dialogXoa.create();
        xoa.show();
    }

}
