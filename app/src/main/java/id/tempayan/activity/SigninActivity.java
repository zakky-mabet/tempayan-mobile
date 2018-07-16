package id.tempayan.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import id.tempayan.R;
import id.tempayan.apihelper.BaseApiService;
import id.tempayan.apihelper.UtilsApi;
import id.tempayan.util.SharedPrefManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {


    TextView tvSignup;
    Button bSignin;
    EditText etEmail, etPassword;
    ProgressDialog loading;
    BaseApiService mApiService;
    Context mContext;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        ButterKnife.bind(this);
        sharedPrefManager = new SharedPrefManager(this);

        if (sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(SigninActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init utilapis pada paket apihelper
        initComponents(); // lempar ke method initComponents() "? Supaya lebih rapi ?"

    }

    private void initComponents() {
        etEmail =(EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bSignin = (Button) findViewById(R.id.bSignin);
        tvSignup = (TextView) findViewById(R.id.tvSignup);

        bSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (etEmail.getText().toString().equals("")) {
                    etEmail.setError("Email diperlukan");
                    //Toast.makeText(getApplication(),"Email diperlukan", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                } else if (!isValidEmail(etEmail.getText().toString())) {
                    etEmail.setError("Format email salah");
                    //Toast.makeText(getApplication(),"Format Email salah", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                } else if (etPassword.getText().toString().equals("")) {
                    etPassword.setError("Password diperlukan");
                    //Toast.makeText(getApplication(),"Password diperlukan", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                } else {
                    loading = ProgressDialog.show(mContext, null, "Harap Tunggu", true, false);
                    requestLogin(); // lempar ke method requestLogin()"? Supaya lebih rapi lagi ?"
                }
            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninActivity.this, SignupActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
    }

    private void requestLogin(){
        mApiService.loginRequest(etEmail.getText().toString(), etPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){

                                    String nama = jsonRESULTS.getJSONObject("penduduk").getString("nama_lengkap");
                                    String email = jsonRESULTS.getJSONObject("users").getString("email");
                                    String photo = jsonRESULTS.getJSONObject("users").getString("photo");
                                    String handphone = jsonRESULTS.getJSONObject("users").getString("handphone");
                                    String tanggal_daftar = jsonRESULTS.getJSONObject("users").getString("tanggal_daftar");
                                    String nik = jsonRESULTS.getJSONObject("penduduk").getString("nik");
                                    String no_kk = jsonRESULTS.getJSONObject("penduduk").getString("no_kk");
                                    String kd_pos = jsonRESULTS.getJSONObject("penduduk").getString("kd_pos");
                                    String ttl = jsonRESULTS.getJSONObject("penduduk").getString("ttl");
                                    String tmp_lahir = jsonRESULTS.getJSONObject("penduduk").getString("tmp_lahir");
                                    String tgl_lahir = jsonRESULTS.getJSONObject("penduduk").getString("tgl_lahir");
                                    String jns_kelamin = jsonRESULTS.getJSONObject("penduduk").getString("jns_kelamin");
                                    String alamat = jsonRESULTS.getJSONObject("penduduk").getString("alamat");
                                    String rt = jsonRESULTS.getJSONObject("penduduk").getString("rt");
                                    String rw = jsonRESULTS.getJSONObject("penduduk").getString("rw");
                                    String rt_rw = jsonRESULTS.getJSONObject("penduduk").getString("rt_rw");
                                    String desa = jsonRESULTS.getJSONObject("penduduk").getString("desa");
                                    String kecamatan = jsonRESULTS.getJSONObject("penduduk").getString("kecamatan");
                                    String agama = jsonRESULTS.getJSONObject("penduduk").getString("agama");
                                    String pekerjaan = jsonRESULTS.getJSONObject("penduduk").getString("pekerjaan");
                                    String kewarganegaraan = jsonRESULTS.getJSONObject("penduduk").getString("kewarganegaraan");
                                    String status_kawin = jsonRESULTS.getJSONObject("penduduk").getString("status_kawin");
                                    String gol_darah = jsonRESULTS.getJSONObject("penduduk").getString("gol_darah");
                                    String status_kk = jsonRESULTS.getJSONObject("penduduk").getString("status_kk");
                                    int id_user = jsonRESULTS.getJSONObject("users").getInt("id_user");
                                    String id_userstring = jsonRESULTS.getJSONObject("users").getString("id_user");

                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, nama);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, email);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_PHOTO, photo);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_HANDPHONE, handphone);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_TANGGALDAFTAR, tanggal_daftar);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NIK, nik);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NOKK, no_kk);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_KDPOS, kd_pos);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_TTL, ttl);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_TMPLAHIR, tmp_lahir);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_TGLLAHIR, tgl_lahir);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_JNSKELAMIN, jns_kelamin);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_ALAMAT, alamat);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_RT, rt);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_RW, rw);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_RTRW, rt_rw);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_DESA, desa);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_KECAMATAN, kecamatan);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_AGAMA, agama);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_PEKERJAAN, pekerjaan);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_KEWARGANEGARAAN, kewarganegaraan);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_STATUSKAWIN, status_kawin);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_GOLDARAH, gol_darah);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_STATUSKK, status_kk);
                                    sharedPrefManager.saveSPInt(SharedPrefManager.SP_ID_USER, id_user);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_ID_USER, id_userstring);

                                    // Jika login berhasil maka data nama yang ada di response API
                                    // akan diparsing ke activity selanjutnya.
                                    Toast.makeText(mContext, "Selamat Datang "+nama, Toast.LENGTH_SHORT).show();
                                    // Shared Pref ini berfungsi untuk menjadi trigger session login
                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                                    startActivity(new Intent(mContext, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();

                                } else {
                                    // Jika login gagal
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(getApplication(), error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }  catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                       // Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }

    public static boolean isValidEmail(String email) {
        boolean validate;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern)) {
            validate = true;
        } else if (email.matches(emailPattern2)) {
            validate = true;
        } else {
            validate = false;
        }

        return validate;
    }


}
