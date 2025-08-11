package com.app.agentefda.controllers;

import com.app.agentefda.services.IAService;
import com.app.agentefda.services.PdfService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {
    private final PdfService pdfService;
    private final IAService iaService;
    public PdfController(PdfService pdfService, IAService iaService) {
        this.pdfService = pdfService;
        this.iaService = iaService;
    }
    @PostMapping
    public ResponseEntity<?> leerPdf(@RequestParam("archivo") MultipartFile archivo) {
        try {
            List<String> text = pdfService.extraerTextoDePdf(archivo);

            String response = iaService.analizarTexto(text);
            return ResponseEntity.ok().body(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
