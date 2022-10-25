package id.kharisma.studio.atelier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DetailPemesanan extends AppCompatActivity {
    Intent intent;
    Button btnPilihLokasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemesanan);
        btnPilihLokasi = findViewById(R.id.btnPilihLokasi);
        btnPilihLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DetailPemesanan.this, GoogleMap.class);
                startActivity(intent);
            }
        });

    }
}