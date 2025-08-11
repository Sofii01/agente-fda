package com.app.agentefda.services;

import dev.langchain4j.model.ollama.OllamaLanguageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IAService {
    private final OllamaLanguageModel model = OllamaLanguageModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("llama3")
            .timeout(Duration.ofSeconds(180))
            .build();

    

    public String analizarTexto(List<String> texto) {
        String prompt = buildPrompt(texto);

        System.out.println("Prompt generado:");
        System.out.println(prompt); // para debug

        String respuesta = String.valueOf(model.generate(prompt)); // `model` es tu instancia de OllamaLanguageModel
        return respuesta;
    }

    private String buildPrompt(List<String> historias) {
        StringBuilder sb = new StringBuilder();

        sb.append("Actuá como un analista funcional experto.\n");
        sb.append("Dado el siguiente texto de un proyecto redactado como historias de usuario,\n");
        sb.append("identificá y listá todas las funcionalidades del sistema.\n\n");
        sb.append("Para cada funcionalidad detectada, devolvé lo siguiente en formato JSON:\n");
        sb.append("- descripcion: breve resumen de la funcionalidad\n");
        sb.append("- tipo: EI (Entrada Externa), EO (Salida Externa), EQ (Consulta Externa), ILF (Archivo Lógico Interno), EIF (Archivo de Interfaz Externa)\n");
        sb.append("- complejidad: baja, media o alta (si se puede estimar)\n\n");
        sb.append("Respondé en formato JSON (array de objetos). No expliques nada fuera del JSON.\n\n");

        sb.append("Texto del proyecto:\n");

        for (int i = 0; i < historias.size(); i++) {
            sb.append((i + 1)).append(". ").append(historias.get(i)).append("\n");
        }

        return sb.toString();
    }
}
