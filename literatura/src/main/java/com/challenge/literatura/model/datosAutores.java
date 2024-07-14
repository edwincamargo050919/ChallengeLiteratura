package com.challenge.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record datosAutores(@JsonAlias("name") String nombre,
                           @JsonAlias("birth_year") Integer fechaDeNacimiento,
                           @JsonAlias("death_year") Integer fechaDeFallecimiento) {
}
