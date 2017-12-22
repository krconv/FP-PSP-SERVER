package py.org.fundacionparaguaya.pspserver.families.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;

import java.lang.Long;

public interface FamilyRepository extends JpaRepository<FamilyEntity, Long>, JpaSpecificationExecutor<FamilyEntity> {

}
