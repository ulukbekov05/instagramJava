package peaksoft.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.instagram.entity.User;
import peaksoft.instagram.entity.UserInfo;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);


    boolean existsByuserName(String userName);
}
