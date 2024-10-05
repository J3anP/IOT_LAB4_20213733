package com.example.tele_futbol.DTO;

import com.example.tele_futbol.entity.Resultado;

import java.io.Serializable;
import java.util.List;

public class ResultadoDTO implements Serializable {
    List<Resultado> events;

    public List<Resultado> getEvents() {
        return events;
    }

    public void setEvents(List<Resultado> events) {
        this.events = events;
    }
}
