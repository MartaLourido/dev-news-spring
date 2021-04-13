package se.sdaproject.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sdaproject.api.exception.ResourceNotFoundException;
import se.sdaproject.model.Article;
import se.sdaproject.model.Comments;
import se.sdaproject.repository.ArticleRepository;
import se.sdaproject.repository.CommentsRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CommentsController {


    CommentsRepository commentsRepository;
    ArticleRepository articleRepository;

    @Autowired
    public CommentsController(CommentsRepository commentsRepository, ArticleRepository articleRepository) {
        this.commentsRepository = commentsRepository;
        this.articleRepository = articleRepository;
    }

    //GET	/articles/{articleId}/comments	return all comments on article given by articleId.
    @GetMapping("/articles/{articleId}/comments")
    public List<Comments> getAllByArticleId(@RequestParam(required = false) Long articleId) {
        if ( articleId == null ) {
            return commentsRepository.findAll();
        } else {
            return commentsRepository.findAllByArticleId(articleId);
        }
    }

    //GET	/comments?authorName={authorName}	return all comments made by author given by authorName.
    @GetMapping(value = "/comments", params = {"authorName"})
    public ResponseEntity<List<Comments>> returnAllCommentsByAuthor(@RequestParam String authorName) {
        return ResponseEntity.ok(commentsRepository.findByAuthorName(authorName));
    }

    //POST	/articles/{articleId}/comments	create a new comment on article given by articleId.
    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<Comments> createComment(@PathVariable Long articleId, @Valid @RequestBody Comments comments) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(ResourceNotFoundException::new);
        comments.setArticle(article);
        return ResponseEntity.ok(commentsRepository.save(comments));
    }


    //PUT	/comments/{id}	update the given comment.
    @PutMapping("/comments/{id}")
    public ResponseEntity<Comments> updateComment(@PathVariable Long id, @RequestBody Comments updatedComment) {
        Comments comments = commentsRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        updatedComment.setId(comments.getId());
        updatedComment.setArticle(comments.getArticle());
        return ResponseEntity.ok(commentsRepository.save(updatedComment));
    }


    //DELETE /comments/{id}	delete the given comment
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Comments> deleteComment(@PathVariable Long id) {
        Comments comments = commentsRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        commentsRepository.delete(comments);
        return ResponseEntity.ok(comments);
    }

}