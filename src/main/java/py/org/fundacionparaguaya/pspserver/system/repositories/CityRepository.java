package py.org.fundacionparaguaya.pspserver.system.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.system.entities.CityEntity;

public interface CityRepository extends JpaRepository<CityEntity, Long> {
    Optional<CityEntity> findByCity(String city);
}
