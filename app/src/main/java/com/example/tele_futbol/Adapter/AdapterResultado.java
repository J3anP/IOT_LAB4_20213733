package com.example.tele_futbol.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tele_futbol.R;
import com.example.tele_futbol.entity.Equipo;
import com.example.tele_futbol.entity.Resultado;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdapterResultado extends RecyclerView.Adapter<AdapterResultado.ViewHolder>{
    private List<Resultado> mResultado;
    private LayoutInflater mInflater;
    private Context context;

    public AdapterResultado(List<Resultado> resultadoList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mResultado = resultadoList;
    }

    @Override
    public int getItemCount(){return mResultado.size();}

    @Override
    public AdapterResultado.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.ivr_resultados, null);
        return new AdapterResultado.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterResultado.ViewHolder holder, final int position){
        holder.bindData(mResultado.get(position));
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setResultado(List<Resultado> resultados){mResultado = resultados;}

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvRonda, tvLocal,tvVisitante,tvResultado,tvFecha,tvCantidad;
        ImageView imgRes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_comp);
            tvRonda  = itemView.findViewById(R.id.tv_rond);
            tvLocal = itemView.findViewById(R.id.tv_loc);
            tvVisitante  = itemView.findViewById(R.id.tv_vis);
            tvResultado = itemView.findViewById(R.id.tv_resul);
            tvFecha = itemView.findViewById(R.id.tv_fecha);
            tvCantidad = itemView.findViewById(R.id.tv_cant);
            imgRes = itemView.findViewById(R.id.imgRes);

        }

        public void bindData(final Resultado resultado) {
            
            tvNombre.setText(resultado.getStrEvent());
            tvRonda.setText(resultado.getIntRound());
            tvLocal.setText(resultado.getStrHomeTeam());
            tvVisitante.setText(resultado.getStrAwayTeam());
            tvResultado.setText("Local ( "+resultado.getIntHomeScore()+"-"+resultado.getIntAwayScore()+" ) Visitante");

            LocalDate fecha = LocalDate.parse(resultado.getDateEvent(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            tvFecha.setText(fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            //Para la foto, consulto en google y me salió esto, además de añadir una dependencia
            Picasso.get()
                    .load(resultado.getStrThumb())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imgRes);
        }
    }
}
