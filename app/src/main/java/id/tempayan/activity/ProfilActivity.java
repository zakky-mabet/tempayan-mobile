package id.tempayan.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import id.tempayan.R;
import id.tempayan.util.SharedPrefManager;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static id.tempayan.apihelper.UtilsApi.BASE_URL_IMAGE;

public class ProfilActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    private CircleImageView ivAvatar1;

    TextView tvResultNama;
    TextView tvResultEmail;
    SharedPrefManager sharedPrefManager;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.btnChangePhoto)
    ImageView btnChangePhoto;
    @BindView(R.id.akun)
    LinearLayout akun;
    @BindView(R.id.identitas)
    LinearLayout identitas;
    @BindView(R.id.ubahpassword)
    LinearLayout ubahpassword;

    Unbinder unbinder;

    private static final int REQUEST_CHOOSE_IMAGE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(id.tempayan.R.layout.activity_profil);

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);
        menu.setElevation(0);

        sharedPrefManager = new SharedPrefManager(this);
        tvResultEmail = (TextView) findViewById(R.id.tvResultEmail);
        tvResultEmail.setText(sharedPrefManager.getSPEmail());
        tvResultNama = (TextView) findViewById(R.id.tvResultNama);
        tvResultNama.setText(sharedPrefManager.getSPNama());

        Glide.with(getApplicationContext())
                .load(BASE_URL_IMAGE+sharedPrefManager.getSPPhoto())
                .into((CircleImageView) findViewById(R.id.ivAvatar));

        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);

        btnChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Fungsi untuk memanggil library choose image
                 */
                EasyImage.openChooserWithGallery(ProfilActivity.this, "Pilih Foto",
                        REQUEST_CHOOSE_IMAGE);
            }
        });

        akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AkunActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        identitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), IdentitasActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        ubahpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), UbahActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // method untuk menghandle ketika user sudah memilih gambar.
        // ketika gambar sudah dipilih maka gambar akan di redirect ke activity
        // library android-image-picker
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                CropImage.activity(Uri.fromFile(imageFile))
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setFixAspectRatio(true)
                        .start(ProfilActivity.this);
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                Toast.makeText(ProfilActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                super.onCanceled(source, type);
            }
        });
        // ----

        // Method ini berfungsi ketika sudah selesai dari activity android-image-picker
        // Jika result_ok maka gambar yang sudah di crop akan dimasukan kedalam imageview
        // yang kita olah menggunakan library glide.
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                Glide.with(this)
                        .load(new File(resultUri.getPath()))
                        .apply(new RequestOptions().circleCrop())
                        .into(ivAvatar);


                Log.d(TAG, "info" + data);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();

                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
