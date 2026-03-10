package peaksoft.instagram.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.instagram.dto.SimpleResponse;
import peaksoft.instagram.dto.userInfo.UserInfoRequest;
import peaksoft.instagram.dto.userInfo.UserInfoResponse;
import peaksoft.instagram.service.UserInfoService;

@RequiredArgsConstructor
@RequestMapping("/api/userInfo")
@RestController
public class UserInfoApi {
    private final UserInfoService userInfoService;

    @PostMapping("/{userId}")
    public SimpleResponse save(@PathVariable Long userId, @RequestBody @Valid UserInfoRequest userInfoRequest) {
        return userInfoService.saveUserInfo(userId, userInfoRequest);
    }

    @GetMapping("/user-info/{userId}")
    public UserInfoResponse getUserInfo(@PathVariable Long userId) {
        return userInfoService.findUserInfoByUserId(userId);
    }

    @PutMapping("/{userId}")
    public SimpleResponse update(@PathVariable Long userId, @RequestBody UserInfoRequest request) {
        return userInfoService.update(userId, request);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return userInfoService.delete(id);
    }




}