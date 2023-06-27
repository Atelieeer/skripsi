package id.kharisma.studio.atelier;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class holderDetailPesanan extends RecyclerView.ViewHolder{
    TextView txtServis;
    public holderDetailPesanan(@NonNull View itemView){
        super(itemView);
        txtServis = itemView.findViewById(R.id.txtServis);
    }
}

public class AdapterDetailPemesanan extends RecyclerView.Adapter<holderDetailPesanan>{
    Context context;
    ArrayList<DaftarDetail> listDetail;
    public AdapterDetailPemesanan(Context context, ArrayList<DaftarDetail> listDetail){
        this.context = context;
        this.listDetail = listDetail;
    }
    @NonNull
    @Override
    public holderDetailPesanan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.daftar_detail, parent, false);
        return new holderDetailPesanan(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holderDetailPesanan holder, int position) {
        DaftarDetail daftarDetail = listDetail.get(position);
        holder.txtServis.setText(daftarDetail.getNama());
    }

    @Override
    public int getItemCount() {
        return listDetail.size();
    }
}
