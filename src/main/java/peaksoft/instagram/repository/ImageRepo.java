package peaksoft.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.instagram.entity.Image;

public interface ImageRepo extends JpaRepository<Image, Long> {
}
