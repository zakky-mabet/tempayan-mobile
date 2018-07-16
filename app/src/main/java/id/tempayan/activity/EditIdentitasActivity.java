package id.tempayan.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.tempayan.R;
import id.tempayan.apihelper.BaseApiService;
import id.tempayan.apihelper.UtilsApi;
import id.tempayan.model.ResponseAgama;
import id.tempayan.model.ResponseGolonganDarah;
import id.tempayan.model.ResponseKelurahan;
import id.tempayan.model.ResponseStatusKawin;
import id.tempayan.model.ResponseStatusKeluarga;
import id.tempayan.model.SemuaAgamaItem;
import id.tempayan.model.SemuaGolonganDarahItem;
import id.tempayan.model.SemuaKelurahanItem;
import id.tempayan.model.SemuaStatusKawinItem;
import id.tempayan.model.SemuaStatusKeluargaItem;
import id.tempayan.util.SharedPrefManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditIdentitasActivity extends AppCompatActivity {

    TextInputEditText etnik,etnokk,etnamalengkap,ettempatlahir,ettanggallahir, etalamat,etrt,etrw,etkecamatan,etkodepos,etpekerjaan;
    RadioGroup rgjeniskelamin, rgkewarganegaraan;
    RadioButton rblakilaki, rbperempuan, rbwni, rbwna;

    Context mContext;
    ProgressDialog loading;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;

    @BindView(R.id.spinnerGolDarah)
    Spinner spinnerGolDarah;

    @BindView(R.id.spinnerAgama)
    Spinner spinnerAgama;

    @BindView(R.id.spinnerStatusKawin)
    Spinner spinnerStatusKawin;

    @BindView(R.id.spinnerStatusKeluarga)
    Spinner spinnerStatusKeluarga;

    @BindView(R.id.spinnerKelurahan)
    Spinner spinnerKelurahan;

    @BindView(R.id.bubah)
    Button bubah;

    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "yyyy-MM-dd";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_identitas);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init utilapis pada paket apihelper
        sharedPrefManager = new SharedPrefManager(this);

        ettanggallahir = (TextInputEditText) findViewById(R.id.ettanggallahir);
        ettanggallahir.setText(sharedPrefManager.getSpTgllahir());
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
        ettanggallahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(mContext, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        initSpinerGolDar();

        spinnerGolDarah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = parent.getItemAtPosition(position).toString();
                spinnerGolDarah.setSelection(((ArrayAdapter<String>)spinnerGolDarah.getAdapter()).getPosition(selectedName));
                //requestDetailDosen(selectedName);
                //Toast.makeText(mContext, "Kamu memilih gol darah " + selectedName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initSpinerAgama();

        spinnerAgama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = parent.getItemAtPosition(position).toString();
                spinnerAgama.setSelection(((ArrayAdapter<String>)spinnerAgama.getAdapter()).getPosition(selectedName));
                //requestDetailDosen(selectedName);
                //Toast.makeText(mContext, "Kamu memilih agama " + selectedName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initSpinerKelurahan();

        spinnerKelurahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = parent.getItemAtPosition(position).toString();
                spinnerKelurahan.setSelection(((ArrayAdapter<String>)spinnerKelurahan.getAdapter()).getPosition(selectedName));
                //requestDetailDosen(selectedName);
                //Toast.makeText(mContext, "Kamu memilih agama " + selectedName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initSpinerStatusKawin();

        spinnerStatusKawin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = parent.getItemAtPosition(position).toString();
                spinnerStatusKawin.setSelection(((ArrayAdapter<String>)spinnerStatusKawin.getAdapter()).getPosition(selectedName));
                //requestDetailDosen(selectedName);
                //Toast.makeText(mContext, "Kamu memilih agama " + selectedName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initSpinerStatusKeluarga();

        spinnerStatusKeluarga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = parent.getItemAtPosition(position).toString();
                spinnerStatusKeluarga.setSelection(((ArrayAdapter<String>)spinnerStatusKeluarga.getAdapter()).getPosition(selectedName));
                //requestDetailDosen(selectedName);
                //Toast.makeText(mContext, "Kamu memilih agama " + selectedName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        set_value();
    }


    private void initSpinerStatusKeluarga() {
        mApiService.getSemuaStatusKeluarga().enqueue(new Callback<ResponseStatusKeluarga>() {
            @Override
            public void onResponse(Call<ResponseStatusKeluarga> call, Response<ResponseStatusKeluarga> response) {
                if (response.isSuccessful()) {
                    List<SemuaStatusKeluargaItem> SemuaStatusKeluargaItems = response.body().getSemuastatuskeluarga();
                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < SemuaStatusKeluargaItems.size(); i++){
                        listSpinner.add(SemuaStatusKeluargaItems.get(i).getNama());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerStatusKeluarga.setAdapter(adapter);

                    String set_default = sharedPrefManager.getSpStatuskk(); //the value you want the position for
                    ArrayAdapter myAdap = (ArrayAdapter) spinnerStatusKeluarga.getAdapter(); //cast to an ArrayAdapter
                    int spinnerPosition = myAdap.getPosition(set_default);
                    spinnerStatusKeluarga.setSelection(spinnerPosition);

                } else {
                    Toast.makeText(mContext, "Gagal mengambil data agama", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStatusKeluarga> call, Throwable t) {
                //loading.dismiss();
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void initSpinerStatusKawin() {
        mApiService.getSemuaStatusKawin().enqueue(new Callback<ResponseStatusKawin>() {
            @Override
            public void onResponse(Call<ResponseStatusKawin> call, Response<ResponseStatusKawin> response) {
                if (response.isSuccessful()) {
                    List<SemuaStatusKawinItem> SemuaStatusKawinItems = response.body().getSemuastatuskawin();
                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < SemuaStatusKawinItems.size(); i++){
                        listSpinner.add(SemuaStatusKawinItems.get(i).getNama());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerStatusKawin.setAdapter(adapter);

                    String set_default = sharedPrefManager.getSpStatuskawin(); //the value you want the position for
                    ArrayAdapter myAdap = (ArrayAdapter) spinnerStatusKawin.getAdapter(); //cast to an ArrayAdapter
                    int spinnerPosition = myAdap.getPosition(set_default);
                    spinnerStatusKawin.setSelection(spinnerPosition);

                } else {
                    Toast.makeText(mContext, "Gagal mengambil data agama", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStatusKawin> call, Throwable t) {
                //loading.dismiss();
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinerAgama() {
        mApiService.getSemuaAgama().enqueue(new Callback<ResponseAgama>() {
            @Override
            public void onResponse(Call<ResponseAgama> call, Response<ResponseAgama> response) {
                if (response.isSuccessful()) {
                    List<SemuaAgamaItem> SemuaAgamaItems = response.body().getSemuaagama();
                    List<String> listSpinner_agama = new ArrayList<String>();
                    for (int i = 0; i < SemuaAgamaItems.size(); i++){
                        listSpinner_agama.add(SemuaAgamaItems.get(i).getNama());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listSpinner_agama);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerAgama.setAdapter(adapter);

                    String set_default = sharedPrefManager.getSpAgama(); //the value you want the position for
                    ArrayAdapter myAdap = (ArrayAdapter) spinnerAgama.getAdapter(); //cast to an ArrayAdapter
                    int spinnerPosition = myAdap.getPosition(set_default);
                    spinnerAgama.setSelection(spinnerPosition);

                } else {
                    Toast.makeText(mContext, "Gagal mengambil data agama", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAgama> call, Throwable t) {
                //loading.dismiss();
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinerKelurahan() {
        mApiService.getSemuaKelurahan().enqueue(new Callback<ResponseKelurahan>() {
            @Override
            public void onResponse(Call<ResponseKelurahan> call, Response<ResponseKelurahan> response) {
                if (response.isSuccessful()) {
                    List<SemuaKelurahanItem> SemuaKelurahanItems = response.body().getSemuakelurahan();
                    List<String> listSpinner_kelurahan = new ArrayList<String>();
                    for (int i = 0; i < SemuaKelurahanItems.size(); i++){
                        listSpinner_kelurahan.add(SemuaKelurahanItems.get(i).getNama());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listSpinner_kelurahan);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerKelurahan.setAdapter(adapter);

                    String set_default = sharedPrefManager.getSpDesa(); //the value you want the position for
                    ArrayAdapter myAdap = (ArrayAdapter) spinnerKelurahan.getAdapter(); //cast to an ArrayAdapter
                    int spinnerPosition = myAdap.getPosition(set_default);
                    spinnerKelurahan.setSelection(spinnerPosition);

                } else {
                    Toast.makeText(mContext, "Gagal mengambil data Kelurahan/desa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseKelurahan> call, Throwable t) {
                //loading.dismiss();
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinerGolDar() {
        //loading = ProgressDialog.show(mContext, null, "harap tunggu...", true, false);

        mApiService.getSemuaGolonganDarah().enqueue(new Callback<ResponseGolonganDarah>() {
            @Override
            public void onResponse(Call<ResponseGolonganDarah> call, Response<ResponseGolonganDarah> response) {
                if (response.isSuccessful()) {


                    //loading.dismiss();
                    List<SemuaGolonganDarahItem> SemuaGolonganDarahItems = response.body().getSemuagolongandarah();
                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < SemuaGolonganDarahItems.size(); i++){
                        listSpinner.add(SemuaGolonganDarahItems.get(i).getNama_gol());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerGolDarah.setAdapter(adapter);

                    String set_default = sharedPrefManager.getSpGoldarah(); //the value you want the position for
                    ArrayAdapter myAdap = (ArrayAdapter) spinnerGolDarah.getAdapter(); //cast to an ArrayAdapter
                    int spinnerPosition = myAdap.getPosition(set_default);
                    spinnerGolDarah.setSelection(spinnerPosition);

                } else {
                    //loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data golongan darah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseGolonganDarah> call, Throwable t) {
                //loading.dismiss();
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateDate() {
        ettanggallahir.setText(sdf.format(myCalendar.getTime()));
    }

    private void set_value() {
        etnik = (TextInputEditText) findViewById(R.id.etnik);
        etnik.setText(sharedPrefManager.getSpNik());

        etnokk = (TextInputEditText) findViewById(R.id.etnokk);
        etnokk.setText(sharedPrefManager.getSpNokk());

        etnamalengkap = (TextInputEditText) findViewById(R.id.etnamalengkap);
        etnamalengkap.setText(sharedPrefManager.getSPNama());

        ettempatlahir = (TextInputEditText) findViewById(R.id.ettempatlahir);
        ettempatlahir.setText(sharedPrefManager.getSpTmplahir());

        if (sharedPrefManager.getSpJnskelamin().equals("Laki-laki")){
            rblakilaki = (RadioButton) findViewById(R.id.rblakilaki);
            rblakilaki.setChecked(true);
        } else{
            rbperempuan = (RadioButton) findViewById(R.id.rbperempuan);
            rbperempuan.setChecked(true);
        }

        etalamat = (TextInputEditText) findViewById(R.id.etalamat);
        etalamat.setText(sharedPrefManager.getSpAlamat());

        etrt = (TextInputEditText) findViewById(R.id.etrt);
        etrt.setText(sharedPrefManager.getSpRt());

        etrw = (TextInputEditText) findViewById(R.id.etrw);
        etrw.setText(sharedPrefManager.getSpRw());

        etkecamatan = (TextInputEditText) findViewById(R.id.etkecamatan);
        etkecamatan.setText(sharedPrefManager.getSpKecamatan());

        etkodepos = (TextInputEditText) findViewById(R.id.etkodepos);
        etkodepos.setText(sharedPrefManager.getSpKdpos());

        etpekerjaan = (TextInputEditText) findViewById(R.id.etpekerjaan);
        etpekerjaan.setText(sharedPrefManager.getSpPekerjaan());

        if (sharedPrefManager.getSpKewarganegaraan().equals("WNI")){
            rbwni = (RadioButton) findViewById(R.id.rbwni);
            rbwni.setChecked(true);
        } else{
            rbwna = (RadioButton) findViewById(R.id.rbwna);
            rbwna.setChecked(true);
        }

        bubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etnik.getText().toString().equals("")) {
                    etnik.setError("NIK diperlukan");
                    etnik.requestFocus();
                    return;
                } else if (etnokk.getText().toString().equals("")) {
                    etnokk.setError("No KK diperlukan");
                    etnokk.requestFocus();
                    return;
                } else if (etnamalengkap.getText().toString().equals("")) {
                    etnamalengkap.setError("Nama Lengkap diperlukan");
                    etnamalengkap.requestFocus();
                    return;
                }
                else if (ettempatlahir.getText().toString().equals("")) {
                    ettempatlahir.setError("Tempat Lahir diperlukan");
                    ettempatlahir.requestFocus();
                    return;
                } else if (ettanggallahir.getText().toString().equals("")) {
                    ettanggallahir.setError("Tanggal Lahir diperlukan");
                    ettanggallahir.requestFocus();
                    return;
                } else if (etalamat.getText().toString().equals("")) {
                    etalamat.setError("Alamat diperlukan");
                    etalamat.requestFocus();
                    return;
                } else if (etrt.getText().toString().equals("")) {
                    etrt.setError("RT diperlukan");
                    etrt.requestFocus();
                    return;
                } else if (etrw.getText().toString().equals("")) {
                    etrw.setError("RW diperlukan");
                    etrw.requestFocus();
                    return;
                } else if (etkecamatan.getText().toString().equals("")) {
                    etkecamatan.setError("Kecamatan diperlukan");
                    etkecamatan.requestFocus();
                    return;
                } else if (etkodepos.getText().toString().equals("")) {
                    etkodepos.setError("Kode Pos diperlukan");
                    etkodepos.requestFocus();
                    return;
                } else if (etpekerjaan.getText().toString().equals("")) {
                    etpekerjaan.setError("Pekerjaan diperlukan");
                    etpekerjaan.requestFocus();
                    return;
                } else {
                    loading = ProgressDialog.show(mContext, null, etnamalengkap.getText()+" Harap Tunggu...", true, false);
                    requestupdateidentitas();
                }
            }
        });

    }

    private void requestupdateidentitas() {

        rgjeniskelamin = (RadioGroup) findViewById(R.id.rgjeniskelamin);
        int selectedId = rgjeniskelamin.getCheckedRadioButtonId();
        final RadioButton jenis_kelamin = (RadioButton) findViewById(selectedId);

        rgkewarganegaraan = (RadioGroup) findViewById(R.id.rgkewarganegaraan);
        int selectedId_kw = rgkewarganegaraan.getCheckedRadioButtonId();
        final RadioButton kewarganegaraan = (RadioButton) findViewById(selectedId_kw);

        Spinner spinner_agama = (Spinner) findViewById(R.id.spinnerAgama);
        final String get_agama = spinner_agama.getSelectedItem().toString();

        Spinner spinner_statuskawin = (Spinner) findViewById(R.id.spinnerStatusKawin);
        final String get_status_kawin = spinner_statuskawin.getSelectedItem().toString();

        Spinner spinner_goldarah = (Spinner) findViewById(R.id.spinnerGolDarah);
        final String get_goldarah = spinner_goldarah.getSelectedItem().toString();

        Spinner spinner_statuskk = (Spinner) findViewById(R.id.spinnerStatusKeluarga);
        final String get_statuskk = spinner_statuskk.getSelectedItem().toString();

        Spinner spinner_desa = (Spinner) findViewById(R.id.spinnerKelurahan);
        final String get_desa = spinner_desa.getSelectedItem().toString();


        mApiService.ubahidentitasrequest(sharedPrefManager.getSPIdUserSting(), etnik.getText().toString(), etnokk.getText().toString(),
                etnamalengkap.getText().toString(), ettempatlahir.getText().toString(), ettanggallahir.getText().toString(), jenis_kelamin.getText().toString(),
                etalamat.getText().toString(), etrt.getText().toString(), etrw.getText().toString(), get_desa, etkecamatan.getText().toString(),
                etkodepos.getText().toString(), get_agama, etpekerjaan.getText().toString(), kewarganegaraan.getText().toString(), get_status_kawin, get_goldarah, get_statuskk  )
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){

                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, etnamalengkap.getText().toString());
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NIK,  etnik.getText().toString());
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NOKK, etnokk.getText().toString());
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_KDPOS, etkodepos.getText().toString());
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_TTL, ettempatlahir.getText().toString()+", "+ettanggallahir.getText().toString());
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_TMPLAHIR, ettempatlahir.getText().toString());
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_TGLLAHIR, ettanggallahir.getText().toString());
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_JNSKELAMIN, jenis_kelamin.getText().toString());
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_ALAMAT, etalamat.getText().toString());
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_RT, etrt.getText().toString());
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_RW, etrw.getText().toString());
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_RTRW, etrt.getText().toString()+" / "+etrw.getText().toString());
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_DESA, get_desa);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_KECAMATAN, etkecamatan.getText().toString());
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_AGAMA, get_agama);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_PEKERJAAN, etpekerjaan.getText().toString());
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_KEWARGANEGARAAN, kewarganegaraan.getText().toString());
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_STATUSKAWIN, get_status_kawin);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_GOLDARAH, get_goldarah);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_STATUSKK, get_statuskk);

                                    String msg = jsonRESULTS.getString("msg");
                                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(mContext, IdentitasActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();

                                } else {
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
                        // Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }
}
