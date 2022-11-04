package master.appcondidature.controllers;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import master.appcondidature.models.User;
import master.appcondidature.security.Untity;
import master.appcondidature.security.services.Signup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import master.appcondidature.models.candidat;
import master.appcondidature.payload.request.LoginRequest;
import master.appcondidature.payload.request.SignupRequest;
import master.appcondidature.payload.response.JwtResponse;
import master.appcondidature.payload.response.MessageResponse;
import master.appcondidature.repository.RoleRepository;

import master.appcondidature.repository.UserRepository;
import master.appcondidature.repository.candidatRepository;
import master.appcondidature.security.jwt.JwtUtils;
import master.appcondidature.security.services.UserDetailsImpl;
import org.springframework.web.servlet.ModelAndView;


@CrossOrigin("http://localhost:8080/")
@RestController
@RequestMapping("/api/auth")
public class AuthController {




    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    candidatRepository candidRepository;


    @Autowired
    UserRepository userRepository;

    @Autowired
    Signup signup;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    // vérification d'email
    @GetMapping("/verify")
    public ModelAndView verifyAccount(@Param("code") String code) {
        boolean verified = signup.verify(code);
        ModelAndView modelAndView = new ModelAndView();
        if(verified == true)
        {
            modelAndView.setViewName("verify_success.html");
            return modelAndView;
        }
       //String pageTitle = verified ? "verification Succeeded!" : "verification Failed";
       // model.addAttribute("pageTitle", pageTitle);
        else {
            modelAndView.setViewName("verify_fail.html");
            return modelAndView;
        }
    }

    // Se connecter
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
                return ResponseEntity.ok(new JwtResponse(jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }

    // inscription au compte
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest,HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        //usernme déja existe
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        //email déja existe
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

             signup.registre(signUpRequest);
             String siteURL= Untity.getSiteURL(request);
             signup.sendVerificationEmail(signUpRequest,siteURL);

            return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès \n Veuillez consulter votre e-mail pour vérifier votre compte."));
    }

