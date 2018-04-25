package id.tempayan.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.tempayan.R;
import id.tempayan.apihelper.BaseApiService;
import id.tempayan.apihelper.UtilsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    public static final int MIN_PASSWORD_LENGTH = 8;

    @BindView(R.id.etNik)
    TextView etNik;
    @BindView(R.id.etNamaLengkap)
    TextView etNamaLengkap;
    @BindView(R.id.etEmail)
    TextView etEmail;
    @BindView(R.id.etSandi)
    TextView etSandi;
    @BindView(R.id.etKonfirmasiSandi)
    TextView etKonfirmasiSandi;
    @BindView(R.id.etNomorHp)
    EditText etNomorHp;
    @BindView(R.id.tvSignin)
    TextView tvSignin;
    @BindView(R.id.bSignup)
    Button bSignup;

    ProgressDialog loading;
    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, SigninActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        }); // end tvSignin setOnClickListener

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        initComponents();

    }

    private void initComponents() {
        bSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNik.getText().toString().equals("")) {
                    etNik.setError("NIK diperlukan");
                    etNik.requestFocus();
                    return;
                } else if (etNamaLengkap.getText().toString().equals("")) {
                    etNamaLengkap.setError("Nama Lengkap diperlukan");
                    etNamaLengkap.requestFocus();
                    return;
                } else if (etEmail.getText().toString().equals("")) {
                    etEmail.setError("Email diperlukan");
                    etEmail.requestFocus();
                    return;
                } else if (!isValidEmail(etEmail.getText().toString())) {
                    etEmail.setError("Format email salah");
                    etEmail.requestFocus();
                    return;
                } else if (etSandi.getText().toString().equals("")) {
                    etSandi.setError("Sandi diperlukan");
                    etSandi.requestFocus();
                    return;
                } else if (etSandi.length() < MIN_PASSWORD_LENGTH) {
                    etSandi.setError("Sandi Minimal 8 Karakter");
                    etSandi.requestFocus();
                    return;
                } else if (etKonfirmasiSandi.getText().toString().equals("")) {
                    etKonfirmasiSandi.setError("Konfirmasi Sandi diperlukan");
                    etKonfirmasiSandi.requestFocus();
                    return;
                } else if (!etKonfirmasiSandi.getText().toString().matches(etSandi.getText().toString()) ) {
                    etKonfirmasiSandi.setError("Konfirmasi Sandi tidak Valid");
                    etKonfirmasiSandi.requestFocus();
                    return;
                }  else if (etNomorHp.getText().toString().equals("")) {
                    etNomorHp.setError("Nomor Handphone diperlukan");
                    etNomorHp.requestFocus();
                    return;
                } else {
                    loading = ProgressDialog.show(mContext, null, etNamaLengkap.getText()+" Harap Tunggu...", true, false);
                    requestRegister();
                }
            }
        });
    }

    private void requestRegister() {
        mApiService.registerRequest(etNik.getText().toString(),
                etNamaLengkap.getText().toString(),
                etEmail.getText().toString(),
                etSandi.getText().toString(),
                etNomorHp.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){
                                    Toast.makeText(mContext, "BERHASIL REGISTRASI", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(mContext, SigninActivity.class));
                                } else {
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("debug", "onResponse: GA BERHASIL");
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
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
