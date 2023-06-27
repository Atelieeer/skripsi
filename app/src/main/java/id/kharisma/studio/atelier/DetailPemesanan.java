package id.kharisma.studio.atelier;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailPemesanan extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{
    RecyclerView rDaftarDetail;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<DaftarDetail> listDetail;
    Intent intent;
    TextView btnPilihLokasi, btnAddMore, btnPesan, txtLatitude, txtLongitude;
    ImageView btnKembali;
    TextView txtHarga;
    String harga;
    ArrayList pilih;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pesanan");

    void Data() {
        rDaftarDetail = findViewById(R.id.reDaftarDetail);
        adapter = new AdapterDetailPemesanan(this, listDetail);
        layoutManager = new LinearLayoutManager(this);
        rDaftarDetail.setLayoutManager(layoutManager);
        rDaftarDetail.setAdapter(adapter);
        Log.d("listdetail", String.valueOf(listDetail.get(0)));
    }
    void DataDummy(){
        listDetail = new ArrayList<>();
        for(int i=0; i<pilih.size(); i++){
            listDetail.add(new DaftarDetail((String) pilih.get(i)));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                String lat = data.getStringExtra("latitude");
                String lng = data.getStringExtra("longitude");
                btnPilihLokasi.setVisibility(View.GONE);
                txtLatitude.setText(lat);
                txtLongitude.setText(lng);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_detailpesanan);

        btnKembali = findViewById(R.id.btnKembali);
        btnPilihLokasi = findViewById(R.id.btnPilihLokasi);
        btnAddMore = findViewById(R.id.btnAddMore);
        txtHarga = findViewById(R.id.txtHarga);
        btnPesan = findViewById(R.id.btnPesan);
        txtLatitude = findViewById(R.id.txtLatitude);
        txtLongitude = findViewById(R.id.txtLongitude);

        Bundle extras = getIntent().getExtras();

        pilih = new ArrayList();
        pilih = extras.getStringArrayList("pilih");
        harga = String.valueOf(extras.getDouble("harga"));

        txtHarga.setText(harga);
        DataDummy();Data();



        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DetailPemesanan.this, ServisScreen.class);
                startActivity(intent);
            }
        });
        btnPilihLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DetailPemesanan.this, GoogleMap.class);
                startActivityForResult(intent, 1);
            }
        });
        btnAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DetailPemesanan.this, ServisScreen.class);
                intent.putExtra("detail", pilih);
                intent.putExtra("totalHarga", Double.parseDouble(harga));
                startActivity(intent);
            }
        });
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int getJumlahPesanan = (int) snapshot.getChildrenCount();
                        databaseReference.child(String.valueOf(getJumlahPesanan + 1)).child("location").child(("latitude")).setValue(txtLatitude.getText());
                        databaseReference.child(String.valueOf(getJumlahPesanan + 1)).child("location").child("longitude").setValue((txtLongitude.getText()));
                        if (getJumlahPesanan > 0){
                            for (int i = 0; i < pilih.size(); i++){
                                Pesanan pesanan = new Pesanan((String) pilih.get(i), harga);
                                databaseReference.child(String.valueOf(getJumlahPesanan + 1)).child(String.valueOf(i + 1)).setValue(pesanan);
                            }

                        } else {
                            for (int i = 0; i < pilih.size(); i++){
                                Pesanan pesanan = new Pesanan((String) pilih.get(i), harga);
                                databaseReference.child("1").child(String.valueOf(i + 1)).setValue(pesanan);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Intent intent = new Intent(DetailPemesanan.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}