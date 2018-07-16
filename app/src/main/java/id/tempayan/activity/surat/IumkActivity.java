package id.tempayan.activity.surat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import id.tempayan.R;
import id.tempayan.apihelper.BaseApiService;
import id.tempayan.apihelper.UtilsApi;
import id.tempayan.util.SharedPrefManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.tempayan.apihelper.UtilsApi.BASE_URL_IMAGE;

public class IumkActivity extends AppCompatActivity {

    private static final int PICK_KTP = 1;
    private static final int PICK_BUKTILUNASPBB = 2;
    private  static final int PICK_NPWP = 3;
    private  static final int PICK_SURATPERSETUJUANTETANGGA = 4;
    private  static final int PICK_SURATPERMOHONAN = 5;
    private  static final int PICK_SURATREKOMENDASILURAH = 6;

    private String file_ktp;
    private String file_buktilunaspbb;
    private String file_npwp;
    private String file_suratpersetujuantetangga;
    private String file_suratpermohonan;
    private String file_suratrekomendasilurah;

    @BindView(R.id.ktppemohon)
    LinearLayout ktppemohon;
    @BindView(R.id.ktppemohonavatar)
    ImageView ktppemohonavatar;
    @BindView(R.id.ktppemohontitle)
    TextView ktppemohontitle;
    @BindView(R.id.ktppemohonsubtitle)
    TextView ktppemohonsubtitle;

    @BindView(R.id.buktilunaspbb)
    LinearLayout buktilunaspbb;
    @BindView(R.id.buktilunaspbbavatar)
    ImageView buktilunaspbbavatar;
    @BindView(R.id.buktilunaspbbtitle)
    TextView buktilunaspbbtitle;
    @BindView(R.id.buktilunaspbbsubtitle)
    TextView buktilunaspbbsubtitle;

    @BindView(R.id.npwp)
    LinearLayout npwp;
    @BindView(R.id.npwpavatar)
    ImageView npwpavatar;
    @BindView(R.id.npwptitle)
    TextView npwptitle;
    @BindView(R.id.npwpsubtitle)
    TextView npwpsubtitle;

    @BindView(R.id.suratpersetujuantetangga)
    LinearLayout suratpersetujuantetangga;
    @BindView(R.id.suratpersetujuantetanggaavatar)
    ImageView suratpersetujuantetanggaavatar;
    @BindView(R.id.suratpersetujuantetanggatitle)
    TextView suratpersetujuantetanggatitle;
    @BindView(R.id.suratpersetujuantetanggasubtitle)
    TextView suratpersetujuantetanggasubtitle;


    @BindView(R.id.suratpermohonan)
    LinearLayout suratpermohonan;
    @BindView(R.id.suratpermohonanavatar)
    ImageView suratpermohonanavatar;
    @BindView(R.id.suratpermohonantitle)
    TextView suratpermohonantitle;
    @BindView(R.id.suratpermohonansubtitle)
    TextView suratpermohonansubtitle;


    @BindView(R.id.suratrekomendasilurah)
    LinearLayout suratrekomendasilurah;
    @BindView(R.id.suratrekomendasilurahavatar)
    ImageView suratrekomendasilurahavatar;
    @BindView(R.id.suratrekomendasilurahtitle)
    TextView suratrekomendasilurahtitle;
    @BindView(R.id.suratrekomendasilurahsubtitle)
    TextView suratrekomendasilurahsubtitle;

    SharedPrefManager sharedPrefManager;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context mContext;

    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;

    @BindView(R.id.tvnamalengkap)
    TextView tvnamalengkap;
    @BindView(R.id.tvnik)
    TextView tvnik;
    @BindView(R.id.ajukanpermohonan)
    Button ajukanpermohonan;

    Unbinder unbinder;

    @BindView(R.id.etnamaperusahaan)
    TextInputEditText etnamaperusahaan;

    @BindView(R.id.etbentukusaha)
    TextInputEditText etbentukusaha;

    @BindView(R.id.etnpwp)
    TextInputEditText etnpwp;

    @BindView(R.id.etkegiatan)
    TextInputEditText etkegiatan;

    @BindView(R.id.etsarana)
    TextInputEditText etsarana;

    @BindView(R.id.etalamat)
    TextInputEditText etalamat;


    @BindView(R.id.etjumlahmodal)
    TextInputEditText etjumlahmodal;


