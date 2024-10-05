package com.example.tele_futbol.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tele_futbol.R;
import com.example.tele_futbol.entity.Equipo;
import com.example.tele_futbol.entity.League;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class AdapterPosicion extends RecyclerView.Adapter<AdapterPosicion.ViewHolder>{
    private List<Equipo> mEquipo;
    private LayoutInflater mInflater;
    private Context context;

    public AdapterPosicion(List<Equipo> equipoList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mEquipo = equipoList;
    }

    @Override
    public int getItemCount(){return mEquipo.size();}

    @Override
    public AdapterPosicion.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.ivr_posiciones, null);
        return new AdapterPosicion.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterPosicion.ViewHolder holder, final int position){
        holder.bindData(mEquipo.get(position));
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setEquipo(List<Equipo> equipos){mEquipo = equipos;}

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvRank, tvState,tvGoals;
        TextInputLayout ti1,ti2,ti3,ti4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_name);
            tvRank = itemView.findViewById(R.id.tv_id);
            tvState = itemView.findViewById(R.id.tv_alt1);
            tvGoals = itemView.findViewById(R.id.tv_alt2);
            ti1=itemView.findViewById(R.id.in_1);
            ti2=itemView.findViewById(R.id.in_2);
            ti3=itemView.findViewById(R.id.in_3);
            ti4=itemView.findViewById(R.id.in_4);

        }

        public void bindData(final Equipo equipo) {
            ti1.setHint("Nombre equipo:");
            ti2.setHint("Ranking:");
            ti3.setHint("Win/Draw/Loss:");
            ti4.setHint("Goles Anotados/Concedidos/Diferencia:");

            tvNombre.setText(equipo.getStrTeam());
            tvRank.setText(equipo.getIntRank());
            tvState.setText(equipo.getIntWin()+"/"+equipo.getIntDraw()+"/"+equipo.getIntLoss());
            tvGoals.setText(equipo.getIntGoalsFor()+"/"+equipo.getIntGoalsAgainst()+"/"+equipo.getIntGoalDifference());

        }
    }
}
