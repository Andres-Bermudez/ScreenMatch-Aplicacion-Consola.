package com.cursospringalura.ScreenMatch.conversiondatos;

public interface IConvertirDatos {

    <T> T obtenerDatos(String json, Class<T> clase);
}