    @BindView(R.id.etnopendaftaran)
    TextInputEditText etnopendaftaran;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iumk);

        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);
        mApiService = UtilsApi.getAPIService();

        initSetPemohon();
        initSetTitle();
        initPick();
        initAjukanPermohonancheck();

    }

    private void initSetPemohon() {
        Glide.with(getApplicationContext())
                .load(BASE_URL_IMAGE+sharedPrefManager.getSPPhoto())
                .into((CircleImageView) findViewById(R.id.ivAvatar));
        tvnik.setText(sharedPrefManager.getSpNik());
        tvnamalengkap.setText(sharedPrefManager.getSPNama());
    }

    private void initSetTitle() {
        ActionBar menu = getSupportActionBar();
        assert menu != null;
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);
        menu.setElevation(0);
        menu.setTitle(Html.fromHtml("<small>Surat Izin Usaha Mikro dan Kecil</small>"));
    }

    private void initPick() {

        ktppemohon.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                openGallery(PICK_KTP);
            }});

        buktilunaspbb.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                openGallery(PICK_BUKTILUNASPBB);
            }});

        npwp.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                openGallery(PICK_NPWP);
            }});

        suratpersetujuantetangga.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                openGallery(PICK_SURATPERSETUJUANTETANGGA);
            }});

        suratpermohonan.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                openGallery(PICK_SURATPERMOHONAN);
            }});

        suratrekomendasilurah.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                openGallery(PICK_SURATREKOMENDASILURAH);
            }});
    }


    private void openGallery(int req_code){

        EasyImage.openChooserWithGallery(IumkActivity.this,"Pilih Foto :" ,
                req_code);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                if (type == PICK_KTP) {

                    ktppemohonsubtitle.setText("Berhasil");
                    ktppemohonsubtitle.setTextColor(getResources().getColor(R.color.green));
                    Glide.with(getApplicationContext())
                            .load(R.drawable.icons8_approval)
                            .into((CircleImageView) findViewById(R.id.ktppemohonavatar));
                    String file_ktp = imageFile.getAbsolutePath();
                    setFile_ktp(file_ktp);

                }
                if (type == PICK_BUKTILUNASPBB) {

                    buktilunaspbbsubtitle.setText("Berhasil");
                    buktilunaspbbsubtitle.setTextColor(getResources().getColor(R.color.green));
                    Glide.with(getApplicationContext())
                            .load(R.drawable.icons8_approval)
                            .into((CircleImageView) findViewById(R.id.buktilunaspbbavatar));
                    String file_bukti = imageFile.getAbsolutePath();
                    setFile_buktilunaspbb(file_bukti);

                }
                if (type == PICK_NPWP) {

                    npwpsubtitle.setText("Berhasil");
                    npwpsubtitle.setTextColor(getResources().getColor(R.color.green));
                    Glide.with(getApplicationContext())
                            .load(R.drawable.icons8_approval)
                            .into((CircleImageView) findViewById(R.id.npwpavatar));
                    String filenpwp = imageFile.getAbsolutePath();
                    setFile_npwp(filenpwp);
                }

                if (type == PICK_SURATPERSETUJUANTETANGGA) {

                    suratpersetujuantetanggasubtitle.setText("Berhasil");
                    suratpersetujuantetanggasubtitle.setTextColor(getResources().getColor(R.color.green));
                    Glide.with(getApplicationContext())
                            .load(R.drawable.icons8_approval)
                            .into((CircleImageView) findViewById(R.id.suratpersetujuantetanggaavatar));
                    String filepersetujuan = imageFile.getAbsolutePath();
                    setFile_suratpersetujuantetangga(filepersetujuan);
                }

                if (type == PICK_SURATPERMOHONAN) {

                    suratpermohonansubtitle.setText("Berhasil");
                    suratpermohonansubtitle.setTextColor(getResources().getColor(R.color.green));
                    Glide.with(getApplicationContext())
                            .load(R.drawable.icons8_approval)
                            .into((CircleImageView) findViewById(R.id.suratpermohonanavatar));
                    String filepermohonan = imageFile.getAbsolutePath();
                    setFile_suratpermohonan(filepermohonan);
                }

                if (type == PICK_SURATREKOMENDASILURAH) {

                    suratrekomendasilurahsubtitle.setText("Berhasil");
                    suratrekomendasilurahsubtitle.setTextColor(getResources().getColor(R.color.green));
                    Glide.with(getApplicationContext())
                            .load(R.drawable.icons8_approval)
                            .into((CircleImageView) findViewById(R.id.suratrekomendasilurahavatar));
                    String filerekomendasi = imageFile.getAbsolutePath();
                    setFile_suratrekomendasilurah(filerekomendasi);
                }
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                Toast.makeText(IumkActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                super.onCanceled(source, type);
            }
        });
    }

    private void initAjukanPermohonancheck() {

        ajukanpermohonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etnamaperusahaan.getText().toString().equals("")) {
                    etnamaperusahaan.setError("Nama Perusahaan diperlukan");
                    etnamaperusahaan.requestFocus();
                    return;
                } else if (etbentukusaha.getText().toString().equals("")) {
                    etbentukusaha.setError("Bentuk Usaha diperlukan");
                    etbentukusaha.requestFocus();
                    return;
                } else if (etnpwp.getText().toString().equals("")) {
                    etnpwp.setError("No NPWP diperlukan");
                    etnpwp.requestFocus();
                    return;
                } else if (etkegiatan.getText().toString().equals("")) {
                    etkegiatan.setError("Kegiatan diperlukan");
                    etkegiatan.requestFocus();
                    return;
                } else if (etsarana.getText().toString().equals("")) {
                    etsarana.setError("Sarana diperlukan");
                    etsarana.requestFocus();
                    return;
                } else if (etalamat.getText().toString().equals("")) {
                    etalamat.setError("Alamat diperlukan");
                    etalamat.requestFocus();
                    return;
                } else if (etjumlahmodal.getText().toString().equals("")) {
                    etjumlahmodal.setError("Jumlah Modal diperlukan");
                    etjumlahmodal.requestFocus();
                    return;
                } else if (etnopendaftaran.getText().toString().equals("")) {
                    etnopendaftaran.setError("Nomor Pendaftaran diperlukan");
                    etnopendaftaran.requestFocus();
                    return;
                } else if (getFile_ktp() == null) {
                    ktppemohonsubtitle.setText("KTP Pemohon diperlukan");
                    ktppemohonsubtitle.setTextColor(getResources().getColor(R.color.pink));

                    return;
                } else if (getFile_buktilunaspbb() == null) {
                    buktilunaspbbtitle.setText("Bukti Lunas PBB Tahun Terakhir diperlukan");
                    buktilunaspbbtitle.setTextColor(getResources().getColor(R.color.pink));

                    return;
                } else if (getFile_npwp() == null) {
                    npwptitle.setText("NPWP diperlukan");
                    npwptitle.setTextColor(getResources().getColor(R.color.pink));

                    return;
                } else if (getFile_suratpersetujuantetangga() == null) {
                    suratpersetujuantetanggatitle.setText("Surat Pernyataan Persetujuan Tetangga diperlukan");
                    suratpersetujuantetanggatitle.setTextColor(getResources().getColor(R.color.pink));

                    return;
                } else if (getFile_suratpermohonan() == null) {
                    suratpermohonantitle.setText("Surat Permohonan diperlukan");
                    suratpermohonantitle.setTextColor(getResources().getColor(R.color.pink));

                    return;
                } else if (getFile_suratrekomendasilurah() == null) {
                    suratrekomendasilurahtitle.setText("Surat Rekomendasi ini diperlukan");
                    suratrekomendasilurahtitle.setTextColor(getResources().getColor(R.color.pink));

                    return;
                }  else {
                    loading = ProgressDialog.show(mContext, null, " Harap Tunggu...", true, false);
                    initAjukanPermohonan();
                }


            }
        });
    }

    private void initAjukanPermohonan() {
        File set_ktp = new File(getFile_ktp());
        File set_buktilunaspbb = new File(getFile_buktilunaspbb());
        File set_syaratnpwp = new File(getFile_npwp());
        File set_suratpersetujuantetangga = new File(getFile_suratpersetujuantetangga());
        File set_suratpermohonan = new File(getFile_suratpermohonan());
        File set_suratrekomendasilurah = new File(getFile_suratrekomendasilurah());


        RequestBody ktp = RequestBody.create(MediaType.parse("image/*"), set_ktp);
        MultipartBody.Part ktp_files =  MultipartBody.Part.createFormData("ktppemohon", set_ktp.getName(), ktp);

        RequestBody buktilunaspbb = RequestBody.create(MediaType.parse("image/*"), set_buktilunaspbb);
        MultipartBody.Part buktilunaspbb_files =  MultipartBody.Part.createFormData("buktilunaspbb", set_buktilunaspbb.getName(), buktilunaspbb);

        RequestBody syaratnpwp = RequestBody.create(MediaType.parse("image/*"), set_syaratnpwp);
        MultipartBody.Part syaratnpwp_files =  MultipartBody.Part.createFormData("syaratnpwp", set_syaratnpwp.getName(), syaratnpwp);

        RequestBody suratpersetujuantetangga = RequestBody.create(MediaType.parse("image/*"), set_suratpersetujuantetangga);
        MultipartBody.Part suratpersetujuantetangga_files =  MultipartBody.Part.createFormData("suratpersetujuantetangga", set_suratpersetujuantetangga.getName(), suratpersetujuantetangga);

        RequestBody suratpermohonan = RequestBody.create(MediaType.parse("image/*"), set_suratpermohonan);
        MultipartBody.Part suratpermohonan_files =  MultipartBody.Part.createFormData("suratpermohonan", set_suratpermohonan.getName(), suratpermohonan);

        RequestBody suratrekomendasilurah = RequestBody.create(MediaType.parse("image/*"), set_suratrekomendasilurah);
        MultipartBody.Part suratrekomendasilurah_files =  MultipartBody.Part.createFormData("suratrekomendasilurah", set_suratrekomendasilurah.getName(), suratrekomendasilurah);

        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), sharedPrefManager.getSPIdUserSting());
        RequestBody nik = RequestBody.create(MediaType.parse("text/plain"), sharedPrefManager.getSpNik());
        RequestBody namaperusahaan = RequestBody.create(MediaType.parse("text/plain"), etnamaperusahaan.getText().toString());
        RequestBody bentukusaha = RequestBody.create(MediaType.parse("text/plain"), etbentukusaha.getText().toString());
        RequestBody npwp = RequestBody.create(MediaType.parse("text/plain"), etnpwp.getText().toString());
        RequestBody kegiatan = RequestBody.create(MediaType.parse("text/plain"), etkegiatan.getText().toString());
        RequestBody sarana = RequestBody.create(MediaType.parse("text/plain"), etsarana.getText().toString());
        RequestBody alamat = RequestBody.create(MediaType.parse("text/plain"), etalamat.getText().toString());
        RequestBody jumlahmodal = RequestBody.create(MediaType.parse("text/plain"), etjumlahmodal.getText().toString());
        RequestBody nopendaftaran = RequestBody.create(MediaType.parse("text/plain"), etnopendaftaran.getText().toString());


        mApiService.Iumk(id, nik, namaperusahaan,bentukusaha,npwp,kegiatan,sarana,alamat,jumlahmodal,nopendaftaran,ktp_files,
                buktilunaspbb_files, syaratnpwp_files, suratpersetujuantetangga_files,suratpermohonan_files,suratrekomendasilurah_files)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){

                                    String msg = jsonRESULTS.getString("msg");

                                    finish();
                                    startActivity(getIntent());
                                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();

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
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }

    public String getFile_ktp() {
        return file_ktp;
    }

    public void setFile_ktp(String file_ktp) {
        this.file_ktp = file_ktp;
    }

    public String getFile_buktilunaspbb() {
        return file_buktilunaspbb;
    }

    public void setFile_buktilunaspbb(String file_buktilunaspbb) {
        this.file_buktilunaspbb = file_buktilunaspbb;
    }

    public String getFile_npwp() {
        return file_npwp;
    }

    public void setFile_npwp(String file_npwp) {
        this.file_npwp = file_npwp;
    }

    public String getFile_suratpersetujuantetangga() {
        return file_suratpersetujuantetangga;
    }

    public void setFile_suratpersetujuantetangga(String file_suratpersetujuantetangga) {
        this.file_suratpersetujuantetangga = file_suratpersetujuantetangga;
    }

    public String getFile_suratpermohonan() {
        return file_suratpermohonan;
    }

    public void setFile_suratpermohonan(String file_suratpermohonan) {
        this.file_suratpermohonan = file_suratpermohonan;
    }

    public String getFile_suratrekomendasilurah() {
        return file_suratrekomendasilurah;
    }

    public void setFile_suratrekomendasilurah(String file_suratrekomendasilurah) {
        this.file_suratrekomendasilurah = file_suratrekomendasilurah;
    }
}
