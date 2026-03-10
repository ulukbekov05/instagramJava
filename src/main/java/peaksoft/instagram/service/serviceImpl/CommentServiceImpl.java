package peaksoft.instagram.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.instagram.config.jwt.JwtService;
import peaksoft.instagram.dto.SimpleResponse;
import peaksoft.instagram.dto.comment.CommentRequest;
import peaksoft.instagram.dto.comment.CommentResponse;
import peaksoft.instagram.entity.Comment;
import peaksoft.instagram.entity.Like;
import peaksoft.instagram.entity.Post;
import peaksoft.instagram.entity.User;
import peaksoft.instagram.repository.CommentRepo;
import peaksoft.instagram.repository.LikeRepo;
import peaksoft.instagram.repository.PostRepo;
import peaksoft.instagram.repository.UserRepo;
import peaksoft.instagram.service.CommentService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final LikeRepo likeRepo;
    private final JwtService jwtService;


    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User табылган жок"));
    }

    @Override
    public SimpleResponse save(CommentRequest request) {
        User user = getCurrentUser();
        Post post = postRepo.findById(request.getPostId())
                .orElseThrow(() -> new NoSuchElementException("Post табылган жок"));

        Comment comment = new Comment();
        comment.setComment(request.getComment());
        comment.setUser(user);
        comment.setPost(post);
        commentRepo.save(comment);

        Like like = new Like();
        like.setLike(false);
        like.setLikeCount(0);
        like.setComment(comment);
        likeRepo.save(like);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Комментарий сакталды")
                .build();
    }

    @Override
    public List<CommentResponse> findAllByPostId(Long postId) {
        return commentRepo.findAllByPostId(postId);
    }

    @Override
    public SimpleResponse deleteById(Long commentId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Комментарий табылган жок"));

        commentRepo.delete(comment);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Комментарий өчүрүлдү")
                .build();
    }
}
