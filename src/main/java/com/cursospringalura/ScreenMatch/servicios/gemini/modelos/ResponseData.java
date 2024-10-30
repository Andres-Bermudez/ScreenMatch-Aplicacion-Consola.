package com.cursospringalura.ScreenMatch.servicios.gemini.modelos;

import java.util.List;

// Clase principal que representa el objeto JSON
public class ResponseData {
    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;

    // Getters y Setters
    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public UsageMetadata getUsageMetadata() {
        return usageMetadata;
    }

    public void setUsageMetadata(UsageMetadata usageMetadata) {
        this.usageMetadata = usageMetadata;
    }
}
