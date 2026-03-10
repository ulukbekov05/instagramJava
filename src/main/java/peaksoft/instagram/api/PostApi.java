package peaksoft.instagram.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import peaksoft.instagram.dto.SimpleResponse;
import peaksoft.instagram.dto.post.PostRequest;
import peaksoft.instagram.dto.post.PostResponse;
import peaksoft.instagram.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApi {

    private final PostService postService;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse createPost(@RequestBody @Valid PostRequest request) {
        return postService.create(request);
    }

    @PutMapping("/{postId}")
    public PostResponse updatePost(@PathVariable Long postId, @RequestBody @Valid PostRequest request) {
        return postService.update(postId, request);
    }

    @GetMapping("/{postId}")
    public PostResponse getPostById(@PathVariable Long postId) {
        return postService.getById(postId);
    }

    @DeleteMapping("/{postId}")
    public SimpleResponse deletePost(@PathVariable Long postId) {
        return postService.delete(postId);
    }

    @GetMapping("/feed")
    public List<PostResponse> getFeed() {
        return postService.getFeed();
    }
}