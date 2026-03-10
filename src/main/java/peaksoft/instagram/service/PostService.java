package peaksoft.instagram.service;

import peaksoft.instagram.dto.SimpleResponse;
import peaksoft.instagram.dto.post.PostRequest;
import peaksoft.instagram.dto.post.PostResponse;

import java.util.List;

public interface PostService {
    PostResponse create(PostRequest request);
    PostResponse update(Long postId, PostRequest request);
    PostResponse getById(Long postId);
    SimpleResponse delete(Long postId);
    List<PostResponse> getFeed();
}
