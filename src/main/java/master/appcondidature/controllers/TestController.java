package master.appcondidature.controllers;

import com.lowagie.text.DocumentException;
import master.appcondidature.models.User;
import master.appcondidature.models.candidat;
import master.appcondidature.pdf.pdf1;
import master.appcondidature.repository.UserRepository;
import master.appcondidature.repository.candidatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:8080/")
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    candidatRepository candidat1;

    @Autowired
    UserRepository userRepository;

    //Dans l'acces à tous.
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    // get all candidat
    @GetMapping("/cand")
    public List getAll(){
        List candidats = new ArrayList();
        candidat1.findAll().forEach(candidats::add);

        return candidats;
    }

    // get candidat by id
    @GetMapping("/candidats/{id}")
    public ResponseEntity<?> getcandidatById(@PathVariable("id") long id){

       Optional candidatOptional = candidat1.findById(id);

        if(candidatOptional.isPresent()){

            return new ResponseEntity<>(candidatOptional.get(),HttpStatus.OK);

        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // get candidat by id ppur générer le pdf
    @GetMapping("/candidatPDF/{id}")
    public List<Object> getcandidatByIdPDF(@PathVariable("id") long id){

        List<Object> var=candidat1.getcandidatByid(id);
        return var;


    }

    // Dans l'acces à Utili et admin
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

//+++++++++++++++++++++++Partie admin+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    //get candidat By filiere
    @GetMapping("/adminfiliere")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Object> getAllfilieres() {
                List<Object> filiere=candidat1.getcandidatByfiliere();
                return filiere;
    }

    //get candidat By session
    @GetMapping("/adminsession")
    public List<Object> getSession(){
        List<Object> Session=candidat1.getcandidatBysessions1();
        return Session;
    }

    //get le nombre des candidats
     @GetMapping("/admincount")
    public Long count(){
        Long Session=candidat1.count();
        return Session;
    }

 //++++++++++++on fait l'appel de ces mapping dans src/services/admin/adminService dans les vues++++++++++++
    //get filiere Desc
     @GetMapping("/filiereDesc")
    public List<Object> getfiliereDesc(){
        List<Object> var=candidat1.orderbynoteDesc();
        return var; }

    //get filiere Asc
    @GetMapping("/filiereAsc")
    public List<Object> getfiliereAsc(){
        List<Object> var=candidat1.orderbynoteAsc();
        return var; }

    //get université Desc
    @GetMapping("/UniversiteDesc")
    public List<Object> getUnivDesc(){
        List<Object> var=candidat1.orderbynoteUnivDesc();
        return var; }

    //get université Asc
    @GetMapping("/UniversiteAsc")
    public List<Object> getUnivAsc(){
        List<Object> var=candidat1.orderbynoteUnivAsc();
        return var; }

    //get établissements Desc
    @GetMapping("/EtabDesc")
    public List<Object> getEtabDesc(){
        List<Object> var=candidat1.orderbynoteEtabDesc();
        return var; }

    //get établissements Asc
    @GetMapping("/EtabAsc")
    public List<Object> getEtabAsc(){
        List<Object> var=candidat1.orderbynoteEtabAsc();
        return var; }

    // exportation csv seulement pour spring, j'ai pas utiliser ce mapping
     @GetMapping("/adminCSV")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");

        //le nom du fichier CSV est généré en fonction de la date et de l'heure actuelles
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        //définir l'entête
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<candidat> listUsers = candidat1.findAll();

        //pour écrire des donn CSV dans la réponse, il doit être passé au CsvBeanWriter
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Candidat ID", "name", "Full Name", "filiere", "dossieer"};
        String[] nameMapping = {"idcandidat", "nom", "prenom", "filierelicence", "notedossier"};

        csvWriter.writeHeader(csvHeader);

        for (candidat user : listUsers) {
            csvWriter.write(user, nameMapping);
        }

        csvWriter.close();

    }

    // exportation Pdf seulement pour spring, j'ai pas utiliser ce mapping
    @GetMapping("/adminPDF")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        // le nom du fichier et sous format date pour que les utilis suivre facile les versions
        // des fichiers téléchargés.
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        //kanjbdo les données ou ndirohom fpdf
        List<candidat> listUsers = candidat1.findAll();

        pdf1 exporter = new pdf1(listUsers);
        exporter.export(response);

    }


}