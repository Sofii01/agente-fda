package com.app.agentefda.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PdfService {

    public List<String> extraerTextoDePdf(MultipartFile archivo) throws IOException {
        try (PDDocument document = PDDocument.load(archivo.getInputStream())){
            PDFTextStripper stripper = new PDFTextStripper();
            String texto = stripper.getText(document);
            String cleanText = limpiarRespuesta(texto); //limpiar texto
            return extractUserStories(cleanText);
        }
    }

    private String limpiarRespuesta(String texto) {
        return texto.replaceAll("\\s+", " ");
    }

    public List<String> extractUserStories(String fullText) {
        List<String> stories = new ArrayList<>();

        // 1. Tomar solo desde "Listado de User Stories:" en adelante
        int inicio = fullText.indexOf("Listado de User Stories:");
        if (inicio == -1) {
            return stories; // No se encontró el marcador, retornar vacío
        }

        String soloHistorias = fullText.substring(inicio);

        // 2. Patrón: número + punto + texto que empieza con "Como ..." y termina con número siguiente o fin
        Pattern pattern = Pattern.compile("(\\d{1,2})\\.\\s+(Como.*?)(?=\\d{1,2}\\.\\s+Como|$)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(soloHistorias);

        while (matcher.find()) {
            String historia = matcher.group(2)
                    .replaceAll("\\s+", " ")   // limpiar múltiples espacios
                    .replaceAll("PROYECTO.*", "") // cortar si aparece "PROYECTO DOPARTI"
                    .trim();

            stories.add(historia);

            if (stories.size() == 30) break; // cortar después de 30
        }

        return stories;
    }
}
