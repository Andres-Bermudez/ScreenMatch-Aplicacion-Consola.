package com.cursospringalura.ScreenMatch.autenticacion;

public class DatosAutenticacion {
    private String apiKeyOMDb = "TU_API_KEY_OMDB";
    private String apiKeyGemini = "TU_API_KEY_GEMINI";

    public String getApiKey() {
        return apiKeyOMDb;
    }

    public String getApiKeyGemini() {
        return apiKeyGemini;
    }
}
