package id.tempayan.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAgama {
    @SerializedName("semuaagama")
    private List<SemuaAgamaItem> semuaagama;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public void setSemuaagama(List<SemuaAgamaItem> semuaagama){
        this.semuaagama = semuaagama;
    }

    public List<SemuaAgamaItem> getSemuaagama(){
        return semuaagama;
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
                "ResponseAgama{" +
                        "semuaagama = '" + semuaagama + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}
