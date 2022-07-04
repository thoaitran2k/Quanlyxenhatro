package mobile.thoaitran.quan_ly_xe_nha_tro.datbase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import mobile.thoaitran.quan_ly_xe_nha_tro.R;

import java.util.ArrayList;

import mobile.thoaitran.quan_ly_xe_nha_tro.chucnang.edit;

// main adapter

public class AdapterThanhVien extends BaseAdapter {
    // gọi lớp kế thừa
    Activity context;
    // tạo danh sách
    ArrayList<ThanhVien> list;

    public AdapterThanhVien(Activity context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    // lấy dữ liệu set lên danh sách
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listview, null);
        ImageView imgHinh = (ImageView) row.findViewById(R.id.imgHinh);
        TextView txtSoPhong = (TextView) row.findViewById(R.id.txtSoPhong);
        TextView txtBienSo = (TextView) row.findViewById(R.id.txtBienSo);
        TextView txtTen = (TextView) row.findViewById(R.id.txtTen);
        TextView txtGhiChu = (TextView) row.findViewById(R.id.txtGhiChu);
        TextView txtSDT = (TextView) row.findViewById(R.id.txtSDT);
        Button btnSua = (Button) row.findViewById(R.id.btnSua);
        Button btnXoa = (Button) row.findViewById(R.id.btnXoa);

        final ThanhVien thanhVien = list.get(position);
        txtSoPhong.setText(thanhVien.sophong);
        txtBienSo.setText(thanhVien.bienso);
        txtTen.setText(thanhVien.ten);
        txtSDT.setText(thanhVien.sdt);
        txtGhiChu.setText(thanhVien.ghichu);

        Bitmap bmHinh = BitmapFactory.decodeByteArray(thanhVien.anh, 0, thanhVien.anh.length);
        imgHinh.setImageBitmap(bmHinh);
        // chuyển qa main sửa sau khi nhấn button
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
                Intent intent = new Intent(context, edit.class);
                intent.putExtra("ID", thanhVien.id);
                context.startActivity(intent);


            }
        });
        // xóa thành viên
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hộp thoại thông báo có muốn xóa không?
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa thành viên này không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(thanhVien.id);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return row;
    }
    // sự kiện xóa thành viên tại vị trí nhấm
    private void delete(int idThanhVien) {
        SQLiteDatabase database = Database.initDatabase(context,"data.sqlite");
        database.delete("ThanhVien", "ID = ?", new String[]{idThanhVien + ""});
        list.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM ThanhVien", null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String sophong = cursor.getString(1);
            String ten = cursor.getString(2);
            String bienso = cursor.getString(3);
            String sdt = cursor.getString(4);
            String ghichu = cursor.getString(5);
            byte[] anh = cursor.getBlob(6);

            list.add(new ThanhVien(id, sophong, ten, bienso, sdt, ghichu, anh));
        }
        notifyDataSetChanged();
    }
}

