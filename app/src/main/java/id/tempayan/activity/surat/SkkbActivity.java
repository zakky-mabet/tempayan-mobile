package id.tempayan.activity.surat;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import id.tempayan.R;
import id.tempayan.apihelper.BaseApiService;
import id.tempayan.apihelper.UtilsApi;
import id.tempayan.util.SharedPrefManager;

import static id.tempayan.apihelper.UtilsApi.BASE_URL_IMAGE;

public class SkkbActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    private static final int PICKFILE_RESULT_CODE = 1;

    private static final int PICK_KK_PEMOHON = 2;

    private  static final int PICK_SURAT_PENGANTAR = 3;


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

    TextInputEditText ettanggalsurat;

    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "yyyy-MM-dd";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skkb);

        ButterKnife.bind(this);
        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);
        mApiService = UtilsApi.getAPIService();

        initSetTitle();
        initSetPemohon();
        initPickKtp();
        initPickKKPemohon();
        initPickSuratPengantar();
        initTanggalSurat();

    }

    private void initTanggalSurat() {
        ettanggalsurat = (TextInputEditText) findViewById(R.id.ettanggalsurat);
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
        ettanggalsurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(mContext, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    private void updateDate() {
        ettanggalsurat.setText(sdf.format(myCalendar.getTime()));
    }

    private void initPickKtp() {

        LinearLayout ktppemohon = (LinearLayout)findViewById(R.id.ktppemohon);

        ktppemohon.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String[] ACCEPT_MIME_TYPES = {"application/pdf","image/*"};
                Intent intent = new Intent();
                intent.setType("image/*,application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_MIME_TYPES, ACCEPT_MIME_TYPES);
                startActivityForResult(Intent.createChooser(intent, "Select File"), PICKFILE_RESULT_CODE);

            }});
    }

    private void initPickKKPemohon() {

        LinearLayout kkpemohon = (LinearLayout)findViewById(R.id.kkpemohon);
        kkpemohon.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String[] ACCEPT_MIME_TYPES = {"application/pdf","image/*"};
                Intent intent = new Intent();
                intent.setType("image/*,application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_MIME_TYPES, ACCEPT_MIME_TYPES);
                startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_KK_PEMOHON);

            }});
    }

    private void initPickSuratPengantar() {

        LinearLayout suratpengantarpemohon = (LinearLayout)findViewById(R.id.suratpengantarpemohon);
        suratpengantarpemohon.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String[] ACCEPT_MIME_TYPES = {"application/pdf","image/*"};
                Intent intent = new Intent();
                intent.setType("image/*,application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_MIME_TYPES, ACCEPT_MIME_TYPES);
                startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_SURAT_PENGANTAR);

            }});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        Log.i(TAG, "onActivityResult: "+ requestCode);

            if (requestCode == PICKFILE_RESULT_CODE ) {
                if(resultCode==RESULT_OK){
                    String FilePath = data.getData().getPath();
                    TextView ktppemohonsubtitle = (TextView)findViewById(R.id.ktppemohonsubtitle);
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getApplication().getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }

                    String extension = displayName.substring(displayName.lastIndexOf(".") + 1);

                    if (extension.equals("PDF") || extension.equals("pdf") || extension.equals("jpg") || extension.equals("JPG") || extension.equals("png") || extension.equals("PNG")) {
                        ktppemohonsubtitle.setText("Berhasil");
                        ktppemohonsubtitle.setTextColor(getResources().getColor(R.color.green));

                        Glide.with(getApplicationContext())
                                .load(R.drawable.icons8_approval)
                                .into((CircleImageView) findViewById(R.id.ktppemohonavatar));
                    } else {
                        ktppemohonsubtitle.setText("Format File Salah");
                        ktppemohonsubtitle.setTextColor(getResources().getColor(R.color.pink));
                        Glide.with(getApplicationContext())
                                .load(R.drawable.icons8_cancel)
                                .into((CircleImageView) findViewById(R.id.ktppemohonavatar));

                    }
                    Log.i(TAG, "file : "+ extension);


                }
            }
            else if (requestCode == PICK_KK_PEMOHON ) {
                if(resultCode==RESULT_OK){
                    String FilePath = data.getData().getPath();
                    TextView kkpemohonsubtitle = (TextView)findViewById(R.id.kkpemohonsubtitle);

                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getApplication().getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }

                    String extension = displayName.substring(displayName.lastIndexOf(".") + 1);

                    if (extension.equals("PDF") || extension.equals("pdf") || extension.equals("jpg") || extension.equals("JPG") || extension.equals("png") || extension.equals("PNG")) {
                        kkpemohonsubtitle.setText("Berhasil");
                        kkpemohonsubtitle.setTextColor(getResources().getColor(R.color.green));

                        Glide.with(getApplicationContext())
                                .load(R.drawable.icons8_approval)
                                .into((CircleImageView) findViewById(R.id.kkpemohonavatar));
                    } else {
                        kkpemohonsubtitle.setText("Format File Salah");
                        kkpemohonsubtitle.setTextColor(getResources().getColor(R.color.pink));
                        Glide.with(getApplicationContext())
                                .load(R.drawable.icons8_cancel)
                                .into((CircleImageView) findViewById(R.id.kkpemohonavatar));

                    }
                    Log.i(TAG, "file : "+ extension);

            }
        }
            else if (requestCode == PICK_SURAT_PENGANTAR ) {
                if(resultCode==RESULT_OK){
                    String FilePath = data.getData().getPath();
                    TextView suratpengantarpemohonsubtitle = (TextView)findViewById(R.id.suratpengantarpemohonsubtitle);

                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getApplication().getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }

                    String extension = displayName.substring(displayName.lastIndexOf(".") + 1);

                    if (extension.equals("PDF") || extension.equals("pdf") || extension.equals("jpg") || extension.equals("JPG") || extension.equals("png") || extension.equals("PNG")) {
                        suratpengantarpemohonsubtitle.setText("Berhasil");
                        suratpengantarpemohonsubtitle.setTextColor(getResources().getColor(R.color.green));

                        Glide.with(getApplicationContext())
                                .load(R.drawable.icons8_approval)
                                .into((CircleImageView) findViewById(R.id.suratpengantarpemohonavatar));
                    } else {
                        suratpengantarpemohonsubtitle.setText("Format File Salah");
                        suratpengantarpemohonsubtitle.setTextColor(getResources().getColor(R.color.pink));
                        Glide.with(getApplicationContext())
                                .load(R.drawable.icons8_cancel)
                                .into((CircleImageView) findViewById(R.id.suratpengantarpemohonavatar));

                    }
                    Log.i(TAG, "file : "+ extension);

                }
            }


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
        menu.setTitle("Surat Keterangan Kelakuan Baik");

    }


}
