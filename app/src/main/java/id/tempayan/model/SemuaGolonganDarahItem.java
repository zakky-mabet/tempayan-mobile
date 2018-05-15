package id.tempayan.model;

import com.google.gson.annotations.SerializedName;

public class SemuaGolonganDarahItem {

    @SerializedName("nama_gol")
    private String nama_gol;

    @SerializedName("id")
    private String id;


    public void setNama_gol(String nama){
        this.nama_gol = nama;
    }

    public String getNama_gol(){
        return nama_gol;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }


    @Override
    public String toString(){
        return
                "SemuaGolonganDarahItem{" +
                        "nama = '" + nama_gol + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}
