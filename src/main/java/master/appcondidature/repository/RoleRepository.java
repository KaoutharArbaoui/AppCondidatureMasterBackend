package master.appcondidature.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import master.appcondidature.models.Role;
import master.appcondidature.models.ERole;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    //chercher le nom du role soit user soit admin
    Optional<Role> findByName(ERole name);
}
