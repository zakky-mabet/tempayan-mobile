package id.tempayan.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseStatusKeluarga {
    @SerializedName("semuastatuskeluarga")
    private List<SemuaStatusKeluargaItem> semuastatuskeluarga;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public void setSemuastatuskeluarga(List<SemuaStatusKeluargaItem> semuastatuskeluarga){
        this.semuastatuskeluarga = semuastatuskeluarga;
    }

    public List<SemuaStatusKeluargaItem> getSemuastatuskeluarga(){
        return semuastatuskeluarga;
    }

    public void setError(boolean error){
        this.error = error;
    }

    public boolean isError(){
        return error;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString(){
        return
                "ResponseStatusKeluarga{" +
                        "semuastatuskeluarga = '" + semuastatuskeluarga + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}
