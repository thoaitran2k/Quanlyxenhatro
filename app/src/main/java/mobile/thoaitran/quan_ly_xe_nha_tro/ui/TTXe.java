package mobile.thoaitran.quan_ly_xe_nha_tro.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import mobile.thoaitran.quan_ly_xe_nha_tro.R;

import mobile.thoaitran.quan_ly_xe_nha_tro.datbase.Database;

// main thông tin
public class TTXe extends AppCompatActivity {
    // kết nối csdl
    final String DATABASE_NAME = "data.sqlite";
    // khai báo
    EditText editTen2, editSDT2, editSP2, editBS2, editGHICHU2;
    ImageView imgHinh2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        adđControl1();
        initUI1();

    }
// vị trí sau khi tra ra trong danh sách và lấy dữ liệu ra
    private void initUI1() {
        Intent intent = this.getIntent();
        String BS = intent.getStringExtra("BS");
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM ThanhVien WHERE BIENSO = ? ", new String[]{BS + ""});
        cursor.moveToFirst();
        String sophong = cursor.getString(1);
        String ten = cursor.getString(2);
        String bienso = cursor.getString(3);
        boolean d = bienso.equals(BS);
        if (d == true) {
            String sdt = cursor.getString(4);
            String ghichu = cursor.getString(5);
            byte[] anh1 = cursor.getBlob(6);
            Bitmap bitmap = BitmapFactory.decodeByteArray(anh1, 0, anh1.length);
            imgHinh2.setImageBitmap(bitmap);
            editSP2.setText(sophong);
            editTen2.setText(ten);
            editBS2.setText(bienso);
            editSDT2.setText(sdt);
            editGHICHU2.setText(ghichu);
        }else {
            finish();
            Toast.makeText(TTXe.this, "Lỗi nhập biển số ",Toast.LENGTH_LONG).show();
        }
    }
    // ánh xạ
    private void adđControl1() {

        editSP2 = (EditText) findViewById(R.id.editSP2);
        editTen2 = (EditText) findViewById(R.id.editTen2);
        editBS2 = (EditText) findViewById(R.id.editBS2);
        editSDT2 = (EditText) findViewById(R.id.editSDT2);
        editGHICHU2 = (EditText) findViewById(R.id.editDD2);
        imgHinh2 = (ImageView) findViewById(R.id.imgHinh2);
    }

}
