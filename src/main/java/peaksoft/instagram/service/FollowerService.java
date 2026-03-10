package peaksoft.instagram.service;

import peaksoft.instagram.dto.SimpleResponse;
import peaksoft.instagram.dto.follower.UserShortResponse;
import peaksoft.instagram.dto.userDto.UserSearchResponse;
import peaksoft.instagram.entity.User;

import java.util.List;

public interface FollowerService {
    List<UserSearchResponse> searchUsers(String query);
    List<UserShortResponse> getAllSubscribersByUserId(Long userId);
    List<UserShortResponse> getAllSubscriptionsByUserId(Long userId);
    SimpleResponse subscribe(Long currentUserId, Long profileUserId);
}
