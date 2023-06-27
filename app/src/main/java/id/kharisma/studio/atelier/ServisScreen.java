package id.kharisma.studio.atelier;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServisScreen extends AppCompatActivity {
    RecyclerView rServis;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutmanager;
    ArrayList<Servis> listServis;
    ArrayList dataServis;
    private DatabaseReference databaseServis;
    ImageView btnKembali;
    Intent intent;
    Double totalHarga;


    void data() {
        rServis = findViewById(R.id.reServis);
        adapter = new AdapterServisScreen(this, listServis, dataServis, totalHarga);
        layoutmanager = new LinearLayoutManager(this);
        rServis.setLayoutManager(layoutmanager);
        rServis.setAdapter(adapter);
    }
    void getDatabaseServis(){
        databaseServis.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot servisSnapshot : dataSnapshot.getChildren()){
                    Log.d("lihat database", String.valueOf(servisSnapshot.getValue()));
                    Servis servis = servisSnapshot.getValue(Servis.class);
                    listServis.add(servis);

                }
                data();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servis_screen);
        btnKembali = findViewById(R.id.btnKembaliHome);
        databaseServis = FirebaseDatabase.getInstance().getReference("daftar_servis");
//        dataDummy();
        listServis = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        dataServis = new ArrayList();
        if (extras != null){
            dataServis = extras.getStringArrayList("detail");
            totalHarga = extras.getDouble("totalHarga");
        }
        getDatabaseServis();
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ServisScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}