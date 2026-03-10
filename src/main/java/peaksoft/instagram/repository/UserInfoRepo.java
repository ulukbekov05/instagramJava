package peaksoft.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.instagram.entity.User;
import peaksoft.instagram.entity.UserInfo;

import java.util.Optional;

public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {



}
