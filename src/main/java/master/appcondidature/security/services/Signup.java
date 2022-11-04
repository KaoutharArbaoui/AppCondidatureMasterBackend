package master.appcondidature.security.services;

import master.appcondidature.models.ERole;
import master.appcondidature.models.Role;
import master.appcondidature.models.User;
import master.appcondidature.models.candidat;
import master.appcondidature.payload.request.SignupRequest;
import master.appcondidature.payload.response.MessageResponse;
import master.appcondidature.repository.RoleRepository;
import master.appcondidature.repository.UserRepository;
import master.appcondidature.repository.candidatRepository;
import master.appcondidature.security.jwt.JwtUtils;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

@Service
public class Signup {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    candidatRepository candidRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    //Cette méthode génere le code de vérification et puis crée l'utilisateur
        public void registre(SignupRequest signUpRequest) {

            String randomCode=RandomString.make(64);

            User user = new User(signUpRequest.getUsername(),
                                 signUpRequest.getEmail(),
                                  encoder.encode(signUpRequest.getPassword()),
                                  signUpRequest.setVerificationCode(randomCode));
            candidat candidat1 = new candidat(user);

            Set<String> strRoles = signUpRequest.getRole();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
                } else {
                  strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);

                            break;


                        default:
                            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                    }
                });
            }

            user.setRoles(roles);
             userRepository.save(user) ;
            candidRepository.save(candidat1);

        }

    //Cette méthode enverra un e-mail à l'e-mail de l'utilisateur (capturé du formulaire d'inscription)
        public void sendVerificationEmail(SignupRequest signupRequest, String siteURL)
                    throws MessagingException, UnsupportedEncodingException {
                 String subject = "Veuillez vérifier votre inscription";
                String senderName = "Candidature master";
                String mailContent = "Bonjour " + signupRequest.getUsername() + ",<p>";
                mailContent += "<p>Veuillez cliquer sur le lien ci-dessous pour vérifier votre inscription: </p>";
                mailContent += "<h3><a href=\"[[URL]]\" target=\"_self\">Vérifier</a></h3>";
                mailContent += "<p> Merci <br> Candidature master équipe</p>";

                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);


                String verifyURL = siteURL + "/api/auth/verify?code="+signupRequest.getVerificationCode();
                mailContent = mailContent.replace("[[URL]]",verifyURL);
                helper.setFrom("kaouthar1012@gmail.com",senderName);
                helper.setTo(signupRequest.getEmail());
                helper.setSubject(subject);
                helper.setText(mailContent,true);

                mailSender.send(message);

            }

    //Lorq l'ulis clique sur vérifier dans sans émail, le champs enabled dans la BD devient true Cad 1
    // et le code de verification se réinist avec la valeur null.
         public  boolean verify(String verificationCode)
             {
                 User user = userRepository.findByVerificationCode(verificationCode);

                 if(user == null || user.isEnabled())
                      {
                         return  false;
                      }
                     else
                     {

                         user.setVerificationCode(null);
                         user.setEnabled(true);
                         userRepository.save(user);
                        //userRepository.enable(user.getId());
                        return  true;
                     }
             }

}
