package id.tempayan.activity;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

public class SrikActivity extends AppCompatActivity {
    private static final int PICK_KTP = 1;
    private static final int PICK_SURATPENGANTAR = 2;
    private  static final int PICK_SURATPERNYATAAN = 3;

    private String file_ktp;
    private String file_suratpengantar;
    private String file_suratpernyataan;

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

    @BindView(R.id.ktppemohon)
    LinearLayout ktppemohon;
    @BindView(R.id.ktppemohonavatar)
    ImageView ktppemohonavatar;
    @BindView(R.id.ktppemohontitle)
    TextView ktppemohontitle;
    @BindView(R.id.ktppemohonsubtitle)
    TextView ktppemohonsubtitle;

    @BindView(R.id.surat_pengantar)
    LinearLayout surat_pengantar;
    @BindView(R.id.surat_pengantaravatar)
    ImageView surat_pengantaravatar;
    @BindView(R.id.surat_pengantartitle)
    TextView surat_pengantartitle;
    @BindView(R.id.surat_pengantarsubtitle)
    TextView surat_pengantarsubtitle;

    @BindView(R.id.suratpernyataan)
    LinearLayout suratpernyataan;
    @BindView(R.id.suratpernyataanavatar)
    ImageView suratpernyataanavatar;
    @BindView(R.id.suratpernyataansubtitle)
    TextView suratpernyataansubtitle;

    @BindView(R.id.etnosurat)
    TextInputEditText etnosurat;
    @BindView(R.id.ettanggalsurat)
    TextInputEditText ettanggalsurat;
    @BindView(R.id.ettanggalkegiatan)
    TextInputEditText ettanggalkegiatan;
    @BindView(R.id.etkeperluan)
    TextInputEditText etkeperluan;

    @BindView(R.id.etjenis_kegiatan)
    TextInputEditText etjenis_kegiatan;

    @BindView(R.id.ethari)
    TextInputEditText ethari;

    @BindView(R.id.etwaktu)
    TextInputEditText etwaktu;

    @BindView(R.id.ettempat)
    TextInputEditText ettempat;

    @BindView(R.id.ethiburan)
    TextInputEditText ethiburan;

    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "yyyy-MM-dd";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

