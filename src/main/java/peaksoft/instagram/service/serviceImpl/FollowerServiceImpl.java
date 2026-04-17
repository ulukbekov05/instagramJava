package peaksoft.instagram.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import peaksoft.instagram.config.jwt.JwtService;
import peaksoft.instagram.dto.SimpleResponse;
import peaksoft.instagram.dto.follower.UserShortResponse;
import peaksoft.instagram.dto.userDto.UserSearchResponse;
import peaksoft.instagram.entity.Follower;
import peaksoft.instagram.entity.User;
import peaksoft.instagram.repository.FollowerRepo;
import peaksoft.instagram.repository.UserRepo;
import peaksoft.instagram.service.FollowerService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService {
    private final FollowerRepo followerRepo;
    private final UserRepo userRepo;
    private final JwtService jwtService;


    @Override
    public List<UserSearchResponse> searchUsers(String query) {
        List<UserSearchResponse> users = followerRepo.searchUsers(query);
        if (users.isEmpty()) {
            throw new NoSuchElementException(String.format("NOT FOUND THIS %s", query));
        }
        return users;
    }

    public List<UserShortResponse> getAllSubscribersByUserId(Long userId) {

        return followerRepo.findSubscribersByUserId(userId);
    }

    public List<UserShortResponse> getAllSubscriptionsByUserId(Long userId) {
        return followerRepo.findSubscriptionsByUserId(userId);
    }


    @Override
    public SimpleResponse subscribe(Long currentUserId, Long profileUserId) {
        User currentuser = jwtService.checkAuthentication();
        if (currentUserId.equals(profileUserId)) {
            throw new BadCredentialsException("Өзүңүзгө подписка кыла албайсыз!");}

        User currentUser = userRepo.findById(currentUserId)
                .orElseThrow(() -> new NoSuchElementException("User табылган жок"));
        User profileUser = userRepo.findById(profileUserId)
                .orElseThrow(() -> new NoSuchElementException("User табылган жок"));
        if (!currentuser.equals(currentUser)){
            throw new BadCredentialsException("you  can not subscribe");
        }
        Follower myFollower = followerRepo.findByUserId(currentUserId)
                .orElseThrow(() -> new NoSuchElementException("Follower табылган жок"));
        Follower profileFollower = followerRepo.findByUserId(profileUserId)
                .orElseThrow(() -> new NoSuchElementException("Follower табылган жок"));

        boolean subscribed = myFollower.getSubscriptions()
                .stream()
                .anyMatch(u -> u.getId().equals(profileUserId));

        if (subscribed==true) {
            myFollower.getSubscriptions().remove(profileUser);
            profileFollower.getSubscribers().remove(currentUser);
        } else {
            myFollower.getSubscriptions().add(profileUser);
            profileFollower.getSubscribers().add(currentUser);
        }

        followerRepo.save(myFollower);
        followerRepo.save(profileFollower);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(subscribed ? "Подписаться" : "Отменить подписку")
                .build();
    }


}
