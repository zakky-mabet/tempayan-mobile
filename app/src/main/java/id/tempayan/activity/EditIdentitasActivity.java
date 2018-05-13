package id.tempayan.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import id.tempayan.R;
import id.tempayan.apihelper.BaseApiService;
import id.tempayan.apihelper.UtilsApi;
import id.tempayan.util.SharedPrefManager;

public class EditIdentitasActivity extends AppCompatActivity {

    String[] languages = { "C","C++","Java","C#","PHP","JavaScript","jQuery","AJAX","JSON" };

    TextInputEditText etnik,etnokk,etnamalengkap,ettempatlahir,ettanggallahir, etalamat,etrt,etrw,etdesa,etkecamatan,etkodepos,etagama,etpekerjaan,etkewarganegaraan,etstatuskawin,etgolongandarah,etstatuskeluarga;
    RadioGroup rgjeniskelamin, rgkewarganegaraan;
    RadioButton rblakilaki, rbperempuan, rbwni, rbwna;

    Context mContext;
    ProgressDialog loading;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;


    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "yyyy-MM-dd";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_identitas);

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, languages);
        //Find TextView control
        AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.languages);
        //Set the number of characters the user must type before the drop down list is shown
        acTextView.setThreshold(1);
        //Set the adapter
        acTextView.setAdapter(adapter);

        set_value();
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

        etagama = (TextInputEditText) findViewById(R.id.etagama);
        etagama.setText(sharedPrefManager.getSpAgama());

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

        etgolongandarah = (TextInputEditText) findViewById(R.id.etgolongandarah);
        etgolongandarah.setText(sharedPrefManager.getSpGoldarah());

        etstatuskeluarga = (TextInputEditText) findViewById(R.id.etstatuskeluarga);
        etstatuskeluarga.setText(sharedPrefManager.getSpStatuskk());


    }
}
