package com.challenge.literatura.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class convierteDatos {
    private static final Logger logger = Logger.getLogger(convierteDatos.class.getName());
    private ObjectMapper objectMapper;

    public convierteDatos() {
        this.objectMapper = new ObjectMapper();
    }

    public <T> T gettingData(String jsonData, Class<T> clase) {
        if (jsonData == null || jsonData.isEmpty()) {
            logger.warning("Sin datos de entrada recibidos");
            return null;
        }

        try {
            logger.info("Iniciando deseralizacion ");
            T data = objectMapper.readValue(jsonData, clase);
            if (data == null) {
                logger.warning("datos Deserializado está vacio");
            } else {
                logger.info("Deserializado JSON exitosa");
            }
            return data;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error durante la Deserializacion", e);
            return null;
        }
    }
}
