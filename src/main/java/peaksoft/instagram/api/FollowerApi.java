package peaksoft.instagram.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.instagram.dto.SimpleResponse;
import peaksoft.instagram.dto.follower.UserShortResponse;
import peaksoft.instagram.dto.userDto.UserSearchResponse;
import peaksoft.instagram.service.FollowerService;

import java.util.List;

@RestController
@RequestMapping("/api/follower")
@RequiredArgsConstructor
public class FollowerApi {
    private final FollowerService followerService;

    @GetMapping("/search")
        public List<UserSearchResponse> search(@RequestParam String query) {
        return followerService.searchUsers(query);
    }

    @GetMapping("/{userId}/subscribers")
    public List<UserShortResponse> getAllSubscribers(@PathVariable Long userId) {
        return followerService.getAllSubscribersByUserId(userId);
    }

    @GetMapping("/{userId}/subscriptions")
    public List<UserShortResponse> getAllSubscriptions(@PathVariable Long userId) {
        return followerService.getAllSubscriptionsByUserId(userId);
    }

    @PostMapping("/subscribe/{currentUserId}/{profileUserId}")
    public SimpleResponse subscribe(@PathVariable Long currentUserId, @PathVariable Long profileUserId) {
        return followerService.subscribe(currentUserId, profileUserId);
    }

}
