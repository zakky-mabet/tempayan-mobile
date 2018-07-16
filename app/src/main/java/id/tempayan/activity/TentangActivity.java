package id.tempayan.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.tempayan.R;

public class TentangActivity extends AppCompatActivity {

    @BindView(R.id.tentang)
    TextView tentang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);

        ButterKnife.bind(this);


        tentang.setText(Html.fromHtml("<p align=\"justify\"><b>Tempayan Mobile</b> merupakan Aplikasi Pelayanan Administrasi Terpadu Kecamatan Koba " +
                "dibuat dengan tujuan memudahkan masyarakat dalam mengajukan surat permohonan seperti surat perizinan dan non perizinan. </p>" +
                "<p align=\"justify\">Sehingga pelayanan administrasi keamatan dpat diakses dan dilakukan oleh masyarakat Kecamatan Koba melalui Smartphone Android.</p>" +
                "Semoga Bermanfaat."));

        initSetTitle();
    }

    private void initSetTitle() {

        ActionBar menu = getSupportActionBar();
        assert menu != null;
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);
        menu.setElevation(0);
        menu.setTitle("Tentang Aplikasi");
    }
}
