package id.tempayan.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import id.tempayan.R;
import id.tempayan.adapter.AdapterRiwayat;
import id.tempayan.network.ApiServices;
import id.tempayan.network.InitRetrofit;
import id.tempayan.response.ResponRiwayat;
import id.tempayan.response.RiwayatItem;
import id.tempayan.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatActivity extends AppCompatActivity {

    // Deklarasi Widget
    private RecyclerView recyclerView;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);
        menu.setElevation(0);


        sharedPrefManager = new SharedPrefManager(this);
        mContext = this;
        // Inisialisasi Widget
        recyclerView = (RecyclerView) findViewById(R.id.rvListRiwayat);
        // RecyclerView harus pakai Layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Eksekusi method
        tampilRiwayat();
        initSetTitle();
    }

    private void tampilRiwayat() {
        ApiServices api = InitRetrofit.getInstance();
        // Siapkan request
        Call<ResponRiwayat> riwayatCall = api.request_show_all_riwayat(sharedPrefManager.getSPIdUserSting());
        // Kirim request
        riwayatCall.enqueue(new Callback<ResponRiwayat>() {

            @Override
            public void onResponse(Call<ResponRiwayat> call, Response<ResponRiwayat> response) {
                loading = ProgressDialog.show(mContext, null, "Mengambil data, Harap tunggu", true, false);
                // Pasikan response Sukses
                if (response.isSuccessful()){
                    loading.dismiss();
                    Log.d("response api", response.body().toString());
                    // tampung data response body ke variable
                    List<RiwayatItem> data_riwayat = response.body().getRiwayat();
                    boolean status = response.body().isStatus();
                    // Kalau response status nya = true
                    if (status){
                        // Buat Adapter untuk recycler view
                        AdapterRiwayat adapter = new AdapterRiwayat(RiwayatActivity.this, data_riwayat);
                        recyclerView.setAdapter(adapter);
                    } else {
                        loading.dismiss();
                        // kalau false
                        Toast.makeText(RiwayatActivity.this, "Belum ada riwayat pelayanan", Toast.LENGTH_SHORT).show();
                        LinearLayout mainLayout =(LinearLayout) findViewById(R.id.kosong);
                        mainLayout.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponRiwayat> call, Throwable t) {
                loading.dismiss();
                // print ke log jika Error
                t.printStackTrace();
            }
        });
    }

    private void initSetTitle() {

        ActionBar menu = getSupportActionBar();
        assert menu != null;
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);
        menu.setElevation(0);
        menu.setTitle("Riwayat Pelayanan");
    }
}
