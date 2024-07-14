package com.challenge.literatura.model;


import static java.awt.SystemColor.text;

public enum lenguajes {
    ES("es"),
    EN("en"),
    FR("fr"),
    IT("it"),
    PT("pt");

    private String idioma;
    lenguajes  (String idioma) {
        this.idioma = idioma;    }
    public static lenguajes fromString(String text) {
        for (lenguajes lenguaje : lenguajes.values()) {
            if (lenguaje.idioma.equalsIgnoreCase(text)) {
                return lenguaje;
            }
        }
        throw new IllegalArgumentException("Idioma no encontrado: "+text);
    }

    public String getIdioma() {
        return idioma;
    }
}
