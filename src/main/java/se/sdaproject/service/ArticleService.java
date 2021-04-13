package se.sdaproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.sdaproject.model.Article;
import se.sdaproject.repository.ArticleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public Optional<Article> getById(Long id) {
        return articleRepository.findById(id);
    }

    public Article create(Article newArticle) {
        return articleRepository.save(newArticle);
    }

    //PUT	/articles/{id}	update the given article.
    public Article update(Article updatedArticles) {
        return articleRepository.save(updatedArticles);
    }

    //DELETE	/articles/{id}	delete the given article.
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}