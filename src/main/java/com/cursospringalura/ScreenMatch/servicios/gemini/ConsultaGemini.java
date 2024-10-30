package com.cursospringalura.ScreenMatch.servicios.gemini;

import com.cursospringalura.ScreenMatch.autenticacion.DatosAutenticacion;
import com.cursospringalura.ScreenMatch.servicios.gemini.modelos.ResponseData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import java.io.IOException;

public class ConsultaGemini extends DatosAutenticacion {

    public ResponseData enviarSolicitud(String prompt) {

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType,
                "{\"contents\":[{\"parts\":[{\"text\":\"" + prompt + "\"}]}]}");

        Request request = new Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:" +
                        "generateContent?key=" + getApiKeyGemini())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();

        } catch (IOException e) {
            System.out.println("\nNo se pudo hacer la solicitud a la API de Gemini.");
            System.out.println(e.getMessage());
        }
        ResponseData respuesta = null;

        assert response != null;
        if (response.isSuccessful()) {
            try {
                assert response.body() != null;
                String json = response.body().string();

                // Procedimiento para convertir el JSON en un objeto Java
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                respuesta = gson.fromJson(json, ResponseData.class);

            } catch (IOException e) {
                System.out.println("\nLa respuesta de Gemini no pudo ser convertida en un objeto Java.");
            }
        }
        return respuesta;
    }
}
