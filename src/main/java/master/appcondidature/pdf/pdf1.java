package master.appcondidature.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import master.appcondidature.models.candidat;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class pdf1 {
    private List<candidat> candidatList;

    public pdf1(List<candidat> candidatList) {
        this.candidatList = candidatList;
    }
    private void writeTableHeader(PdfPTable table) {
        //entête du tableau
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Id", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Nom", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Prénom", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("CIN ou numéro de passeport", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nationalité", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date de naissance", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Adresse", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Téléphone", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);
    }
    private void writeTableData(PdfPTable table) {
        for (candidat candidat1 : candidatList) {
            table.addCell(String.valueOf(candidat1.getIdcandidat()));
            table.addCell(candidat1.getNom());
            table.addCell(candidat1.getPrenom());
            table.addCell(candidat1.getCin());
            table.addCell(candidat1.getNationalite());
            table.addCell(candidat1.getDatedenaissance());
            table.addCell(candidat1.getAdresse());
            table.addCell(candidat1.getTelephone());

        }
    }
     //export() écrire le contenu du fichier PDF dans le flux de sortie de la réponse,
     // de sorte que les clients pourront télécharger le document PDF exporté .
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of Candidats", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1f, 1f, 1f, 1f, 1f,1f,1f,1f,1f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }

}
