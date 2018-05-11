package id.tempayan.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import id.tempayan.R;
import id.tempayan.apihelper.BaseApiService;
import id.tempayan.apihelper.UtilsApi;
import id.tempayan.util.SharedPrefManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.tempayan.activity.SignupActivity.MIN_PASSWORD_LENGTH;

public class UbahActivity extends AppCompatActivity {

    Button bubahpassword;
    TextInputEditText etpasswordsekarang,etpasswordbaru,etkonfirmasipassword;

    Context mContext;
    ProgressDialog loading;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init utilapis pada paket apihelper
        sharedPrefManager = new SharedPrefManager(this);

        check_dulu_coy();
    }

    private void check_dulu_coy() {
        etpasswordsekarang = (TextInputEditText) findViewById(R.id.etpasswordsekarang);
        etpasswordbaru = (TextInputEditText) findViewById(R.id.etpasswordbaru);
        etkonfirmasipassword = (TextInputEditText) findViewById(R.id.etkonfirmasipassword);
        bubahpassword = (Button) findViewById(R.id.bubahpassword);
        bubahpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etpasswordsekarang.getText().toString().equals("")) {
                    etpasswordsekarang.setError("Password Sekarang diperlukan");
                    etpasswordsekarang.requestFocus();
                    return;
                } else if (etpasswordbaru.getText().toString().equals("")) {
                    etpasswordbaru.setError("Password Baru diperlukan");
                    etpasswordbaru.requestFocus();
                    return;
                } else if (etpasswordbaru.length() < MIN_PASSWORD_LENGTH) {
                    etpasswordbaru.setError("Sandi Minimal 8 Karakter");
                    etpasswordbaru.requestFocus();
                    return;
                }else if (etkonfirmasipassword.getText().toString().equals("")) {
                    etkonfirmasipassword.setError("Konfirmasi Password Baru diperlukan");
                    etkonfirmasipassword.requestFocus();
                    return;
                } else if (!etkonfirmasipassword.getText().toString().matches(etpasswordbaru.getText().toString()) ) {
                    etkonfirmasipassword.setError("Konfirmasi Password Baru tidak Valid");
                    etkonfirmasipassword.requestFocus();
                    return;
                } else {
                    loading = ProgressDialog.show(mContext, null, "Harap Tunggu", true, false);
                    requestubahpassword();
                }
            }
        });
    }

    private void requestubahpassword(){
            mApiService.ubahpasswordrequest(etpasswordsekarang.getText().toString(), etpasswordbaru.getText().toString(),sharedPrefManager.getSPIdUserSting() )
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()){
                                loading.dismiss();
                                try {
                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                    if (jsonRESULTS.getString("error").equals("false")){

                                        String msg = jsonRESULTS.getString("msg");
                                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(mContext, UbahActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                        finish();

                                    } else {
                                        // Jika login gagal
                                        String error_message = jsonRESULTS.getString("error_msg");
                                        Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
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

}
