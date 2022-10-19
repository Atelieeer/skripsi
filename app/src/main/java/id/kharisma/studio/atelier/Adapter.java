package id.kharisma.studio.atelier;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class holder extends RecyclerView.ViewHolder {
    TextView txtServis, txtHarga;
    public holder(@NonNull View itemView) {
        super(itemView);
        txtServis = itemView.findViewById(R.id.txtServis);
        txtHarga = itemView.findViewById(R.id.txtHarga);
    }
}

public class Adapter extends RecyclerView.Adapter<holder> {
    Context context;
    ArrayList<Servis> listServis;

    public Adapter(Context context, ArrayList<Servis> listServis){
        this.context = context;
        this.listServis = listServis;
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
        holder.txtServis.setText(servis.getNama());
        holder.txtServis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailPemesanan.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listServis.size();
    }
}
