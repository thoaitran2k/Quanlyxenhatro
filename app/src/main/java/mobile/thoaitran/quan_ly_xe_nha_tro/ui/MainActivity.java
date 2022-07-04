package mobile.thoaitran.quan_ly_xe_nha_tro.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import mobile.thoaitran.quan_ly_xe_nha_tro.R;

import mobile.thoaitran.quan_ly_xe_nha_tro.chucnang.Tra;
import mobile.thoaitran.quan_ly_xe_nha_tro.chucnang.Quet;

// main chính
public class MainActivity extends AppCompatActivity {
    //khai báo các button
    Button btnquet;
    Button btnds;
    Button btnthoat;
    Button btnnhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ánh xạ và gáng lệnh thực thi
        
        // chuyển qa main quét
        btnquet = (Button) findViewById(R.id.btnquet);
        btnquet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Quet.class);
                startActivity(intent);
            }
        });
        // chuyển qa main danh sách
        btnds = (Button) findViewById(R.id.btnds);
        btnds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DSXe.class);
                startActivity(intent);
            }
        });
        // chuyển qa main nhập
        btnnhap = (Button) findViewById(R.id.btnnhap);
        btnnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Tra.class);
                startActivity(intent);
            }
        });
        // thoát
        btnthoat = (Button) findViewById(R.id.btnthoat);
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.exit);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có thật sự muốn thoát?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }
}
