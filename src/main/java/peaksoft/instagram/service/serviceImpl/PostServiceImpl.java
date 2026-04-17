package peaksoft.instagram.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.instagram.dto.SimpleResponse;
import peaksoft.instagram.dto.follower.UserShortResponse;
import peaksoft.instagram.dto.post.PostRequest;
import peaksoft.instagram.dto.post.PostResponse;
import peaksoft.instagram.entity.Image;
import peaksoft.instagram.entity.Like;
import peaksoft.instagram.entity.Post;
import peaksoft.instagram.entity.User;
import peaksoft.instagram.repository.ImageRepo;
import peaksoft.instagram.repository.LikeRepo;
import peaksoft.instagram.repository.PostRepo;
import peaksoft.instagram.repository.UserRepo;
import peaksoft.instagram.service.PostService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final ImageRepo imageRepo;
    private final LikeRepo likeRepo;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User табылган жок"));
    }

    @Override
    public PostResponse create(PostRequest request) {

        User user = getCurrentUser();

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        post.setUser(user);

        if (request.getTaggedUserIds() != null) {
            List<User> users = userRepo.findAllById(request.getTaggedUserIds());
            post.setTaggedUsers(users);
        }

        postRepo.save(post);

        Image image = new Image();
        image.setImageURL(request.getImageURL());
        image.setPost(post);
        imageRepo.save(image);

        Like like = new Like();
        like.setLike(false);
        like.setLikeCount(0);
        like.setPost(post);
        likeRepo.save(like);

        return mapToResponse(post, image, like);
    }



    @Override
    public PostResponse update(Long postId, PostRequest request) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post табылган жок"));

        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        postRepo.save(post);
        return mapToResponse(post, post.getImage(), post.getLike());
    }

    @Override
    public PostResponse getById(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post табылган жок"));
        return mapToResponse(post, post.getImage(), post.getLike());
    }

    @Override
    public SimpleResponse delete(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post табылган жок"));
        postRepo.delete(post);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Post өчүрүлдү")
                .build();
    }

    @Override
    public List<PostResponse> getFeed() {
        User user = getCurrentUser();
        List<Post> posts = postRepo.getFeed(user.getId());
        return posts.stream()
                .map(post -> mapToResponse(post, post.getImage(), post.getLike())).toList();
    }



    private PostResponse mapToResponse(Post post, Image image, Like like) {
        List<UserShortResponse> tagged = post.getTaggedUsers()
                .stream()
                .map(u -> new UserShortResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getUserInfo() != null ? u.getUserInfo().getImage() : null)).toList();
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getCreatedAt(),
                image.getImageURL(),
                like.isLike(),
                like.getLikeCount(),
                tagged
        );
    }


}