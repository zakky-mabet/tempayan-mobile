package id.tempayan.apihelper;

import id.tempayan.model.ResponseAgama;
import id.tempayan.model.ResponseGolonganDarah;
import id.tempayan.model.ResponseStatusKawin;
import id.tempayan.model.ResponseStatusKeluarga;
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

    @GET("master_data/golongan_darah/")
    Call<ResponseGolonganDarah> getSemuaGolonganDarah();

    @GET("master_data/agama/")
    Call<ResponseAgama> getSemuaAgama();

    @GET("master_data/statuskawin/")
    Call<ResponseStatusKawin> getSemuaStatusKawin();

    @GET("master_data/statuskeluarga/")
    Call<ResponseStatusKeluarga> getSemuaStatusKeluarga();

    @FormUrlEncoded
    @POST("auth/ubahidentitas/")
    Call<ResponseBody> ubahidentitasrequest(@Field("id") String id,
                                            @Field("nik") String nik,
                                            @Field("nokk") String nokk,
                                            @Field("namalengkap") String namalengkap,
                                            @Field("tempatlahir") String tempatlahir,
                                            @Field("tanggallahir") String tanggallahir,
                                            @Field("jeniskelamin") String jeniskelamin,
                                            @Field("alamat") String alamat,
                                            @Field("rt") String rt,
                                            @Field("rw") String rw,
                                            @Field("desa") String desa,
                                            @Field("kecamatan") String kecamatan,
                                            @Field("kodepos") String kodepos,
                                            @Field("agama") String agama,
                                            @Field("pekerjaan") String pekerjaan,
                                            @Field("kewarganegaraan") String kewarganegaraan,
                                            @Field("statuskawin") String statuskawin,
                                            @Field("golongandarah") String golongandarah,
                                            @Field("statuskeluarga") String statuskeluarga
                                           );
}
