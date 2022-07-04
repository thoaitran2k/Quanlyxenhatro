package mobile.thoaitran.quan_ly_xe_nha_tro.datbase;

// khai báo các biến toàn cục
public class ThanhVien {
    public int id;
    public String sophong;
    public String ten;
    public String bienso;
    public String sdt;
    public String ghichu;
    public byte[] anh;

    public ThanhVien(int id, String sophong, String ten, String bienso, String sdt, String ghichu, byte[] anh) {
        this.id = id;
        this.sophong = sophong;
        this.ten = ten;
        this.bienso = bienso;
        this.sdt = sdt;
        this.ghichu = ghichu;
        this.anh = anh;
    }
}
