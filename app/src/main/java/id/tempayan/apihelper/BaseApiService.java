package id.tempayan.apihelper;

import id.tempayan.model.ResponseAgama;
import id.tempayan.model.ResponseGolonganDarah;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BaseApiService {

    // Func Get Api
    @FormUrlEncoded
    @POST("auth/login/")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/register/")
    Call<ResponseBody> registerRequest(@Field("nik") String nik,
                                       @Field("nama_lengkap") String nama_lengkap,
                                       @Field("email") String email,
                                       @Field("password") String password,
                                       @Field("handphone") String handphone );

    @FormUrlEncoded
    @POST("auth/ubahpassword/")
    Call<ResponseBody> ubahpasswordrequest(@Field("password_sekarang") String password_sekarang,
                                           @Field("password_baru") String password_baru,
                                           @Field("id") String id);

    @GET("master_data/golongan_darah")
    Call<ResponseGolonganDarah> getSemuaGolonganDarah();

    @GET("master_data/agama")
    Call<ResponseAgama> getSemuaAgama();
}
