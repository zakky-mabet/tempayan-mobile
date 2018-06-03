package id.tempayan.activity.surat.frag_skkb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.tempayan.R;
import id.tempayan.util.SharedPrefManager;

import static id.tempayan.apihelper.UtilsApi.BASE_URL_IMAGE;

@SuppressLint("ValidFragment")
public class PemohonFragment extends Fragment {

    SharedPrefManager sharedPrefManager;

    public String nik;
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

    public PemohonFragment(String nikSet) {
        nik = nikSet;

    }

    public static PemohonFragment newInstance(String nik) {
        Bundle args = new Bundle();
        PemohonFragment fragment = new PemohonFragment(nik);
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
        View rootView =
                inflater.inflate(R.layout.fragment_pemohon, container, false);

        ButterKnife.bind(this,rootView);

        sharedPrefManager = new SharedPrefManager(getActivity());

        tvno_kk.setText(sharedPrefManager.getSpNokk());
        tvnik.setText(nik);
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

        Glide.with(getActivity())
                .load(BASE_URL_IMAGE+sharedPrefManager.getSPPhoto())
                .into((ImageView) rootView.findViewById(R.id.ivAvatar));

        return rootView;
    }


}
