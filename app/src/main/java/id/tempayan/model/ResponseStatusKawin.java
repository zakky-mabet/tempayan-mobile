package id.tempayan.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseStatusKawin {
    @SerializedName("semuastatuskawin")
    private List<SemuaStatusKawinItem> semuastatuskawin;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public void setSemuastatuskawin(List<SemuaStatusKawinItem> semuastatuskawin){
        this.semuastatuskawin = semuastatuskawin;
    }

    public List<SemuaStatusKawinItem> getSemuastatuskawin(){
        return semuastatuskawin;
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
                "ResponseStatusKawin{" +
                        "semuastatuskawin = '" + semuastatuskawin + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }

}
