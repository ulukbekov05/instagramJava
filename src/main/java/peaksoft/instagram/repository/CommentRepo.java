package peaksoft.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.instagram.dto.comment.CommentResponse;
import peaksoft.instagram.entity.Comment;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    @Query("""
     select new peaksoft.instagram.dto.comment.CommentResponse(
          c.id, c.comment, c.createdAt, c.user.userName, c.like.likeCount)
        from Comment c where c.post.id = :postId
    """)
    List<CommentResponse> findAllByPostId(@Param("postId") Long postId);
}
