package id.tempayan.network;

import id.tempayan.response.ResponRiwayat;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServices {


    @GET("surat/riwayat/{id}")
    Call<ResponRiwayat> request_show_all_riwayat( @Path("id")String id);
}
