package com.example.tele_futbol.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tele_futbol.R;
import com.example.tele_futbol.entity.League;

import java.util.List;

public class AdapterLiga extends RecyclerView.Adapter<AdapterLiga.ViewHolder>{
    private List<League> mLiga;
    private LayoutInflater mInflater;
    private Context context;

    public AdapterLiga(List<League> ligaList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mLiga = ligaList;
    }

    @Override
    public int getItemCount(){return mLiga.size();}

    @Override
    public AdapterLiga.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.irv_ligas, null);
        return new AdapterLiga.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterLiga.ViewHolder holder, final int position){
        holder.bindData(mLiga.get(position));
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setLiga(List<League> ligas){mLiga = ligas;}

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvId, tvAlt1,tvAlt2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_name);
            tvId = itemView.findViewById(R.id.tv_id);
            tvAlt1 = itemView.findViewById(R.id.tv_alt1);
            tvAlt2 = itemView.findViewById(R.id.tv_alt2);

        }

        public void bindData(final League liga) {
            //String idLiga = liga.getIdLeague();
            String[] copyList = liga.getStrLeagueAlternate().split(",");
            if(liga.getStrLeagueAlternate().trim().isEmpty()){
                tvNombre.setText(liga.getStrLeague());
                tvId.setText(liga.getIdLeague());
                tvAlt1.setText("---");
                tvAlt2.setText("---");
            }else{
                if(copyList.length==1){
                    tvNombre.setText(liga.getStrLeague());
                    tvId.setText(liga.getIdLeague());
                    tvAlt1.setText(liga.getStrLeagueAlternate());
                    tvAlt2.setText("---");
                }else{
                    tvNombre.setText(liga.getStrLeague());
                    tvId.setText(liga.getIdLeague());
                    tvAlt1.setText(liga.getStrLeagueAlternate().split(",")[0]);
                    tvAlt2.setText(liga.getStrLeagueAlternate().split(",")[1]);
                }
            }

        }
    }
}
