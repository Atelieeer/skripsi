package id.kharisma.studio.atelier;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText mobile = findViewById(R.id.txtNoHp);
        final TextView btnSignUp = findViewById(R.id.btnSignUp);
        final AppCompatButton btnSignIn = findViewById(R.id.btnSignIn);
        final ProgressBar progressBar = findViewById(R.id.progressBarSignIn);

        DatabaseReference cekDataUser = FirebaseDatabase.getInstance().getReference("users");
        FirebaseAuth mAuth;
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobile.getText().toString().trim().isEmpty()){
                    Toast.makeText(Login.this, "Enter Mobile", Toast.LENGTH_SHORT).show();
                    return;
                }
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+62"+ mobile.getText().toString(),
                        60, TimeUnit.SECONDS, Login.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                btnSignIn.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                btnSignIn.setVisibility(View.VISIBLE);
                                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.VISIBLE);
                                btnSignIn.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(getApplicationContext(), OTPVerification.class);
                                intent.putExtra("mobile", mobile.getText().toString());
                                intent.putExtra("verificationId", verificationId);
                                startActivity(intent);
                            }
                        }
                );
            }
        });
    }
}