package id.tempayan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.tempayan.R;
import id.tempayan.activity.EditIdentitasActivity;
import id.tempayan.activity.surat.frag_skkb.SkkbDetail;
import id.tempayan.response.RiwayatItem;

public class AdapterRiwayat  extends  RecyclerView.Adapter<AdapterRiwayat.MyViewHolder>{

    // Buat Global variable untuk manampung context
    Context context;
    List<RiwayatItem> riwayat;
    public AdapterRiwayat(Context context, List<RiwayatItem> data_riwayat) {
        // Inisialisasi
        this.context = context;
        this.riwayat = data_riwayat;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Layout inflater
        View view = LayoutInflater.from(context).inflate(R.layout.riwayat_item, parent, false);

        // Hubungkan dengan MyViewHolder
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // Set widget
        holder.ID_pelayanan.setText(riwayat.get(position).getID_pelayanan());
        holder.nama_surat.setText(riwayat.get(position).getNama_surat());
        holder.waktu_mulai.setText(riwayat.get(position).getWaktu_mulai());
        holder.status_surat.setText(riwayat.get(position).getStatus());

        if (riwayat.get(position).getReal_status().equals("no")){
            holder.status_surat.setTextColor(ContextCompat.getColor(context, R.color.pink));
        } else if (riwayat.get(position).getReal_status().equals("yes")) {
            holder.status_surat.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else if (riwayat.get(position).getReal_status().equals("read")) {
            holder.status_surat.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }

        // Event klik ketika item list nya di klik
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (riwayat.get(position).getKode_surat_sistem().equals("skkb")){

                    Intent varIntent = new Intent(context, SkkbDetail.class);
                    varIntent.putExtra("ID_pelayanan", riwayat.get(position).getID_pelayanan());
                    varIntent.putExtra("NIK", riwayat.get(position).getNik());
                    varIntent.putExtra("NAMA_SURAT", riwayat.get(position).getNama_surat());
                    varIntent.putExtra("ID_SURAT", riwayat.get(position).getId());
                    context.startActivity(varIntent);

                } else if (riwayat.get(position).getKode_surat_sistem().equals("kpj")){

                    Intent varIntent = new Intent(context, EditIdentitasActivity.class);
                    varIntent.putExtra("ID_pelayanan", riwayat.get(position).getID_pelayanan());
                    varIntent.putExtra("NIK", riwayat.get(position).getNik());
                    varIntent.putExtra("NAMA_SURAT", riwayat.get(position).getNama_surat());
                    varIntent.putExtra("ID_SURAT", riwayat.get(position).getId());
                    context.startActivity(varIntent);

                }

            }
        });
    }

    // Menentukan Jumlah item yang tampil
    @Override
    public int getItemCount() {
        return riwayat.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Deklarasi widget

        TextView ID_pelayanan, nama_surat, waktu_mulai, status_surat;
        public MyViewHolder(View itemView) {
            super(itemView);
            // inisialisasi widget
            ID_pelayanan = (TextView) itemView.findViewById(R.id.ID_pelayanan);
            nama_surat = (TextView) itemView.findViewById(R.id.nama_surat);
            waktu_mulai = (TextView) itemView.findViewById(R.id.waktu_mulai);
            status_surat = (TextView) itemView.findViewById(R.id.status_surat);

        }
    }

}
