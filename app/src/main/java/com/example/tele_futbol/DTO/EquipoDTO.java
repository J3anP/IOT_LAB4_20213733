package com.example.tele_futbol.DTO;

import com.example.tele_futbol.entity.Equipo;

import java.io.Serializable;
import java.util.List;

public class EquipoDTO implements Serializable {
    List<Equipo> table;

    public List<Equipo> getTable() {
        return table;
    }

    public void setTable(List<Equipo> table) {
        this.table = table;
    }
}
