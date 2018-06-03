package id.tempayan.response;

import com.google.gson.annotations.SerializedName;

public class RiwayatItem {

    @SerializedName("id")
    private String id;

    @SerializedName("nik")
    private String nik;

    @SerializedName("kode_surat")
    private  String kode_surat;

    @SerializedName("kode_surat_sistem")
    private  String kode_surat_sistem;

    @SerializedName("nama_surat")
    private String nama_surat;

    @SerializedName("tahun")
    private  String tahun;

    @SerializedName("waktu_mulai")
    private String waktu_mulai;

    @SerializedName("waktu_selesai")
    private String waktu_selesai;

    @SerializedName("status")
    private String status;

    @SerializedName("real_status")
    private String real_status;

    @SerializedName("ID_pelayanan")
    private String ID_pelayanan;


    public void setId(String id) {
        this.id = id;
    }
    public String getId(){
        return id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getKode_surat() {
        return kode_surat;
    }

    public void setKode_surat(String kode_surat) {
        this.kode_surat = kode_surat;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getWaktu_mulai() {
        return waktu_mulai;
    }

    public void setWaktu_mulai(String waktu_mulai) {
        this.waktu_mulai = waktu_mulai;
    }

    public String getWaktu_selesai() {
        return waktu_selesai;
    }

    public void setWaktu_selesai(String waktu_selesai) {
        this.waktu_selesai = waktu_selesai;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getID_pelayanan() {
        return ID_pelayanan;
    }

    public void setID_pelayanan(String ID_pelayanan) {
        this.ID_pelayanan = ID_pelayanan;
    }

    public String getNama_surat() {
        return nama_surat;
    }

    public void setNama_surat(String nama_surat) {
        this.nama_surat = nama_surat;
    }

    public String getReal_status() {
        return real_status;
    }

    public void setReal_status(String real_status) {
        this.real_status = real_status;
    }

    public String getKode_surat_sistem() {
        return kode_surat_sistem;
    }

    public void setKode_surat_sistem(String kode_surat_sistem) {
        this.kode_surat_sistem = kode_surat_sistem;
    }

    @Override
    public String toString(){
        return
                "RiwayatItem{" +
                        "id = '" + id + '\'' +
                        ",nik = '" + nik + '\'' +
                        ",kode_surat = '" + kode_surat + '\'' +
                        ",kode_surat_sistem = '" + kode_surat_sistem + '\'' +
                        ",tahun = '" + tahun + '\'' +
                        ",nama_surat = '" + nama_surat + '\'' +
                        ",real_status = '" + real_status + '\'' +
                        ",waktu_mulai = '" + waktu_mulai + '\'' +
                        ",waktu_selesai = '" + waktu_selesai + '\'' +
                        ",status = '" + status + '\'' +
                        ",ID_pelayanan = '" + ID_pelayanan + '\'' +
                        "}";
    }


}
