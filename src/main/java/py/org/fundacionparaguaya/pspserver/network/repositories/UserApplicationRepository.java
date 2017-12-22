package py.org.fundacionparaguaya.pspserver.network.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.org.fundacionparaguaya.pspserver.network.entities.UserApplicationEntity;
import py.org.fundacionparaguaya.pspserver.security.entities.UserEntity;

import java.util.Optional;

public interface UserApplicationRepository extends JpaRepository<UserApplicationEntity, Long> {

    Optional<UserApplicationEntity> findByUser(UserEntity entity);
}
