package peaksoft.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.instagram.entity.Like;

public interface LikeRepo extends JpaRepository<Like, Long> {



}
