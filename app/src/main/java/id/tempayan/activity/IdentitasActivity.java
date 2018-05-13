package id.tempayan.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class IdentitasActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    SharedPrefManager sharedPrefManager;
    BaseApiService mApiService;
    Context mContext;

    @BindView(R.id.tvnik)
    TextView tvnik;
    @BindView(R.id.tvno_kk)
    TextView tvno_kk;
    @BindView(R.id.tvnama_lengkap)
    TextView tvnama_lengkap;
    @BindView(R.id.tvttl)
    TextView tvttl;
    @BindView(R.id.tv_jns_kelamin)
    TextView tv_jns_kelamin;
    @BindView(R.id.tvalamat)
    TextView tvalamat;
    @BindView(R.id.tvrtrw)
    TextView tvrtrw;
    @BindView(R.id.tvdesa)
    TextView tvdesa;
    @BindView(R.id.tvkecamatan)
    TextView tvkecamatan;
    @BindView(R.id.tvkdpos)
    TextView tvkdpos;
    @BindView(R.id.tvagama)
    TextView tvagama;
    @BindView(R.id.tvpekerjaan)
    TextView tvpekerjaan;
    @BindView(R.id.tvkewarganegaraan)
    TextView tvkewarganegaraan;
    @BindView(R.id.tvstatus_kawin)
    TextView tvstatus_kawin;
    @BindView(R.id.tvgoldarah)
    TextView tvgoldarah;
    @BindView(R.id.tvstatuskk)
    TextView tvstatuskk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identitas);

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        sharedPrefManager = new SharedPrefManager(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditIdentitasActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        //Log.i("TAG", sharedPrefManager.getSPEmail());
        mApiService = UtilsApi.getAPIService(); // meng-init utilapis pada paket apihelper
        tvnik.setText(sharedPrefManager.getSpNik());
        tvno_kk.setText(sharedPrefManager.getSpNokk());
        tvnama_lengkap.setText(sharedPrefManager.getSPNama());
        tvttl.setText(sharedPrefManager.getSpTtl());
        tv_jns_kelamin.setText(sharedPrefManager.getSpJnskelamin());
        tvalamat.setText(sharedPrefManager.getSpAlamat());
        tvrtrw.setText(sharedPrefManager.getSpRtrw());
        tvdesa.setText(sharedPrefManager.getSpDesa());
        tvkecamatan.setText(sharedPrefManager.getSpKecamatan());
        tvkdpos.setText(sharedPrefManager.getSpKdpos());
        tvagama.setText(sharedPrefManager.getSpAgama());
        tvpekerjaan.setText(sharedPrefManager.getSpPekerjaan());
        tvkewarganegaraan.setText(sharedPrefManager.getSpKewarganegaraan());
        tvstatus_kawin.setText(sharedPrefManager.getSpStatuskawin());
        tvgoldarah.setText(sharedPrefManager.getSpGoldarah());
        tvstatuskk.setText(sharedPrefManager.getSpStatuskk());

        Glide.with(getApplicationContext())
                .load(BASE_URL_IMAGE+sharedPrefManager.getSPPhoto())
                .into((ImageView) findViewById(R.id.ivphoto));

    }
}