    Calendar myCalendar1 = Calendar.getInstance();
    String dateFormat1 = "yyyy-MM-dd";
    DatePickerDialog.OnDateSetListener date1;
    SimpleDateFormat sdf1 = new SimpleDateFormat(dateFormat1, Locale.ENGLISH);

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srik);

        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);
        mApiService = UtilsApi.getAPIService();

        initSetPemohon();
        initSetTitle();
        initTanggalSurat();
        initTanggalKegiatan();
        initPickKtp();
        initPickSuratPengantar();
        initPickSuratPernyataan();
        initAjukanPermohonancheck();

    }

    private void initTanggalSurat() {

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };
        // onclick - popup datepicker
        ettanggalsurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(mContext, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void initTanggalKegiatan() {

        date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateKegiatan();
            }

        };
        // onclick - popup datepicker
        ettanggalkegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(mContext, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateDate() {
        ettanggalsurat.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateDateKegiatan() {
        ettanggalkegiatan.setText(sdf1.format(myCalendar1.getTime()));
    }

    private void initPickKtp() {

        ktppemohon.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                openGallery(PICK_KTP);
            }});
    }
    private void initPickSuratPengantar() {

        surat_pengantar.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                openGallery(PICK_SURATPENGANTAR);
            }});
    }
    private void initPickSuratPernyataan() {
        suratpernyataan.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                openGallery(PICK_SURATPERNYATAAN);
            }});
    }

    private void openGallery(int req_code){

        EasyImage.openChooserWithGallery(SrikActivity.this,"Pilih Foto :" ,
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
                if (type == PICK_SURATPENGANTAR) {

                    surat_pengantarsubtitle.setText("Berhasil");
                    surat_pengantarsubtitle.setTextColor(getResources().getColor(R.color.green));
                    Glide.with(getApplicationContext())
                            .load(R.drawable.icons8_approval)
                            .into((CircleImageView) findViewById(R.id.surat_pengantaravatar));
                    String file_suratpengantar = imageFile.getAbsolutePath();
                    setFile_suratpengantar(file_suratpengantar);

                }
                if (type == PICK_SURATPERNYATAAN) {

                    suratpernyataansubtitle.setText("Berhasil");
                    suratpernyataansubtitle.setTextColor(getResources().getColor(R.color.green));
                    Glide.with(getApplicationContext())
                            .load(R.drawable.icons8_approval)
                            .into((CircleImageView) findViewById(R.id.suratpernyataanavatar));
                    String filesuratpernyataansubtitle = imageFile.getAbsolutePath();
                    setFile_suratpernyataan(filesuratpernyataansubtitle);
                }
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                Toast.makeText(SrikActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

                if (etnosurat.getText().toString().equals("")) {
                    etnosurat.setError("Nomor Surat diperlukan");
                    etnosurat.requestFocus();
                    return;
                } else if (ettanggalsurat.getText().toString().equals("")) {
                    ettanggalsurat.setError("Tanggal Surat diperlukan");
                    ettanggalsurat.requestFocus();
                    return;
                } else if (etkeperluan.getText().toString().equals("")) {
                    etkeperluan.setError("Keperluan Surat diperlukan");
                    etkeperluan.requestFocus();
                    return;
                } else if (etjenis_kegiatan.getText().toString().equals("")) {
                    etjenis_kegiatan.setError("Jenis Keramaian/Kegiatan Surat diperlukan");
                    etjenis_kegiatan.requestFocus();
                    return;
                } else if (ethari.getText().toString().equals("")) {
                    ethari.setError("Hari diperlukan");
                    ethari.requestFocus();
                    return;
                } else if (ettanggalkegiatan.getText().toString().equals("")) {
                    ettanggalkegiatan.setError("Tanggal Kegiatan diperlukan");
                    ettanggalkegiatan.requestFocus();
                    return;
                } else if (etwaktu.getText().toString().equals("")) {
                    etwaktu.setError("Waktu diperlukan");
                    etwaktu.requestFocus();
                    return;
                } else if (ettempat.getText().toString().equals("")) {
                    ettempat.setError("Tempat diperlukan");
                    ettempat.requestFocus();
                    return;
                } else if (ethiburan.getText().toString().equals("")) {
                    ethiburan.setError("Hiburan diperlukan");
                    ethiburan.requestFocus();
                    return;
                }  else if (getFile_ktp() == null) {
                    ktppemohonsubtitle.setText("KTP Pemohon diperlukan");
                    ktppemohonsubtitle.setTextColor(getResources().getColor(R.color.pink));

                    return;
                } else if (getFile_suratpengantar()== null) {
                    surat_pengantarsubtitle.setText("Surat Pengantar ini diperlukan");
                    surat_pengantarsubtitle.setTextColor(getResources().getColor(R.color.pink));
                    return;
                } else if (getFile_suratpernyataan() == null) {
                    suratpernyataansubtitle.setText("Surat Pernyataan ini diperlukan");
                    suratpernyataansubtitle.setTextColor(getResources().getColor(R.color.pink));

                    return;
                } else {
                    loading = ProgressDialog.show(mContext, null, " Harap Tunggu...", true, false);
                    initAjukanPermohonan();
                }


            }
        });
    }

    private void initAjukanPermohonan() {
        File set_ktp = new File(getFile_ktp());
        File set_suratpengantar = new File(getFile_suratpengantar());
        File set_suratpernyataan = new File(getFile_suratpernyataan());

        RequestBody ktp = RequestBody.create(MediaType.parse("image/*"), set_ktp);
        MultipartBody.Part ktp_files =  MultipartBody.Part.createFormData("ktp_pemohon", set_ktp.getName(), ktp);

        RequestBody suratpengantar = RequestBody.create(MediaType.parse("image/*"), set_suratpengantar);
        MultipartBody.Part suratpengantar_files =  MultipartBody.Part.createFormData("surat_pengantar", set_suratpengantar.getName(), suratpengantar);

        RequestBody suratpernyataan = RequestBody.create(MediaType.parse("image/*"), set_suratpernyataan);
        MultipartBody.Part suratpernyataan_files =  MultipartBody.Part.createFormData("surat_pernyataan", set_suratpernyataan.getName(), suratpernyataan);

        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), sharedPrefManager.getSPIdUserSting());
        RequestBody nik = RequestBody.create(MediaType.parse("text/plain"), sharedPrefManager.getSpNik());
        RequestBody no_surat = RequestBody.create(MediaType.parse("text/plain"), etnosurat.getText().toString());
        RequestBody tanggal_surat = RequestBody.create(MediaType.parse("text/plain"), ettanggalsurat.getText().toString());
        RequestBody keperluan = RequestBody.create(MediaType.parse("text/plain"), etkeperluan.getText().toString());
        RequestBody jeniskegiatan = RequestBody.create(MediaType.parse("text/plain"), etjenis_kegiatan.getText().toString());
        RequestBody hari = RequestBody.create(MediaType.parse("text/plain"), ethari.getText().toString());
        RequestBody tanggal_kegiatan = RequestBody.create(MediaType.parse("text/plain"), ettanggalkegiatan.getText().toString());
        RequestBody waktu = RequestBody.create(MediaType.parse("text/plain"), etwaktu.getText().toString());
        RequestBody tempat = RequestBody.create(MediaType.parse("text/plain"), ettempat.getText().toString());
        RequestBody hiburan = RequestBody.create(MediaType.parse("text/plain"), ethiburan.getText().toString());


        mApiService.Srik(id, nik, no_surat, tanggal_surat, keperluan,jeniskegiatan,hari,tanggal_kegiatan,waktu,tempat,hiburan,ktp_files,suratpengantar_files,suratpernyataan_files)
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
        menu.setTitle(Html.fromHtml("<small>Surat Rekomendasi Izin Keramaian</small>"));
    }

    public String getFile_ktp() {
        return file_ktp;
    }

    public void setFile_ktp(String file_ktp) {
        this.file_ktp = file_ktp;
    }

    public String getFile_suratpengantar() {
        return file_suratpengantar;
    }

    public void setFile_suratpengantar(String file_suratpengantar) {
        this.file_suratpengantar = file_suratpengantar;
    }

    public String getFile_suratpernyataan() {
        return file_suratpernyataan;
    }

    public void setFile_suratpernyataan(String file_suratpernyataan) {
        this.file_suratpernyataan = file_suratpernyataan;
    }
}
