package com.example.tele_futbol.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tele_futbol.Adapter.AdapterLiga;
import com.example.tele_futbol.DTO.LeagueDTO;
import com.example.tele_futbol.R;
import com.example.tele_futbol.entity.League;
import com.example.tele_futbol.servicio.FreeSportsService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LigaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LigaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    FreeSportsService service;
    LeagueDTO ligaDTO;
    List<League> listaLiga;
    AdapterLiga adapter;
    RecyclerView recycler;
    Button btSearch;
    EditText txtPais;

    public LigaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LigaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LigaFragment newInstance(String param1, String param2) {
        LigaFragment fragment = new LigaFragment();
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
        view = inflater.inflate(R.layout.fragment_liga, container, false);

        btSearch = view.findViewById(R.id.bt_liga);
        txtPais = view.findViewById(R.id.ed_pais);

        btSearch.setOnClickListener(view1 -> {

            if(!txtPais.getText().toString().isEmpty()){
                listaLigasPais(txtPais.getText().toString());
            }else{
                listarLigas();
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

    public void listarLigas(){
        createService();
        service.listarLigas().enqueue(new Callback<LeagueDTO>() {
            @Override
            public void onResponse(Call<LeagueDTO> call, Response<LeagueDTO> response) {
                if(response.isSuccessful()){
                    listaLiga = (response.body()).getCountries();

                    adapter = new AdapterLiga(listaLiga,getContext());
                    adapter.setLiga(listaLiga);
                    recycler = view.findViewById(R.id.recyclerLiga);
                    recycler.setAdapter(adapter);
                    recycler.setLayoutManager(new LinearLayoutManager(getContext()));

                }
            }

            @Override
            public void onFailure(Call<LeagueDTO> call, Throwable throwable) {

            }
        });
    }

    public void listaLigasPais(String pais){
        createService();
        service.listarLigasPais(pais).enqueue(new Callback<LeagueDTO>() {
            @Override
            public void onResponse(Call<LeagueDTO> call, Response<LeagueDTO> response) {
                if(response.isSuccessful()){
                    listaLiga = (response.body()).getCountries();

                    if(listaLiga!=null){
                        adapter = new AdapterLiga(listaLiga,getContext());
                        adapter.setLiga(listaLiga);
                        recycler = view.findViewById(R.id.recyclerLiga);
                        recycler.setAdapter(adapter);
                        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                    }else{//Si no hay resultados para ese país
                        Toast.makeText(getContext(),"No hay resultados para su búsqueda",Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<LeagueDTO> call, Throwable throwable) {

            }
        });
    }

}