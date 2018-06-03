package id.tempayan.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponRiwayat {

    @SerializedName("riwayat")
    private List<RiwayatItem> riwayat;

    @SerializedName("status")
    private boolean status;

    public void setRiwayat(List<RiwayatItem> riwayat){
        this.riwayat = riwayat;
    }

    public List<RiwayatItem> getRiwayat(){
        return riwayat;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public boolean isStatus(){
        return status;
    }

    @Override
    public String toString(){
        return
                "ResponRiwayat{" +
                        "riwayat = '" + riwayat + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}
