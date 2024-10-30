package com.cursospringalura.ScreenMatch.modelos;

public enum Categoria {

    ACCION("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    CRIMEN("Crime"),
    DRAMA("Drama");

    private String categoriaOMDb;

    Categoria(String categoriaOMDb) {
        this.categoriaOMDb = categoriaOMDb;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOMDb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("\nNinguna categoria encontrada: " + text);
    }
}
