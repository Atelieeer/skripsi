package id.kharisma.studio.atelier;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class holder extends RecyclerView.ViewHolder {
    TextView txtServis, txtHarga;
    CardView cardServis;
    ArrayList data;
    public holder(@NonNull View itemView) {
        super(itemView);
        txtServis = itemView.findViewById(R.id.txtServis);
        cardServis = itemView.findViewById(R.id.cardServis);
        txtHarga = itemView.findViewById(R.id.txtHarga);
    }
}

public class AdapterServisScreen extends RecyclerView.Adapter<holder> {
    Context context;
    ArrayList<Servis> listServis;
    ArrayList data;
    Double total;

    boolean cekSama(String s){
        boolean sama = false;
        if (data.size() >= 1){
            for(int i=0; i<data.size(); i++) {
                if (data.get(i).equals(s)){
                    sama = true;
                }
            }
            if (!sama){
                data.add(s);
            }
        } else {
            data.add(s);
        }
        return (sama);
    }

    public AdapterServisScreen(Context context, ArrayList<Servis> listServis, ArrayList data, Double total){
        this.context = context;
        this.listServis = listServis;
        this.data = data;
        this.total = total;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.daftarservis, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        Servis servis = listServis.get(position);
        holder.txtHarga.setText(servis.getHarga());
        String estimasiHarga = (String) holder.txtHarga.getText();
        String[] kata = estimasiHarga.split(" ");
        String harga = kata[3];
        holder.txtServis.setText(servis.getNama());
        holder.cardServis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = (String) holder.txtServis.getText();
                boolean cekSama = cekSama(s);
                Log.d("sama", String.valueOf(cekSama));
                Double totalHarga = Double.parseDouble(harga);

                if (cekSama==false){
                    if (total != null){
                        total = total + totalHarga;
                    } else {
                        total = Double.parseDouble(harga);
                    }
                } else {
                    total = total;
                }
                Intent intent = new Intent(context, DetailPemesanan.class);
                intent.putExtra("pilih", data);
                intent.putExtra("harga", total);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listServis.size();
    }
}
