package master.appcondidature.repository;


import master.appcondidature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import master.appcondidature.models.candidat;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository

public interface candidatRepository extends JpaRepository<candidat, Long>{


    Boolean existsByCin(String cin);


    //Query utiliser dans l'éxportation du pdf pour le candidat
    @Query(value = "SELECT u.cin,u.notes1,u.notes2,u.notes3,u.notes4,u.notes5,u.notes6 FROM candidat u WHERE u.id = :param",
            nativeQuery = true)
    List<Object> getcandidatByid(@Param("param") Long param);

    //Query pour sélectionner les filieres
    @Query("select s.filierelicence from candidat s")
    List<Object> getcandidatByfiliere();

    //Query pour sélectionner les sessions
    @Query("select k.sessions1 from candidat k ")
     List<Object>   getcandidatBysessions1();

    //Query pour sélectionner les candidats classé par note Desc
    @Query("select s from candidat s where s.nom is not null order by  s.notedossier DESC ")
    List<Object> orderbynoteDesc();

    //Query pour sélectionner les candidats classé par note Asc
    @Query("select s from candidat s where s.nom is not null order by  s.notedossier asc ")
    List<Object> orderbynoteAsc();

    //Query pour sélectionner les candidats classé par note Desc
    @Query("select s from candidat s where s.nom is not null order by  s.notedossier DESC ")
    List<Object> orderbynoteUnivDesc();

    //Query pour sélectionner les candidats classé par note Asc
    @Query("select s from candidat s where s.nom is not null order by  s.notedossier asc ")
    List<Object> orderbynoteUnivAsc();

    //Query pour sélectionner les candidats classé par note Desc
    @Query("select s from candidat s where s.nom is not null order by  s.notedossier desc ")
    List<Object> orderbynoteEtabDesc();

    //Query pour sélectionner les candidats classé par note Asc
    @Query("select s from candidat s where s.nom is not null order by  s.notedossier asc ")
    List<Object> orderbynoteEtabAsc();



    //Query pour chercher le candidat qu'il a id de user donné au param
    @Query(value = "SELECT * FROM candidat u WHERE u.id = :param",
            nativeQuery = true)
     candidat findByUser(@Param("param") Long param);







}
