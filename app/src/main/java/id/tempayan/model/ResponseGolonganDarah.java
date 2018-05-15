package id.tempayan.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGolonganDarah {
    @SerializedName("semuagolongandarah")
    private List<SemuaGolonganDarahItem> semuagolongandarah;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public void setSemuagolongandarah(List<SemuaGolonganDarahItem> semuagolongandarah){
        this.semuagolongandarah = semuagolongandarah;
    }

    public List<SemuaGolonganDarahItem> getSemuagolongandarah(){
        return semuagolongandarah;
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
                "ResponseGolonganDarah{" +
                        "semuagolongandarah = '" + semuagolongandarah + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}
