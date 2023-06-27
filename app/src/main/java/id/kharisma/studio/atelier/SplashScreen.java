package id.kharisma.studio.atelier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
        } else {
            intent = new Intent(SplashScreen.this, Login.class);
            startActivity(intent);
        }
    }
}