package peaksoft.instagram.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.instagram.dto.SimpleResponse;
import peaksoft.instagram.dto.comment.CommentRequest;
import peaksoft.instagram.dto.comment.CommentResponse;
import peaksoft.instagram.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentApi {
    private final CommentService commentService;

    @PostMapping
    public SimpleResponse save(@RequestBody CommentRequest request) {
        return commentService.save(request);
    }

    @GetMapping("/{postId}")
    public List<CommentResponse> findAll(@PathVariable Long postId) {
        return commentService.findAllByPostId(postId);
    }

    @DeleteMapping("/{commentId}")
    public SimpleResponse delete(@PathVariable Long commentId) {
        return commentService.deleteById(commentId);
    }
}
