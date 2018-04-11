package id.tempayan;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SigninActivity extends AppCompatActivity {

    TextView forgot_password,signup;
    Button signin;
    EditText email, password;
    ImageView main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signup=(TextView) findViewById(R.id.TV_signup);
        main=(ImageView) findViewById(R.id.main);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
                SigninActivity.this.startActivity(intent);
            }
        });

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                SigninActivity.this.startActivity(intent);
            }
        });


        signin=(Button) findViewById(R.id.B_signin);
        email=(EditText) findViewById(R.id.ET_email);
        password=(EditText) findViewById(R.id.ET_password);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


}
