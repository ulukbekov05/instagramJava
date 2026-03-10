package peaksoft.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.instagram.entity.Post;


import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findAllByUserIdOrderByCreatedAtDesc(Long userId);

   @Query("""
    select p from Post p where p.user.id = :userId or p.user in ( select u from Follower f join f.subscriptions u
     where f.user.id = :userId) order by p.createdAt desc""")
    List<Post> getFeed(@Param("userId") Long userId);
}
