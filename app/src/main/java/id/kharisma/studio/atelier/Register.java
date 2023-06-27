package id.kharisma.studio.atelier;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private int jumlahData;
    private String nama;
    private String noHp;

    void simpanUser(String userId, String nama, String noHp) {
        User user = new User(nama, noHp);
        mDatabase.child("users").child(userId).setValue(user);
    }
    void getDataSize(){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null){
                    jumlahData = (int) snapshot.child("users").getChildrenCount();
                } else {
                    jumlahData = 0;
                }

                simpanUser(String.valueOf(jumlahData+1), nama, noHp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final EditText txtNamaLengkap = findViewById(R.id.txtRegisterNamaLengkap);
        final EditText mobile = findViewById(R.id.txtRegisterNoHp);
        final TextView btnSignIn = findViewById(R.id.btnRegisterSignIn);
        final ProgressBar progressBar = findViewById(R.id.progressBarSignUp);
        final AppCompatButton signUpButton = findViewById(R.id.btnRegisterSignUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = String.valueOf(txtNamaLengkap.getText());
                noHp = String.valueOf(mobile.getText());
                MemoryData.saveName(nama, Register.this);
                MemoryData.saveData(noHp, Register.this);
                getDataSize();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+62"+ mobile.getText().toString(),
                        60, TimeUnit.SECONDS, Register.this,
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
                                Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}