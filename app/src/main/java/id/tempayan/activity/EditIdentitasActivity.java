package id.tempayan.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

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
import id.tempayan.model.SemuaAgamaItem;
import id.tempayan.model.ResponseGolonganDarah;
import id.tempayan.model.SemuaGolonganDarahItem;
import id.tempayan.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditIdentitasActivity extends AppCompatActivity {


    TextInputEditText etnik,etnokk,etnamalengkap,ettempatlahir,ettanggallahir, etalamat,etrt,etrw,etdesa,etkecamatan,etkodepos,etpekerjaan,etstatuskawin,etstatuskeluarga;
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
        // init - set date to current date
        ettanggallahir.setText(sharedPrefManager.getSpTgllahir());

        // set calendar date and update editDate
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
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
                // TODO Auto-generated method stub
                new DatePickerDialog(mContext, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        initSpinerGolDar();

        initSpinerAgama();

        spinnerGolDarah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = parent.getItemAtPosition(position).toString();
                spinnerGolDarah.setSelection(((ArrayAdapter<String>)spinnerGolDarah.getAdapter()).getPosition(selectedName));
//                requestDetailDosen(selectedName);
                Toast.makeText(mContext, "Kamu memilih gol darah " + selectedName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerAgama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = parent.getItemAtPosition(position).toString();
                spinnerAgama.setSelection(((ArrayAdapter<String>)spinnerAgama.getAdapter()).getPosition(selectedName));
//                requestDetailDosen(selectedName);
                Toast.makeText(mContext, "Kamu memilih agama " + selectedName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        set_value();
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

        etdesa = (TextInputEditText) findViewById(R.id.etdesa);
        etdesa.setText(sharedPrefManager.getSpDesa());

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

        etstatuskawin = (TextInputEditText) findViewById(R.id.etstatuskawin);
        etstatuskawin.setText(sharedPrefManager.getSpStatuskawin());


        etstatuskeluarga = (TextInputEditText) findViewById(R.id.etstatuskeluarga);
        etstatuskeluarga.setText(sharedPrefManager.getSpStatuskk());


    }
}
