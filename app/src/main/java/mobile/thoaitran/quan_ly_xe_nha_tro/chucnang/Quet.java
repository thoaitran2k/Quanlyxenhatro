package mobile.thoaitran.quan_ly_xe_nha_tro.chucnang;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import mobile.thoaitran.quan_ly_xe_nha_tro.R;

import java.io.IOException;

import mobile.thoaitran.quan_ly_xe_nha_tro.ui.TTXe;

// main quét
public class Quet extends AppCompatActivity {
    // khai báo
    final String DATABASE_NAME = "data.sqlite";
    SQLiteDatabase database;
    Button btn;
    Button btnt;
    SurfaceView cameraView;
    TextView textView;
    TextView textView2;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;

// kết nối camera
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            break;
        }
    }
    // ánh xạ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quet);
        btn = (Button) findViewById(R.id.btn);
        btnt = (Button) findViewById(R.id.btnt);
        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        textView = (TextView) findViewById(R.id.text_view);
        textView2 = (TextView) findViewById(R.id.text_view2);

        // lấy text từ camera
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("quet", "Detector dependencies are not yet available");
        } else {

            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {

                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(Quet.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });

            // load text sau khi lấy ra
            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {

                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if(items.size() != 0)
                    {
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for(int i =0;i<items.size();++i)
                                {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("");
                                }
                                // gáng text lên textview
                                if(stringBuilder.toString().length()<13){
                                    textView.setText(stringBuilder.toString());
                                }
                                // lấy text hiển thị lên editText
                                btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    String text = textView.getText().toString();
                                    String e = ".";
                                    String f = "-";
                                    StringBuffer sb = new StringBuffer(text);
                                        if (sb.length()> 3) {
                                            sb.replace(2, 3, f);
                                        }
                                        if (sb.length()> 9){
                                            sb.delete(5, 6);
                                        }
                                        if (sb.length()> 12){
                                        StringBuffer sc = new StringBuffer(sb);
                                        sc.replace(8, 9, e);
                                        textView2.setText(sc);
                                    }else {
                                        textView2.setText(sb);
                                }
                                    }
                                });
                                // dùng text trên editText để tra
                                btnt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String bso = textView2.getText().toString();
                                        int lengh = textView2.length();
                                        if (lengh == 9) {
                                            Intent intent = new Intent(Quet.this, TTXe.class);
                                            intent.putExtra("BS", bso);
                                            startActivity(intent);
                                        }else if (lengh == 11){
                                            Intent intent = new Intent(Quet.this, TTXe.class);
                                            intent.putExtra("BS", bso);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(Quet.this, "Biển số được chọn chưa đúng định dạng", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            });
        }
    }

}
