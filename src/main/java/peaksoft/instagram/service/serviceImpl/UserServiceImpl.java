    package peaksoft.instagram.service.serviceImpl;

    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.security.authentication.BadCredentialsException;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import peaksoft.instagram.config.jwt.JwtService;
    import peaksoft.instagram.dto.SimpleResponse;
    import peaksoft.instagram.dto.userDto.PostResponse;
    import peaksoft.instagram.dto.userDto.UpdateUserRequest;
    import peaksoft.instagram.dto.userDto.UserResponse;
    import peaksoft.instagram.dto.userProfile.UserProfileResponse;
    import peaksoft.instagram.entity.Follower;
    import peaksoft.instagram.entity.Post;
    import peaksoft.instagram.entity.User;
    import peaksoft.instagram.entity.UserInfo;
    import peaksoft.instagram.repository.PostRepo;
    import peaksoft.instagram.repository.UserRepo;
    import peaksoft.instagram.service.UserService;

    import java.util.List;
    import java.util.NoSuchElementException;

    @RequiredArgsConstructor
    @Service
    @Transactional
    public class UserServiceImpl implements UserService {

        private final UserRepo userRepo;
        private final PostRepo postRepo;
        private final JwtService jwtService;

        @Override
        public UserProfileResponse userProfile(Long userId) {


            User user = userRepo.findById(userId).orElseThrow(()
                    -> new NoSuchElementException("User not found"));

            List<Post> posts = postRepo.findAllByUserIdOrderByCreatedAtDesc(userId);

            List<PostResponse> postResponses = posts.stream()
                    .map(post -> PostResponse.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .description(post.getDescription())
                            .imageUrl(post.getImage().getImageURL())
                            .createdAt(post.getCreatedAt())
                            .build()).toList();

            UserInfo userInfo = user.getUserInfo();
            Follower follower = user.getFollower();

            return UserProfileResponse.builder()
                    .userName(user.getUsername())
                    .image(userInfo != null ? userInfo.getImage() : null)
                    .fullName(userInfo != null ? userInfo.getFullName() : null)
                    .subscribersCount(follower != null ? follower.getSubscribers().size() : 0)
                    .subscriptionsCount(follower != null ? follower.getSubscriptions().size() : 0)
                    .posts(postResponses)
                    .build();
        }

        public List<UserResponse> getAllUsers() {
            return userRepo.findAll().stream()
                    .map(user -> UserResponse.builder()
                            .id(user.getId())
                            .userName(user.getUsername())
                            .email(user.getEmail())
                            .phoneNumber(user.getPhoneNumber())
                            .build()).toList();
        }

        public UserResponse getUserById(Long id) {
            User user = userRepo.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
            return UserResponse.builder()
                    .id(user.getId())
                    .userName(user.getUsername())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .build();
        }




        public SimpleResponse updateUser(Long id, UpdateUserRequest request) {
            User currentUser = jwtService.checkAuthentication();
            User user = userRepo.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            if (!currentUser.equals(user)){
                throw new BadCredentialsException("you  can not update");
            }

            user.setUserName(request.getUserName());
            user.setEmail(request.getEmail());
            user.setPhoneNumber(request.getPhoneNumber());
            userRepo.save(user);
            return new SimpleResponse(HttpStatus.OK, "user  updated ");
        }


        @Override
        public SimpleResponse delete(Long id) {
            User currentUser = jwtService.checkAuthentication();
            User user = userRepo.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("User табылган жок"));
            if (!currentUser.equals(user)){
                throw new BadCredentialsException("you  can not delete");
            }
            userRepo.delete(user);
            return new SimpleResponse(HttpStatus.OK, "user өчүрүлдү");
        }





    }
