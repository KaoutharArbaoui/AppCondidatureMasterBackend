package master.appcondidature.repository;

import java.util.Optional;

import master.appcondidature.models.candidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import master.appcondidature.models.User;
@Repository

public interface UserRepository extends JpaRepository<User, Long> {



    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);


    //ces 2 queries pa utiliser comme une dexiéme méthode pour vérification d'email voir l'autre méthode
    //utilisé dans security/services/Signup line 134 et 135


    //sélectionner user qu'il a un certain code de verification
    @Query("select u from User u where u.verificationCode = ?1")
    public User findByVerificationCode(String code);

    // modifier le champs enabled dans la table si user verifie son compte enabled devient 1
    @Query("UPDATE User c SET c.enabled = true WHERE c.id = ?1")
    @Modifying
    public void enable(Long id);




}
