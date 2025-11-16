package co.edu.uniquindio.proyectofinalprog2code.Controller;

import co.edu.uniquindio.proyectofinalprog2code.dto.ReporteDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Generador de reportes en CSV y PDF (RF-039)
 */
public class GeneradorReportes {

    public void generarReporteCSV(List<ReporteDTO> datos, String rutaArchivo) throws IOException {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(rutaArchivo, "UTF-8")) {
            // Escribir encabezados
            writer.println("Tipo,ID,Fecha,Estado,Monto");

            // Escribir los datos
            for (ReporteDTO dto : datos) {
                writer.printf("%s,%s,%s,%s,%.2f\n",
                        sanitize(dto.getTipo()),
                        sanitize(dto.getId()),
                        dto.getFecha() != null ? dto.getFecha().toString() : "",
                        sanitize(dto.getEstado()),
                        dto.getMonto()
                );
            }
        }
    }

    public void generarReportePDF(List<ReporteDTO> datos, String rutaArchivo) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Encabezado
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.newLineAtOffset(50, 750);
        contentStream.showText("REPORTE DE ENVÍOS");
        contentStream.endText();

        // Fecha
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        contentStream.newLineAtOffset(50, 730);
        contentStream.showText("Fecha de generación: " + LocalDateTime.now().toString());
        contentStream.endText();

        // Encabezados de columnas
        int yPos = 700;
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        contentStream.newLineAtOffset(50, yPos);
        contentStream.showText("Tipo");
        contentStream.newLineAtOffset(100, 0);
        contentStream.showText("ID");
        contentStream.newLineAtOffset(150, 0);
        contentStream.showText("Estado");
        contentStream.newLineAtOffset(100, 0);
        contentStream.showText("Monto");
        contentStream.endText();

        // Datos
        yPos = 680;
        for (ReporteDTO dto : datos) {
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 9);
            contentStream.newLineAtOffset(50, yPos);
            contentStream.showText(dto.getTipo());
            contentStream.newLineAtOffset(100, 0);
            contentStream.showText(dto.getId());
            contentStream.newLineAtOffset(150, 0);
            contentStream.showText(dto.getEstado());
            contentStream.newLineAtOffset(100, 0);
            contentStream.showText(String.valueOf(dto.getMonto()));
            contentStream.endText();
            yPos -= 15;
        }

        contentStream.close();
        document.save(rutaArchivo);
        document.close();
    }

    public String generarRutaReporte(String tipoArchivo) {
        String timestamp = LocalDateTime.now().toString().replace(":", "-").substring(0, 19);
        String directorio = System.getProperty("user.home") + File.separator + "PlataformaLogistica" + File.separator + "Reportes";

        File dir = new File(directorio);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return directorio + File.separator + "reporte_" + timestamp + "." + tipoArchivo;
    }

    /**
     * Devuelve el texto escapado en CSV (sin comas ni saltos de línea).
     */
    private String sanitize(String s) {
        if (s == null) return "";
        String t = s.replace("\n", " ").replace("\r", " ");
        if (t.contains(",") || t.contains("\"") || t.contains("\n")) {
            t = '"' + t.replace("\"", "\"\"") + '"';
        }
        return t;
    }
}