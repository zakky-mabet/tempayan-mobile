package id.tempayan.activity.surat.frag_skkb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

@SuppressLint("ValidFragment")
public class KeteranganFragment extends Fragment {

    String id_surat;

    BaseApiService mApiService;
    Context mContext;

    @BindView(R.id.status_surat)
    TextView status_surat;
    @BindView(R.id.waktu_mulai)
    TextView waktu_mulai;
    @BindView(R.id.waktu_selesai)
    TextView waktu_selesai;


    public KeteranganFragment(String id_dokumen) {
        id_surat = id_dokumen;
    }

    public static KeteranganFragment newInstance(String id_dokumen) {

        Bundle args = new Bundle();
        KeteranganFragment fragment = new KeteranganFragment(id_dokumen);
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
        final View rootView = inflater.inflate(R.layout.fragment_keterangan, container, false);

        ButterKnife.bind(this,rootView);

        mApiService = UtilsApi.getAPIService();

        mApiService.get_byid_surat(id_surat)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("status").equals("true")){

                                    String getstatus_surat = jsonRESULTS.getString("status_surat");
                                    String getwaktu_mulai = jsonRESULTS.getString("waktu_mulai");
                                    String getwaktu_selesai = jsonRESULTS.getString("waktu_selesai");
                                    String getreal_status = jsonRESULTS.getString("real_status");

                                    status_surat.setText(getstatus_surat);
                                    waktu_mulai.setText(getwaktu_mulai);
                                    waktu_selesai.setText(getwaktu_selesai);

                                    if (getreal_status.equals("no")){
                                        status_surat.setTextColor(ContextCompat.getColor(getContext(), R.color.pink));
                                    } else if (getreal_status.equals("yes")) {
                                        status_surat.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                                    } else if (getreal_status.equals("read")) {
                                        status_surat.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                                    }

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
