package mobile.thoaitran.quan_ly_xe_nha_tro.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import static mobile.thoaitran.quan_ly_xe_nha_tro.R.id.btnAdd;

import mobile.thoaitran.quan_ly_xe_nha_tro.R;

import mobile.thoaitran.quan_ly_xe_nha_tro.chucnang.Add;
import mobile.thoaitran.quan_ly_xe_nha_tro.datbase.ThanhVien;
import mobile.thoaitran.quan_ly_xe_nha_tro.datbase.AdapterThanhVien;
import mobile.thoaitran.quan_ly_xe_nha_tro.datbase.Database;

// main danh sách

public class DSXe extends AppCompatActivity {
    // kết nối database
    final String DATABASE_NAME = "data.sqlite";
    SQLiteDatabase database;
    // kahi báo
    ListView listView;
    ArrayList<ThanhVien> list;
    AdapterThanhVien adapter;
    Button buttonThem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        addControls();
        readData();
    }
    // ánh xạ
    private void addControls() {
        buttonThem = (Button) findViewById(btnAdd);
        buttonThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(DSXe.this, Add.class);
                startActivity(intent);
            }
        });
        // sau khi nhấn button thêm sẽ chuyển qa main thêm
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new AdapterThanhVien(this, list);
        listView.setAdapter(adapter);
    }
    // đọc dữ liệu trong csdl load lên danh sách
    private void readData(){
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM ThanhVien",null);
        list.clear();
        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String sophong = cursor.getString(1);
            String ten = cursor.getString(2);
            String bienso = cursor.getString(3);
            String sdt = cursor.getString(4);
            String ghichu = cursor.getString(5);
            byte[] anh = cursor.getBlob(6);

            list.add(new ThanhVien(id, sophong, ten, bienso, sdt, ghichu, anh));
        }
        adapter.notifyDataSetChanged();
    }
}
