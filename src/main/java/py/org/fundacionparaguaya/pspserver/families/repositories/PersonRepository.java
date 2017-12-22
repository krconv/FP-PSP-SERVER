package py.org.fundacionparaguaya.pspserver.families.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.families.entities.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

}
