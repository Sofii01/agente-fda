package com.app.agentefda.controllers;

import com.app.agentefda.services.PdfService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {
    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }
    @PostMapping
    public ResponseEntity<?> leerPdf(@RequestParam("archivo") MultipartFile archivo) {
        try {
            String text = pdfService.extraerTextoDePdf(archivo);
            return ResponseEntity.ok().body(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
