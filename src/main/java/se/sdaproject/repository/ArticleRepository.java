package se.sdaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.sdaproject.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}