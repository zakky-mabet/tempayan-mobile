package id.tempayan.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseKelurahan {
    @SerializedName("semuakelurahan")
    private List<SemuaKelurahanItem> semuakelurahan;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public void setSemuakelurahan(List<SemuaKelurahanItem> semuakelurahan){
        this.semuakelurahan = semuakelurahan;
    }

    public List<SemuaKelurahanItem> getSemuakelurahan(){
        return semuakelurahan;
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
                "ResponseKelurahan{" +
                        "semuakelurahan = '" + semuakelurahan + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}
