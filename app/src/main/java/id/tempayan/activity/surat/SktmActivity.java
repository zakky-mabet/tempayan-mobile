package id.tempayan.activity.surat;

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

public class SktmActivity extends AppCompatActivity {

    private static final String TAG = "SKKB ACTIVIRY";

    private static final String tag = "SKKB ACTIVIRY SMALL";

    private static final int PICK_KTP = 1;

    private static final int PICK_KK_PEMOHON = 2;

    private  static final int PICK_SURAT_PENGANTAR = 3;

    private String file_ktp;
    private String file_kk;
    private String file_speng;

    SharedPrefManager sharedPrefManager;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context mContext;

    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.ktppemohonavatar)
    ImageView ktppemohonavatar;
    @BindView(R.id.tvnamalengkap)
    TextView tvnamalengkap;
    @BindView(R.id.tvnik)
    TextView tvnik;
    @BindView(R.id.ajukanpermohonan)
    Button ajukanpermohonan;

    @BindView(R.id.suratpengantarpemohonsubtitle)
    TextView suratpengantarpemohonsubtitle;

    @BindView(R.id.kkpemohonsubtitle)
    TextView kkpemohonsubtitle;

    @BindView(R.id.ktppemohonsubtitle)
    TextView ktppemohonsubtitle;

    TextInputEditText etnosurat,ettanggalsurat,etkeperluan,etnamalurah,etnip,etjabatan;

    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "yyyy-MM-dd";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sktm);

        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);
        mApiService = UtilsApi.getAPIService();

        initSetTitle();
        initSetPemohon();
        initTanggalSurat();
        initPickKtp();
        initPickKKPemohon();
        initPickSuratPengantar();
        initAjukanPermohonancheck();
    }

    private void initTanggalSurat() {
        ettanggalsurat = (TextInputEditText) findViewById(R.id.ettanggalsurat);
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

    private void updateDate() {
        ettanggalsurat.setText(sdf.format(myCalendar.getTime()));
    }

    private void initSetPemohon() {
        Glide.with(getApplicationContext())
                .load(BASE_URL_IMAGE+sharedPrefManager.getSPPhoto())
                .into((CircleImageView) findViewById(R.id.ivAvatar));
        tvnik.setText(sharedPrefManager.getSpNik());
        tvnamalengkap.setText(sharedPrefManager.getSPNama());
    }

    private void initPickKtp() {
        LinearLayout ktppemohon = (LinearLayout)findViewById(R.id.ktppemohon);
        ktppemohon.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                openGallery(PICK_KTP);
            }});
    }
    private void initPickKKPemohon() {
        LinearLayout kkpemohon = (LinearLayout)findViewById(R.id.kkpemohon);
        kkpemohon.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                openGallery(PICK_KK_PEMOHON);
            }});
    }
    private void initPickSuratPengantar() {
        LinearLayout suratpengantarpemohon = (LinearLayout)findViewById(R.id.suratpengantarpemohon);
        suratpengantarpemohon.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                openGallery(PICK_SURAT_PENGANTAR);
            }});
    }

    private void openGallery(int req_code){

        EasyImage.openChooserWithGallery(SktmActivity.this,"Pilih Foto :" ,
                req_code);

    }

    private void initSetTitle() {
        ActionBar menu = getSupportActionBar();
        assert menu != null;
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);
        menu.setElevation(0);
        menu.setTitle(Html.fromHtml("<small>Surat Keterangan Tidak Mampu</small>"));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                if (type == PICK_KTP) {
                    Log.i(TAG, "KTP: " + imageFile.getAbsolutePath());
                    ktppemohonsubtitle.setText("Berhasil");
                    ktppemohonsubtitle.setTextColor(getResources().getColor(R.color.green));
                    Glide.with(getApplicationContext())
                            .load(R.drawable.icons8_approval)
                            .into((CircleImageView) findViewById(R.id.ktppemohonavatar));
                    String file_ktp = imageFile.getAbsolutePath();
                    setFile_ktp(file_ktp);

                }
                if (type == PICK_KK_PEMOHON) {
                    Log.i(TAG, "KK: " + imageFile.getAbsolutePath());
                    kkpemohonsubtitle.setText("Berhasil");
                    kkpemohonsubtitle.setTextColor(getResources().getColor(R.color.green));
                    Glide.with(getApplicationContext())
                            .load(R.drawable.icons8_approval)
                            .into((CircleImageView) findViewById(R.id.kkpemohonavatar));
                    String file_kk = imageFile.getAbsolutePath();
                    setFile_kk(file_kk);
                }
                if (type == PICK_SURAT_PENGANTAR) {
                    Log.i(TAG, "SURAT PENGANTAR: " + imageFile.getAbsolutePath());
                    suratpengantarpemohonsubtitle.setText("Berhasil");
                    suratpengantarpemohonsubtitle.setTextColor(getResources().getColor(R.color.green));
                    Glide.with(getApplicationContext())
                            .load(R.drawable.icons8_approval)
                            .into((CircleImageView) findViewById(R.id.suratpengantarpemohonavatar));
                    String file_speng = imageFile.getAbsolutePath();
                    setFile_speng(file_speng);
                }
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                Toast.makeText(SktmActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                super.onCanceled(source, type);
            }
        });
    }

    private void initAjukanPermohonancheck() {
        etnosurat = (TextInputEditText) findViewById(R.id.etnosurat);
        etkeperluan = (TextInputEditText) findViewById(R.id.etkeperluan);
        etnamalurah = (TextInputEditText) findViewById(R.id.etnamalurah);
        etnip = (TextInputEditText) findViewById(R.id.etnip);
        etjabatan = (TextInputEditText) findViewById(R.id.etjabatan);

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
                } else if (etnamalurah.getText().toString().equals("")) {
                    etnamalurah.setError("Nama Lurah diperlukan");
                    etnamalurah.requestFocus();
                    return;
                }  else if (etnip.getText().toString().equals("")) {
                    etnip.setError("NIP diperlukan");
                    etnip.requestFocus();
                    return;
                }  else if (etjabatan.getText().toString().equals("")) {
                    etjabatan.setError("Jabatan diperlukan");
                    etjabatan.requestFocus();
                    return;
                }  else if (etkeperluan.getText().toString().equals("")) {
                    etkeperluan.setError("Keperluan Surat diperlukan");
                    etkeperluan.requestFocus();
                    return;
                } else if (getFile_ktp() == null) {
                    ktppemohonsubtitle.setText("KTP Pemohon diperlukan");
                    ktppemohonsubtitle.setTextColor(getResources().getColor(R.color.pink));

                    return;
                } else if (getFile_kk()== null) {
                    kkpemohonsubtitle.setText("KK Pemohon diperlukan");
                    kkpemohonsubtitle.setTextColor(getResources().getColor(R.color.pink));
                    return;
                } else if (getFile_speng() == null) {
                    suratpengantarpemohonsubtitle.setText("Surat Pengantar dari Kelurahan / Desa diperlukan");
                    suratpengantarpemohonsubtitle.setTextColor(getResources().getColor(R.color.pink));

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
        File set_kk = new File(getFile_kk());
        File set_speng = new File(getFile_speng());

        RequestBody ktp = RequestBody.create(MediaType.parse("image/*"), set_ktp);
        MultipartBody.Part ktp_files =  MultipartBody.Part.createFormData("ktp_pemohon", set_ktp.getName(), ktp);

        RequestBody kk = RequestBody.create(MediaType.parse("image/*"), set_kk);
        MultipartBody.Part kk_files =  MultipartBody.Part.createFormData("kk_pemohon", set_kk.getName(), kk);

        RequestBody speng = RequestBody.create(MediaType.parse("image/*"), set_speng);
        MultipartBody.Part speng_files =  MultipartBody.Part.createFormData("surat_pengantar", set_speng.getName(), speng);

        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), sharedPrefManager.getSPIdUserSting());
        RequestBody nik = RequestBody.create(MediaType.parse("text/plain"), sharedPrefManager.getSpNik());
        RequestBody no_surat = RequestBody.create(MediaType.parse("text/plain"), etnosurat.getText().toString());
        RequestBody tanggal_surat = RequestBody.create(MediaType.parse("text/plain"), ettanggalsurat.getText().toString());
        RequestBody keperluan = RequestBody.create(MediaType.parse("text/plain"), etkeperluan.getText().toString());

        RequestBody namalurah = RequestBody.create(MediaType.parse("text/plain"), etnamalurah.getText().toString());
        RequestBody nip = RequestBody.create(MediaType.parse("text/plain"), etnip.getText().toString());
        RequestBody jabatan = RequestBody.create(MediaType.parse("text/plain"), etjabatan.getText().toString());

        mApiService.Sktm(id, nik, no_surat, tanggal_surat, keperluan,namalurah,nip ,jabatan, ktp_files, kk_files, speng_files)
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

    public String getFile_kk() {
        return file_kk;
    }

    public void setFile_kk(String file_kk) {
        this.file_kk = file_kk;
    }

    public String getFile_speng() {
        return file_speng;
    }

    public void setFile_speng(String file_speng) {
        this.file_speng = file_speng;
    }
}
