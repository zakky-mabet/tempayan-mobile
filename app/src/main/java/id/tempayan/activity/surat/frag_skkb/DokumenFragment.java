package id.tempayan.activity.surat.frag_skkb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.tempayan.R;
import id.tempayan.apihelper.BaseApiService;
import id.tempayan.apihelper.UtilsApi;
import id.tempayan.util.SharedPrefManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class DokumenFragment extends Fragment {

    String id_surat;

    SharedPrefManager sharedPrefManager;
    BaseApiService mApiService;
    Context mContext;

    @BindView(R.id.tvidsurat)
    TextView tvidsurat;
    @BindView(R.id.nama_surat)
    TextView nama_surat;
    @BindView(R.id.no_surat)
    TextView no_surat;
    @BindView(R.id.tanggal_surat)
    TextView tanggal_surat;
    @BindView(R.id.keperluan)
    TextView keperluan;
    @BindView(R.id.ktp_pemohon)
    TextView ktp_pemohon;
    @BindView(R.id.kk_pemohon)
    TextView kk_pemohon;
    @BindView(R.id.surat_pengantar)
    TextView surat_pengantar;


    public DokumenFragment(String id_dokumen) {
        id_surat = id_dokumen;
    }

    public static DokumenFragment newInstance(String id_dokumen) {

        Bundle args = new Bundle();
        DokumenFragment fragment = new DokumenFragment(id_dokumen);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         final View rootView = inflater.inflate(R.layout.fragment_dokumen, container, false);


        ButterKnife.bind(this,rootView);

        sharedPrefManager = new SharedPrefManager(getActivity());

        mApiService = UtilsApi.getAPIService();

        mApiService.get_byid_surat(id_surat)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("status").equals("true")){



                                    String id_pelayanan = jsonRESULTS.getString("ID_pelayanan");
                                    String judul_surat = jsonRESULTS.getString("judul_surat");

                                    String isi_surat_no_surat = jsonRESULTS.getJSONObject("isi_surat").getString("no_surat");
                                    String isi_surat_tanggal_surat = jsonRESULTS.getJSONObject("isi_surat").getString("tanggal_surat");
                                    String isi_surat_keperluan= jsonRESULTS.getJSONObject("isi_surat").getString("keperluan");

                                    String persyaratan_surat_ktp_pemohon = jsonRESULTS.getJSONObject("persyaratan_surat").getString("ktp_pemohon");
                                    String persyaratan_surat_kk_pemohon = jsonRESULTS.getJSONObject("persyaratan_surat").getString("kk_pemohon");
                                    String persyaratan_surat_surat_pengantar = jsonRESULTS.getJSONObject("persyaratan_surat").getString("surat_pengantar");


                                    tvidsurat.setText(id_pelayanan);
                                    nama_surat.setText(judul_surat);
                                    no_surat.setText(isi_surat_no_surat);
                                    tanggal_surat.setText(isi_surat_tanggal_surat);
                                    keperluan.setText(isi_surat_keperluan);


                                    Glide.with(getActivity())
                                            .load(persyaratan_surat_ktp_pemohon)
                                            .into((ImageView) rootView.findViewById(R.id.iv_ktp_pemohon));

                                    Glide.with(getActivity())
                                            .load(persyaratan_surat_kk_pemohon)
                                            .into((ImageView) rootView.findViewById(R.id.iv_kk_pemohon));

                                    Glide.with(getActivity())
                                            .load(persyaratan_surat_surat_pengantar)
                                            .into((ImageView) rootView.findViewById(R.id.iv_surat_pengantar));



                                } else {

                                    String status = jsonRESULTS.getString("status");
                                    Toast.makeText(mContext, status, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }  catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });



        return rootView;
    }



}
