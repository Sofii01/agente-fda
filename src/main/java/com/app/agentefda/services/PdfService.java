package com.app.agentefda.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PdfService {

    public String extraerTextoDePdf(MultipartFile archivo) throws IOException {
        try (PDDocument document = PDDocument.load(archivo.getInputStream())){
            PDFTextStripper stripper = new PDFTextStripper();
            String texto = stripper.getText(document);
            return limpiarRespuesta(texto); //limpiar texto
        }
    }

    private String limpiarRespuesta(String texto) {
        return texto.replaceAll("\\s+", " ");
    }
}
