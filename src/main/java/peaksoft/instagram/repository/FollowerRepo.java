package peaksoft.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.instagram.dto.follower.UserShortResponse;
import peaksoft.instagram.dto.userDto.UserSearchResponse;
import peaksoft.instagram.entity.Follower;
import peaksoft.instagram.entity.User;

import java.util.List;
import java.util.Optional;

public interface FollowerRepo extends JpaRepository<Follower, Long> {

    @Query("""
   select new peaksoft.instagram.dto.userDto.UserSearchResponse(u.userName, ui.fullName, ui.image)
   from User u left join u.userInfo ui
   where lower(u.userName) like lower(concat('%', :query, '%'))
   or lower(ui.fullName) like lower(concat('%', :query, '%'))""")
    List<UserSearchResponse> searchUsers(String query);

    @Query("select f from Follower f where f.user.id = :userId")
    Optional<Follower> findByUserId(@Param("userId") Long userId);

    @Query("""
    select new peaksoft.instagram.dto.follower.UserShortResponse( u.id, u.userName, u.email, ui.image)
    from Follower f join f.subscribers u
    left join u.userInfo ui  where f.user.id = :userId""")
    List<UserShortResponse> findSubscribersByUserId(@Param("userId") Long userId);

    @Query("""
    select new peaksoft.instagram.dto.follower.UserShortResponse(  u.id, u.userName, u.email, ui.image) from Follower f
    join f.subscriptions u left join u.userInfo ui where f.user.id = :userId""")
    List<UserShortResponse> findSubscriptionsByUserId(@Param("userId") Long userId);
}
