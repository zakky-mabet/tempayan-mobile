package id.tempayan.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.tempayan.R;
import id.tempayan.apihelper.BaseApiService;
import id.tempayan.apihelper.UtilsApi;
import id.tempayan.util.SharedPrefManager;

import static id.tempayan.apihelper.UtilsApi.BASE_URL_IMAGE;

public class AkunActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    SharedPrefManager sharedPrefManager;
    BaseApiService mApiService;
    Context mContext;

    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvHandphone)
    TextView tvHandphone;
    @BindView(R.id.tvTanggal_daftar)
    TextView tvTanggal_daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun);

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        sharedPrefManager = new SharedPrefManager(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Edit Akun", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Log.i("TAG", sharedPrefManager.getSPEmail());
        mApiService = UtilsApi.getAPIService(); // meng-init utilapis pada paket apihelper
        tvEmail.setText(sharedPrefManager.getSPEmail());
        tvHandphone.setText(sharedPrefManager.getSpHandphone());
        tvTanggal_daftar.setText(sharedPrefManager.getSpTanggaldaftar());

        Glide.with(getApplicationContext())
                .load(BASE_URL_IMAGE+sharedPrefManager.getSPPhoto())
                .into((ImageView) findViewById(R.id.ivphoto));

        //m_getbyiduser();

    }

//    private void m_getbyiduser(){
//        mApiService.getbyiduser(sharedPrefManager.getSPEmail())
//                .enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        if (response.isSuccessful()){
//                            try {
//                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                                if (jsonRESULTS.getString("error").equals("false")){
//
//
//                                    String email = jsonRESULTS.getJSONObject("users").getString("email");
//                                    String photo = jsonRESULTS.getJSONObject("users").getString("photo");
//                                    String handphone = jsonRESULTS.getJSONObject("users").getString("handphone");
//                                    String tanggal_daftar = jsonRESULTS.getJSONObject("users").getString("tanggal_daftar");
//
//                                    tvEmail.setText(email);
//                                    tvHandphone.setText(handphone);
//                                    tvTanggal_daftar.setText(tanggal_daftar);
//
//                                    Glide.with(getApplicationContext())
//                                            .load(BASE_URL_IMAGE+photo)
//                                            .into((ImageView) findViewById(R.id.ivphoto));
//
//
//                                } else {
//
//                                    String error_message = jsonRESULTS.getString("error_msg");
//                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }  catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Log.e("debug", "onFailure: ERROR > " + t.toString());
//                    }
//                });
//    }
}
