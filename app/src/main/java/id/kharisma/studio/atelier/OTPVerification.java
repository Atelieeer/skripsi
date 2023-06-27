package id.kharisma.studio.atelier;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OTPVerification extends AppCompatActivity {
    private DatabaseReference mDatabase;
    String nama, noHp;
//    int jumlahData;
//    void simpanUser(String userId, String nama, String noHp) {
//        User user = new User(nama, noHp);
//        mDatabase.child("users").child(userId).setValue(user);
//    }
//    void getDataSize(){
//
//        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.getValue() != null){
//                    jumlahData = (int) snapshot.child("users").getChildrenCount();
//                } else {
//                    jumlahData = 0;
//                }
//
//                simpanUser(String.valueOf(jumlahData+1), nama, noHp);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0){
                if (selectedETPossiton == 0){
                    selectedETPossiton = 1;
                    showKeyboard(txtOtp2);
                } else if (selectedETPossiton == 1){
                    selectedETPossiton = 2;
                    showKeyboard(txtOtp3);
                } else if (selectedETPossiton == 2){
                    selectedETPossiton = 3;
                    showKeyboard(txtOtp4);
                } else if (selectedETPossiton == 3){
                    selectedETPossiton = 4;
                    showKeyboard(txtOtp5);
                } else if (selectedETPossiton == 4){
                    selectedETPossiton = 5;
                    showKeyboard(txtOtp6);
                }
            }
        }
    };
    private EditText txtOtp1, txtOtp2, txtOtp3, txtOtp4, txtOtp5, txtOtp6;
    private TextView resendBtn;
    private Boolean resendEnabled = false;
    private int resendTime = 60;
    private int selectedETPossiton = 0;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_otpverification);

        txtOtp1 = findViewById(R.id.txtOTP1);
        txtOtp2 = findViewById(R.id.txtOTP2);
        txtOtp3 = findViewById(R.id.txtOTP3);
        txtOtp4 = findViewById(R.id.txtOTP4);
        txtOtp5 = findViewById(R.id.txtOTP5);
        txtOtp6 = findViewById(R.id.txtOTP6);

        resendBtn = findViewById(R.id.resendBtn);
        final AppCompatButton btnVerify = findViewById(R.id.btnVerify);
        final TextView otpMobile = findViewById(R.id.otpMobile);

        final String getMobile = getIntent().getStringExtra("mobile");
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        otpMobile.setText("+62"+getMobile);

        txtOtp1.addTextChangedListener(textWatcher);
        txtOtp2.addTextChangedListener(textWatcher);
        txtOtp3.addTextChangedListener(textWatcher);
        txtOtp4.addTextChangedListener(textWatcher);
        txtOtp5.addTextChangedListener(textWatcher);
        txtOtp6.addTextChangedListener(textWatcher);

        showKeyboard(txtOtp1);
        startCountDownTimer();
        verificationId = getIntent().getStringExtra("verificationId");
        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resendEnabled){
                    startCountDownTimer();
                }
            }
        });
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtOtp1.getText().toString().trim().isEmpty()
                || txtOtp2.getText().toString().trim().isEmpty()
                || txtOtp3.getText().toString().trim().isEmpty()
                || txtOtp4.getText().toString().trim().isEmpty()
                || txtOtp5.getText().toString().trim().isEmpty()
                || txtOtp6.getText().toString().trim().isEmpty()){
                    Toast.makeText(OTPVerification.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = txtOtp1.getText().toString()+txtOtp2.getText().toString()+txtOtp3.getText().toString()+txtOtp4.getText().toString()
                                +txtOtp5.getText().toString() + txtOtp6.getText().toString();
                if(verificationId != null){
                    progressBar.setVisibility(View.VISIBLE);
                    btnVerify.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId, code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            btnVerify.setVisibility(View.VISIBLE);

                            if(task.isSuccessful()){
//                                MemoryData.saveData(String.valueOf(otpMobile.getText()),
//                                        OTPVerification.this);
                                nama = MemoryData.getName(OTPVerification.this);
                                noHp = MemoryData.getData(OTPVerification.this);
                                Log.d("nama", nama);
//                                getDataSize();
                                Intent intent;
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(OTPVerification.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    private void showKeyboard(EditText otpET) {
        otpET.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET, inputMethodManager.SHOW_IMPLICIT);
    }
    private void startCountDownTimer() {
        resendEnabled = false;
        resendBtn.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(resendTime * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                resendBtn.setText("Kirim Ulang Kode (" + (millisUntilFinished / 1000)+")");
            }

            @Override
            public void onFinish() {
                resendEnabled = true;
                resendBtn.setText("Kirim Ulang Kode");
                resendBtn.setTextColor(Color.parseColor("#1286FA"));
            }
        }.start();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_DEL){
            if (selectedETPossiton == 5){
                selectedETPossiton = 4;
                showKeyboard(txtOtp5);
            }else if(selectedETPossiton == 4){
                selectedETPossiton = 3;
                showKeyboard(txtOtp4);
            } else if(selectedETPossiton == 3){
                selectedETPossiton = 2;
                showKeyboard(txtOtp3);
            } else if(selectedETPossiton == 2){
                selectedETPossiton = 1;
                showKeyboard(txtOtp2);
            } else if(selectedETPossiton == 1){
                selectedETPossiton = 0;
                showKeyboard(txtOtp1);
            }
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }
}