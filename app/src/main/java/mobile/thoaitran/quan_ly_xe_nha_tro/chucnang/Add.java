package mobile.thoaitran.quan_ly_xe_nha_tro.chucnang;

import android.content.ContentValues;
import android.content.Intent;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import static mobile.thoaitran.quan_ly_xe_nha_tro.R.id.img1;

import mobile.thoaitran.quan_ly_xe_nha_tro.R;

import mobile.thoaitran.quan_ly_xe_nha_tro.datbase.Database;
import mobile.thoaitran.quan_ly_xe_nha_tro.ui.DSXe;


// main thêm giống main sửa

public class Add extends AppCompatActivity {
     // kết nối database
    final String DATABASE_NAME = "data.sqlite";
    // khai báo chụp và chọn ảnh
    final int RESQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;
    // khai báo các button, editText, image
    Button btnThem, btnHuy, btnchon, btnchup;
    EditText editTen, editSDT, editSP, editBS, editGHICHU;
    ImageView imgh1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        addControls();
        addEvents();
    }
    // ánh xạ các địa chỉ
    private void addControls() {
        imgh1 = (ImageView) findViewById(img1);
        btnchon = (Button) findViewById(R.id.btn1);
        btnchup = (Button) findViewById(R.id.btn2);
        btnThem = (Button) findViewById(R.id.btnThem);
        btnHuy = (Button) findViewById(R.id.btnHuy);

        editSP = (EditText) findViewById(R.id.editSP);
        editTen = (EditText) findViewById(R.id.editTen);
        editBS = (EditText) findViewById(R.id.editBS);
        editSDT = (EditText) findViewById(R.id.editSDT);
        editGHICHU = (EditText) findViewById(R.id.editDD);
    }
    // bắt các sự kiện
    private void addEvents() {
        btnchon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        btnchup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
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
                    imgh1.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == RESQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgh1.setImageBitmap(bitmap);
            }

        }
    }
    // lấy thông tin trên editText để thêm vào csdl sau khi nhấn vào button thêm
    private void insert(){
        String sophong = editSP.getText().toString();
        String ten = editTen.getText().toString();
        String bienso = editBS.getText().toString();
        String sdt = editSDT.getText().toString();
        String ghichu = editGHICHU.getText().toString();
        byte[] anh = getByteArrayFromImageView(imgh1);
        ContentValues contentValues = new ContentValues();
        int lengh = sophong.length();
        if (lengh >= 1 ) {
            contentValues.put("SOPHONG", sophong);
            Toast.makeText(Add.this, "Thêm thành công ",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(Add.this, "Lỗi nhập số phòng ",Toast.LENGTH_LONG).show();
        }
        int lengh1 = ten.length();
        if (lengh1 >= 1) {
            contentValues.put("TEN", ten);
        }else {
            Toast.makeText(Add.this, "Lỗi nhập tên ",Toast.LENGTH_LONG).show();
        }
        int lengh2 = bienso.length();
        if (lengh2 == 9) {
            contentValues.put("BIENSO", bienso);
        }else if (lengh2 == 10){
            contentValues.put("BIENSO", bienso);
        }else if (lengh2 == 11){
            contentValues.put("BIENSO", bienso);
        }else {
            Toast.makeText(Add.this, "Lỗi nhập biển số ",Toast.LENGTH_LONG).show();
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
            Toast.makeText(Add.this, "Lỗi nhập sđt ",Toast.LENGTH_LONG).show();
        }
            // ghi chú và ảnh có cũng đc, k có cũng đc
        contentValues.put("GHICHU", ghichu);
        contentValues.put("Anh", anh);
        SQLiteDatabase database = Database.initDatabase(this, "data.sqlite");
        database.insert("ThanhVien",null, contentValues);
        finish();
        // sau khi lưu sẽ chuyển qa main danh sách
        Intent intent = new Intent(this, DSXe.class);
        startActivity(intent);

    }
    // sau khi nhấn button hủy sẽ quay lại main danh sách
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
