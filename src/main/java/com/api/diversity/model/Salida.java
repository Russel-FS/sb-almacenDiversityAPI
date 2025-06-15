package com.api.diversity.model;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Salida {
    private Integer idSalida;
    private Timestamp fechaSalida;
    private String motivoSalida;
}