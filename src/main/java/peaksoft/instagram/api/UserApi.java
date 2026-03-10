package peaksoft.instagram.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.instagram.dto.SimpleResponse;
import peaksoft.instagram.dto.userDto.UpdateUserRequest;
import peaksoft.instagram.dto.userDto.UserResponse;
import peaksoft.instagram.dto.userProfile.UserProfileResponse;
import peaksoft.instagram.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @GetMapping("/profile/{userId}")
    public UserProfileResponse getUserProfile(@PathVariable Long userId){
        return userService.userProfile(userId);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteUser(@PathVariable Long id){
        return userService.delete(id);
    }
}