    // inscription au master
    @PutMapping("/candidature/{id}")
    public ResponseEntity<?> candidat(@Valid @RequestBody candidat candidat2 ,
                                             @PathVariable("id") Long id) {


        //Cin déja existe
        if (candidRepository.existsByCin(candidat2.getCin())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Cin exist déja"));
        }

        Optional<User> user1= userRepository.findById(id);
        Optional<candidat> candidat = Optional.of(candidRepository.findByUser(id));

        if (candidat.isPresent())
        {
            candidat co=candidat.get();

            co.setNom(candidat2.getNom());
            co.setPrenom(candidat2.getPrenom());
            co.setCin(candidat2.getCin());
            co.setNationalite(candidat2.getNationalite());
            co.setDatedenaissance(candidat2.getDatedenaissance());
            co.setAdresse(candidat2.getAdresse());
            co.setTelephone(candidat2.getTelephone());
            co.setSituationprofessionnelle(candidat2.getSituationprofessionnelle());
            co.setAnneebac(candidat2.getAnneebac());
            co.setFilierelicence(candidat2.getFilierelicence());
            co.setUniversite(candidat2.getUniversite());
            co.setFaculte(candidat2.getFaculte());
            co.setAnneepremiereinscription(candidat2.getAnneepremiereinscription());

            co.setNotes1(candidat2.getNotes1());
            co.setSessions1(candidat2.getSessions1());

            co.setNotes2(candidat2.getNotes2());
            co.setSessions2(candidat2.getSessions2());

            co.setNotes3(candidat2.getNotes3());
            co.setSessions3(candidat2.getSessions3());

            co.setNotes4(candidat2.getNotes4());
            co.setSessions4(candidat2.getSessions4());

            co.setNotes5(candidat2.getNotes5());
            co.setSessions5(candidat2.getSessions5());

            co.setNotes6(candidat2.getNotes6());
            co.setSessions6(candidat2.getSessions6());

            co.setAnneelicence(candidat2.getAnneelicence());

            co.setDiplomelicence(candidat2.getDiplomelicence());

            int ptDeug=0;
            int ptLicence=0;

            double ptSessionDeug1;
            double ptSessionDeug2;
            double ptSessionDeug3;
            double ptSessionDeug4;

            double sommeSessionDeug;
            double sommeSessionLicence;
            double noteDossier;
            double noteDossierfinal;
            double ptSessionLicence5;
            double ptSessionLicence6;



            Float moyenneDeug = (candidat2.getNotes1()+ candidat2.getNotes2()
                    +candidat2.getNotes3()+candidat2.getNotes4())/4;

            Float moyenneLicence= (candidat2.getNotes5()+ candidat2.getNotes6())/2;

            // ++++++++++++++++++++++++++++++++++++cas de moyenne de deug++++++++++++++++++++++++++++++++++++++++
            if(moyenneDeug<11)
            {
                ptDeug=0;
            }
            if(moyenneDeug>=11 && moyenneDeug<12)
            {
                ptDeug=2;
            }
            if(moyenneDeug>=12 && moyenneDeug<13)
            {
                ptDeug=4;
            }
            if(moyenneDeug>=13 && moyenneDeug<14)
            {
                ptDeug=6;
            }
            if(moyenneDeug>=14)
            {
                ptDeug=8;
            }

// ++++++++++++++++++++++++++++++cas de moyenne de Licence+++++++++++++++++++++++++++++++++++++++++++++
            if(moyenneLicence<11)
            {
                ptLicence=0;
            }
            if(moyenneLicence>=11 && moyenneLicence<12)
            {
                ptLicence=2;
            }
            if(moyenneLicence>=12 && moyenneLicence<13)
            {
                ptLicence=4;
            }
            if(moyenneLicence>=13 && moyenneLicence<14)
            {
                ptLicence=6;
            }
            if(moyenneLicence>=14)
            {
                ptLicence=8;
            }
// +++++++++++++++++++++++++cas de point session deug +++++++++++++++++++++++++++++++++++++++++++++++
            if (candidat2.getSessions1().equals("Normale") ){
                ptSessionDeug1 = 0.5;
            }
            else  {
                ptSessionDeug1 = 0;
            }
            if (candidat2.getSessions2().equals("Normale")) {
                ptSessionDeug2 = 0.5;
            }
            else  {
                ptSessionDeug2 = 0;
            }
            if (candidat2.getSessions3().equals("Normale")) {
                ptSessionDeug3 = 0.5;
            }
            else  {
                ptSessionDeug3 = 0;
            }
            if (candidat2.getSessions4().equals("Normale")) {
                ptSessionDeug4 = 0.5;
            }
            else  {
                ptSessionDeug4 = 0;
            }
            sommeSessionDeug=ptSessionDeug1+ptSessionDeug2+ptSessionDeug3+ptSessionDeug4;
            // +++++++++++++++++++++++++cas de point session licence +++++++++++++++++++++++++++++++++++++++++++++++
            if (candidat2.getSessions5().equals("Normale")) {
                ptSessionLicence5 = 1;
            }
           else{
                ptSessionLicence5 = 0;
            }
            if (candidat2.getSessions6().equals("Normale")) {
                ptSessionLicence6 = 1;
            }
            else {
                ptSessionLicence6 = 0;
            }
            sommeSessionLicence = ptSessionLicence5+ptSessionLicence6;

//  +++++++++++++++++++++++++cas de nombre d'année +++++++++++++++++++++++++++++++++++++++++++++++
               String chaine = candidat2.getAnneepremiereinscription();
               String  c = chaine.substring(chaine.length()-4);
               int annee = Integer.parseInt(c);
               Date dt = new Date();
               int year =dt.getYear();
               int current_Year = year + 1900;
               int nbAnnee = current_Year - annee ;
               int ptAnnee ;

          if(nbAnnee == 3){ptAnnee=6;}
          else if (nbAnnee == 4){ptAnnee=3;}
            else ptAnnee=0;

            noteDossier = sommeSessionDeug + sommeSessionLicence +ptDeug +ptLicence + ptAnnee;
            noteDossierfinal = (noteDossier * 20)/26;

            co.setNotedossier(noteDossierfinal);

             candidRepository.save(co);
            return ResponseEntity.ok(new MessageResponse("Candidat enregistré avec succès"));


        } else
        {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}