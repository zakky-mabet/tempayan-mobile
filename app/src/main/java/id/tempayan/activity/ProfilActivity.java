package id.tempayan.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import id.tempayan.R;
import id.tempayan.util.SharedPrefManager;

import static id.tempayan.apihelper.UtilsApi.BASE_URL_IMAGE;

public class ProfilActivity extends AppCompatActivity {

    private CircleImageView imgview_fotoprofil;

    TextView tvResultNama;
    TextView tvResultEmail;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(id.tempayan.R.layout.activity_profil);

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        sharedPrefManager = new SharedPrefManager(this);

        tvResultEmail = (TextView) findViewById(R.id.tvResultEmail);
        tvResultEmail.setText(sharedPrefManager.getSPEmail());

        tvResultNama = (TextView) findViewById(R.id.tvResultNama);
        tvResultNama.setText(sharedPrefManager.getSPNama());

        Glide.with(getApplicationContext())
                .load(BASE_URL_IMAGE+sharedPrefManager.getSPPhoto())
                .into((CircleImageView) findViewById(R.id.imgview_fotoprofil));
    }
}
