package com.example.tele_futbol.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tele_futbol.Adapter.AdapterPosicion;
import com.example.tele_futbol.DTO.EquipoDTO;
import com.example.tele_futbol.R;
import com.example.tele_futbol.entity.Equipo;
import com.example.tele_futbol.entity.League;
import com.example.tele_futbol.servicio.FreeSportsService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PosicionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PosicionesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    FreeSportsService service;
    TextInputEditText edIdLiga;
    TextInputEditText edTemp;
    Button btPosicion;
    List<Equipo> listaEquipo;
    AdapterPosicion adapter;
    RecyclerView recycler;
    boolean idIsCorrect = true;
    boolean tempIsCorrect = true;
    boolean allCorrect = false;

    public PosicionesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PosicionesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PosicionesFragment newInstance(String param1, String param2) {
        PosicionesFragment fragment = new PosicionesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_posiciones, container, false);

        edIdLiga = view.findViewById(R.id.ed_id1);
        edTemp = view.findViewById(R.id.ed_temp);
        btPosicion = view.findViewById(R.id.bt_posicion);

        String idLiga = edIdLiga.getText().toString();
        String temporada = edTemp.getText().toString();

        btPosicion.setOnClickListener(view1 -> {

            if(idLiga.isEmpty()){
                idIsCorrect = false;
            }
            if(temporada.isEmpty()){
                tempIsCorrect = false;
            }

            if (temporada.length() == 9 && temporada.charAt(4) == '-') {
                try {
                    int firstYear = Integer.parseInt(temporada.substring(0, 4));
                    int secondYear = Integer.parseInt(temporada.substring(5));

                    if (firstYear + 1 == secondYear) {
                        allCorrect = true;
                    }
                } catch (NumberFormatException e) {
                    allCorrect = false;
                }
            }

            //listarPosicionesParam(idLiga,temporada);

            //Comente estos if
            if (!idIsCorrect || !tempIsCorrect || !allCorrect) {
                StringBuilder mensaje = new StringBuilder("Debe completar los campos");

                if (idIsCorrect && tempIsCorrect && !allCorrect) {
                    mensaje = new StringBuilder("Ingrese bien el formato");
                } else if (!idIsCorrect && tempIsCorrect && !allCorrect) {
                    mensaje.append(" e ingresar bien el formato");
                }

                Toast.makeText(getContext(), mensaje.toString(), Toast.LENGTH_LONG).show();
            } else {
                listarPosicionesParam(idLiga, temporada);
            }

        });

        return view;
    }

    //Me sale unreacheable si no lo pongo como función separada
    public void createService(){
        service = new Retrofit.Builder()
                .baseUrl("https://www.thesportsdb.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FreeSportsService.class);
    }

    public void listarPosicionesParam(String id, String temp){
        createService();
        service.listaPosiciones(id,temp).enqueue(new Callback<EquipoDTO>() {
            @Override
            public void onResponse(Call<EquipoDTO> call, Response<EquipoDTO> response) {
                if(response.isSuccessful()){
                    if(response.toString().isEmpty()||response.body().getTable() == null){
                        Toast.makeText(getContext(),"No hay resultados para su búsqueda",Toast.LENGTH_LONG).show();
                    }else{
                        listaEquipo = (response.body()).getTable();
                        adapter = new AdapterPosicion(listaEquipo,getContext() );
                        adapter.setEquipo(listaEquipo);
                        recycler = view.findViewById(R.id.recyclerPosiciones);
                        recycler.setAdapter(adapter);
                        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                }else{
                    Log.d("bug","Pipipipipi");
                }
            }
            //Ya no sé que está mal :c, no llega al mensaje de error, pero tmp muestra :'
            @Override
            public void onFailure(Call<EquipoDTO> call, Throwable throwable) {

            }
        });
    }

}