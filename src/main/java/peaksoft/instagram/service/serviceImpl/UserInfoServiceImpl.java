package peaksoft.instagram.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import peaksoft.instagram.config.jwt.JwtService;
import peaksoft.instagram.dto.SimpleResponse;
import peaksoft.instagram.dto.userInfo.UserInfoRequest;
import peaksoft.instagram.dto.userInfo.UserInfoResponse;
import peaksoft.instagram.entity.User;
import peaksoft.instagram.entity.UserInfo;
import peaksoft.instagram.repository.UserInfoRepo;
import peaksoft.instagram.repository.UserRepo;
import peaksoft.instagram.service.UserInfoService;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepo userInfoRepo;
    private final UserRepo userRepo;
    private final JwtService jwtService;


    @Override
    public SimpleResponse saveUserInfo(Long userId, UserInfoRequest userInfoRequest) {
        User currentUser = jwtService.checkAuthentication();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        if (user.getUserInfo() != null) {
            throw new RuntimeException("UserInfo already exists");
        }
        if (!currentUser.equals(user)){
            throw new BadCredentialsException("you  can not save user info ");
        }
        UserInfo userInfo = UserInfo.builder()
                .fullName(userInfoRequest.getFullName())
                .biography(userInfoRequest.getBiography())
                .gender(userInfoRequest.getGender())
                .image(userInfoRequest.getImage())
                .user(user).build();
        userInfoRepo.save(userInfo);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("UserInfo successfully saved")
                .build();
    }

    @Override
    public UserInfoResponse findUserInfoByUserId(Long userId) {
        User currentUser = jwtService.checkAuthentication();
      User user =  userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
      UserInfo userInfo = user.getUserInfo();
        if (userInfo ==null){
            throw new NoSuchElementException("not found User Info");}
        if (!currentUser.equals(user)){
            throw new BadCredentialsException("you  can not see UserInfo");
        }
        return UserInfoResponse.builder()
                .fullName(userInfo.getFullName())
                .biography(userInfo.getBiography())
                .gender(userInfo.getGender())
                .image(userInfo.getImage())
                .build();
    }


    @Override
    public SimpleResponse update(Long userId, UserInfoRequest request) {
        User currentUser = jwtService.checkAuthentication();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        UserInfo userInfo = user.getUserInfo();
        if (userInfo == null) {
            throw new NoSuchElementException("UserInfo not found");
        }
        if (!currentUser.equals(user)){
            throw new BadCredentialsException("you  can not update");
        }

        userInfo.setFullName(request.getFullName());
        userInfo.setBiography(request.getBiography());
        userInfo.setGender(request.getGender());
        userInfo.setImage(request.getImage());

        userInfoRepo.save(userInfo);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("UserInfo updated successfully")
                .build();
    }
    @Override
    public SimpleResponse delete(Long id) {
        User currentUser = jwtService.checkAuthentication();
        User user = userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        UserInfo userInfo = user.getUserInfo();
        if (userInfo == null) {
            throw new NoSuchElementException("UserInfo not found");}
        if (!currentUser.equals(user)){
            throw new BadCredentialsException("you  can not deleted");
        }
        user.setUserInfo(null);
        userInfoRepo.delete(userInfo);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("UserInfo  deleted  successfully")
                .build();
    }
}
