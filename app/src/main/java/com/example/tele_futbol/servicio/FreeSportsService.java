package com.example.tele_futbol.servicio;

import com.example.tele_futbol.DTO.EquipoDTO;
import com.example.tele_futbol.DTO.LeagueDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FreeSportsService {

    //Para listar las ligas
    @GET("/api/v1/json/3/all_leagues.php")
    Call<LeagueDTO> listarLigas();

    @GET("/api/v1/json/3/search_all_leagues.php")
    Call<LeagueDTO> listarLigasPais(@Query("c") String country);

    //Para listar posiciones
    @GET("/api/v1/json/3/lookuptable.php")
    Call<EquipoDTO> listaPosiciones(@Query("l") String id,
                                  @Query("s") String temporada);

    //Para listar resultados

}
