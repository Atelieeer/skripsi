package id.kharisma.studio.atelier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class ServisScreen extends AppCompatActivity {
    Intent intent;
    ImageView backArrow;
    RecyclerView rServis;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutmanager;
    ArrayList<Servis> listServis;

    void dataDummy() {
        listServis = new ArrayList<>();
        listServis.add(new Servis("Ganti Oli", "Estimasi Harga Rp.5000"));
        listServis.add(new Servis("Ganti Ban", "Estimasi Harga Rp.50.000"));
        listServis.add(new Servis("Tambal Ban", "Estimasi Harga Rp.15.000"));
    }

    void data() {
        rServis = findViewById(R.id.reServis);
        adapter = new Adapter(this, listServis);
        layoutmanager = new LinearLayoutManager(this);
        rServis.setLayoutManager(layoutmanager);
        rServis.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servis_screen);
        dataDummy();data();
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
//                backArrow.setVisibility(View.GONE);
                intent = new Intent(ServisScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}