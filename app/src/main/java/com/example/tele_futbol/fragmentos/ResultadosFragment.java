package com.example.tele_futbol.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tele_futbol.Adapter.AdapterPosicion;
import com.example.tele_futbol.Adapter.AdapterResultado;
import com.example.tele_futbol.DTO.EquipoDTO;
import com.example.tele_futbol.DTO.ResultadoDTO;
import com.example.tele_futbol.R;
import com.example.tele_futbol.entity.Resultado;
import com.example.tele_futbol.servicio.FreeSportsService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultadosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultadosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    FreeSportsService service;
    AdapterResultado adapter;
    List<Resultado> listaResultado;
    RecyclerView recycler;
    MaterialButton btRes;
    private TextInputEditText edIdLiga;
    private TextInputEditText edTemp;
    private TextInputEditText edRonda;
    boolean idIsCorrect = true;
    boolean tempIsCorrect = true;
    boolean rondaIsCorrect = true;
    boolean allCorrect = false;
    String busquedaAnterior="";

    public ResultadosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultadosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultadosFragment newInstance(String param1, String param2) {
        ResultadosFragment fragment = new ResultadosFragment();
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
        view = inflater.inflate(R.layout.fragment_resultados, container, false);

        // Gestión del adapter:
        listaResultado = new ArrayList<>();
        adapter = new AdapterResultado(listaResultado,getContext() );
        adapter.setResultado(listaResultado);
        recycler = view.findViewById(R.id.recyclerResultados);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        btRes = view.findViewById(R.id.bt_res);
        edIdLiga = view.findViewById(R.id.ed_id2);
        edTemp = view.findViewById(R.id.ed_seas);
        edRonda = view.findViewById(R.id.ed_ronda);

        String idLiga = edIdLiga.getText().toString();
        String season = edTemp.getText().toString();
        String ronda = edRonda.getText().toString();

        btRes.setOnClickListener(l -> {

            if(!busquedaAnterior.equals(idLiga+season+ronda)) {

                if (idLiga.isEmpty()) {
                    idIsCorrect = false;
                }
                if (season.isEmpty()) {
                    tempIsCorrect = false;
                }

                if (ronda.isEmpty()) {
                    rondaIsCorrect = false;
                }


                if (season.length() == 9 && season.charAt(4) == '-') {
                    try {
                        int firstYear = Integer.parseInt(season.substring(0, 4));
                        int secondYear = Integer.parseInt(season.substring(5));

                        if (firstYear + 1 == secondYear) {
                            allCorrect = true;
                        }
                    } catch (NumberFormatException e) {
                        allCorrect = false;
                    }
                }

                List<String> mensaje = new ArrayList<>();

                if (!idIsCorrect) mensaje.add("El idLiga no puede estar vacío");
                if (!tempIsCorrect) mensaje.add("La temporada no puede estar vacía");
                if (!rondaIsCorrect) mensaje.add("La ronda no puede estar vacía");
                if (!allCorrect && tempIsCorrect) mensaje.add("La temporada debe tener el formato 20XX-20XY");

                busquedaAnterior = idLiga + season + ronda;

                if (mensaje.size() > 0) {
                    Toast.makeText(getContext(), String.join("! ", mensaje), Toast.LENGTH_LONG).show();
                } else {
                    listarResultadosParam(idLiga, season, ronda);
                }
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

    public void listarResultadosParam(String id, String season,String ronda){
        createService();
        service.listaResultados(id,season,ronda).enqueue(new Callback<ResultadoDTO>() {
            @Override
            public void onResponse(Call<ResultadoDTO> call, Response<ResultadoDTO> response) {
                if(response.isSuccessful()){
                    if(response.toString().isEmpty()||response.body().getEvents() == null){
                        Toast.makeText(getContext(),"No hay resultados para su búsqueda",Toast.LENGTH_LONG).show();
                    }else{
                        listaResultado = (response.body()).getEvents();
                        adapter = new AdapterResultado(listaResultado,getContext() );
                        adapter.setResultado(listaResultado);
                        recycler = view.findViewById(R.id.recyclerResultados);
                        recycler.setAdapter(adapter);
                        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                }else{
                    Log.d("bug","Pipipipipi");
                }
            }
            @Override
            public void onFailure(Call<ResultadoDTO> call, Throwable throwable) {

            }
        });
    }
}