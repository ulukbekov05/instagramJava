package peaksoft.instagram.service;

import peaksoft.instagram.dto.SimpleResponse;

import peaksoft.instagram.dto.userDto.UpdateUserRequest;
import peaksoft.instagram.dto.userDto.UserResponse;
import peaksoft.instagram.dto.userProfile.UserProfileResponse;

import java.util.List;

public interface UserService {
    UserProfileResponse userProfile(Long userId);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    SimpleResponse updateUser(Long id, UpdateUserRequest request);
    SimpleResponse delete(Long id);


}
