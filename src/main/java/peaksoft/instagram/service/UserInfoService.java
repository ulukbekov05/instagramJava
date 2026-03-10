package peaksoft.instagram.service;

import peaksoft.instagram.dto.SimpleResponse;
import peaksoft.instagram.dto.userInfo.UserInfoRequest;
import peaksoft.instagram.dto.userInfo.UserInfoResponse;
import peaksoft.instagram.entity.UserInfo;

public interface UserInfoService {

    SimpleResponse saveUserInfo(Long userId, UserInfoRequest userInfoRequest);
    UserInfoResponse findUserInfoByUserId(Long userId);
    SimpleResponse update(Long userId, UserInfoRequest request);
    SimpleResponse delete(Long id);

}
