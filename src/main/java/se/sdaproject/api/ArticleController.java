package se.sdaproject.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.sdaproject.model.Article;
import se.sdaproject.service.ArticleService;

import java.util.List;

@RestController
public class ArticleController {

    ArticleService articleService;

    @Autowired  //@Autowired annotation allows Spring to resolve and inject collaborating beans into your bean.
    public ArticleController(ArticleService articleService) {

        this.articleService = articleService;
    }

    //GET	/articles      return all the articles
    @GetMapping("/articles")
    public List<Article> listAllArticles() {
        return articleService.getAll();
    }

    //GET	/articles/{id}	return a specific article based on the provided id.
    @GetMapping("/articles/{id}")
    public Article getById(@PathVariable Long id) {
        return articleService.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //POST	/articles	create a new article.
    @PostMapping("/articles")
    public Article createArticle(@RequestBody Article newArticle) {
        return articleService.create(newArticle);
    }

    //PUT	/articles/{id}	update the given article.

    @PutMapping("/articles/{id}")
    Article updateArticle(@RequestBody Article updatedArticle) {
        return articleService.update(updatedArticle);
    }

    //DELETE	/articles/{id}	delete the given article.
    @DeleteMapping("/articles/{id}")
    public void deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
    }
}



