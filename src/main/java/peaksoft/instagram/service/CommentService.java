package peaksoft.instagram.service;

import peaksoft.instagram.dto.SimpleResponse;
import peaksoft.instagram.dto.comment.CommentRequest;
import peaksoft.instagram.dto.comment.CommentResponse;

import java.util.List;

public interface CommentService {

    SimpleResponse save(CommentRequest request);
    List<CommentResponse> findAllByPostId(Long postId);
    SimpleResponse deleteById(Long commentId);
}
