package id.tempayan.model;

import com.google.gson.annotations.SerializedName;

public class SemuaKelurahanItem {

    @SerializedName("nama")
    private String nama;

    @SerializedName("id")
    private String id;


    public void setNama(String nama){
        this.nama = nama;
    }

    public String getNama(){
        return nama;
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
                "SemuaKelurahanItem{" +
                        "nama = '" + nama + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}
