package mobile.thoaitran.quan_ly_xe_nha_tro.chucnang;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import mobile.thoaitran.quan_ly_xe_nha_tro.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import mobile.thoaitran.quan_ly_xe_nha_tro.datbase.Database;
import mobile.thoaitran.quan_ly_xe_nha_tro.ui.DSXe;

// main sửa

public class edit extends AppCompatActivity {
    // kết nối database
    final String DATABASE_NAME = "data.sqlite";

    // khai báo chụp và chọn ảnh
    final int RESQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;
    // khai báo biến i toàn cục
    int id = -1;
    // khai báo các button, editText, image
    Button btnChon, btnChup, btnLuu, btnHuy;
    EditText editTen, editSDT, editSP, editBS, editGHICHU;
    ImageView imgHinh1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        addControl();
        initUI();
        addEvents();
    }
    // vị trí trong danh sách và lấy dữ liệu ra
    private void initUI() {
            Intent intent = getIntent();
            id = intent.getIntExtra("ID", -1);
            SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
            Cursor cursor = database.rawQuery("SELECT * FROM ThanhVien WHERE ID = ? ",new String[]{id + ""});
            cursor.moveToFirst();
            String sophong = cursor.getString(1);
            String ten = cursor.getString(2);
            String bienso = cursor.getString(3);
            String sdt = cursor.getString(4);
            String ghichu = cursor.getString(5);
            byte [] anh1 = cursor.getBlob(6);
            Bitmap bitmap = BitmapFactory.decodeByteArray(anh1, 0, anh1.length);
            imgHinh1.setImageBitmap(bitmap);
            editSP.setText(sophong);
            editTen.setText(ten);
            editBS.setText(bienso);
            editSDT.setText(sdt);
            editGHICHU.setText(ghichu);
    }
        // ánh xạ các địa chỉ
    private void addControl() {
        btnChon = (Button) findViewById(R.id.btnChon);
        btnChup = (Button) findViewById(R.id.btnChup);
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);
        editSP = (EditText) findViewById(R.id.editSP);
        editTen = (EditText) findViewById(R.id.editTen);
        editBS = (EditText) findViewById(R.id.editBS);
        editSDT = (EditText) findViewById(R.id.editSDT);
        editGHICHU = (EditText) findViewById(R.id.editDD);
        imgHinh1 = (ImageView) findViewById(R.id.imgHinh1);
    }
    // bắt các sự kiện
    private void addEvents() {
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        btnChup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               update();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

    }
    // khai báo sự kiện chụp và chọn ảnh khi nhấn button
    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RESQUEST_TAKE_PHOTO);
    }
    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK );
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }
    // tạo sự kiện khi button chụp và chọn 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {

                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgHinh1.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == RESQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgHinh1.setImageBitmap(bitmap);
            }

        }
    }
    // lấy thông tin trên editText để cập nhật vào csdl sau khi nhấn vào button lưu
    private void update(){
        String sophong = editSP.getText().toString();
        String ten = editTen.getText().toString();
        String bienso = editBS.getText().toString();
        String sdt = editSDT.getText().toString();
        String ghichu = editGHICHU.getText().toString();
        byte[] anh = getByteArrayFromImageView(imgHinh1);

        ContentValues contentValues = new ContentValues();
        int lengh = sophong.length();
        if (lengh >= 1 ) {
            contentValues.put("SOPHONG", sophong);
            Toast.makeText(edit.this, "Sửa thành công ",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(edit.this, "Lỗi nhập số phòng ",Toast.LENGTH_LONG).show();
        }
        int lengh1 = ten.length();
        if (lengh1 >= 1) {
            contentValues.put("TEN", ten);
        }else {
            Toast.makeText(edit.this, "Lỗi nhập tên ",Toast.LENGTH_LONG).show();
        }
        int lengh2 = bienso.length();
        if (lengh2 == 9) {
            contentValues.put("BIENSO", bienso);
        }else if (lengh2 == 10){
            contentValues.put("BIENSO", bienso);

        }else if (lengh2 == 11){
            contentValues.put("BIENSO", bienso);

        }
        else {
            Toast.makeText(edit.this, "Lỗi nhập biển số ",Toast.LENGTH_LONG).show();
        }
        int lengh3 = sdt.length();
        if (lengh3  == 10  ) {
            contentValues.put("SDT", sdt);
        }else if (lengh3 == 11){
            contentValues.put("SDT", sdt);
        }
        else if (lengh3 == 12){
            contentValues.put("SDT", sdt);
        }
        else if (lengh3 == 13){
            contentValues.put("SDT", sdt);
        }
        else if (lengh3 == 14){
            contentValues.put("SDT", sdt);
        }else {
            Toast.makeText(edit.this, "Lỗi nhập sđt ",Toast.LENGTH_LONG).show();
        }
        // ghi chú và ảnh có cũng đc, k có cũng đc
        contentValues.put("GHICHU", ghichu);
        contentValues.put("Anh", anh);
        SQLiteDatabase database = Database.initDatabase(this, "data.sqlite");
        database.update("ThanhVien", contentValues, "id = ?", new String[] {id + ""});
        // sau khi lưu sẽ chuyển qa main danh sách
        finish();
        Intent intent = new Intent(this, DSXe.class);
        startActivity(intent);
    }
        // sau khi nhấn button thoát sẽ quay lại main danh sách
    private void cancel(){
        finish();
        Intent intent = new Intent(this, DSXe.class);
        startActivity(intent);
    }
    // lấy ảnh set lên giao diện
    private byte[] getByteArrayFromImageView(ImageView imgv){

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}
