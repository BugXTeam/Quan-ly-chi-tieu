package moneyapp.com.quanlytienbac;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static Database database;
    ListView listView;
    private FloatingActionButton floatingActionButton;
    static ArrayList<Item> arrayList;
    static ItemAdapter itemAdapter;
    static String databaseName="Items3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        itemAdapter = new ItemAdapter(this, R.layout.listview_custom, arrayList);
        listView.setAdapter(itemAdapter);

        floatingActionButton = findViewById(R.id.floatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });

        database = new Database(this, "quanly5", null, 1);
        database.queryData("create table if not exists '"+databaseName+"'(Id integer primary key autoincrement,Ten nvarchar(70),Ngay nvarchar(50),Tien varchar(50))");
        //database.queryData("insert into Item values(null,'khang','20/11',2000)");
        //database.queryData("delete from Item");

        selectData();

    }

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuAdd) {
            startActivity(new Intent(MainActivity.this, AddActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void dialog(final int id, final String ten, final String ngay, final String gia)
    {
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit);
        final EditText edt1,edt2,edt3;
        Button bt1;
        edt1=(EditText)dialog.findViewById(R.id.edtMota);
        edt2=(EditText) dialog.findViewById(R.id.edtNgay);
        edt3=(EditText)dialog.findViewById(R.id.edtGiaDialog);
        bt1=(Button) dialog.findViewById(R.id.btAddDialog);

        edt1.setText(ten);
        edt2.setText(ngay);
        edt3.setText(gia+"");

        edt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            AddActivity.ChonNgay(edt2,MainActivity.this);
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi=edt1.getText().toString().trim();
                String ngayMoi=edt2.getText().toString().trim();
                String giaMoi=edt3.getText().toString();
                if(tenMoi.equals("") && ngayMoi.equals("") && giaMoi.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Vui lòng cập nhật đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    database.queryData("update '"+databaseName+"' set Ten='"+tenMoi+"',Ngay='"+ngayMoi+"',Tien='"+giaMoi+"' where Id='"+id+"'");
                    dialog.dismiss();
                    selectData();
                }

            }
        });

        dialog.show();

    }
    public void dialogXoa(String ten, final int id)
    {
        final AlertDialog.Builder dialogXoa=new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa "+ten+" không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.queryData("delete from '"+databaseName+"' where Id='"+id+"'");
                Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                selectData();

            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        dialogXoa.show();
    }
}
