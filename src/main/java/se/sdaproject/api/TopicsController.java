package se.sdaproject.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sdaproject.api.exception.ResourceNotFoundException;
import se.sdaproject.model.Article;
import se.sdaproject.model.Topics;
import se.sdaproject.repository.ArticleRepository;
import se.sdaproject.repository.TopicsRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TopicsController {

    TopicsRepository topicsRepository;
    ArticleRepository articleRepository;

    @Autowired
    public TopicsController(TopicsRepository topicsRepository, ArticleRepository articleRepository) {
        this.topicsRepository = topicsRepository;
        this.articleRepository = articleRepository;
    }


    //POST	/create new topic
    @PostMapping("/topics")
    public ResponseEntity<Topics> createNewTopic(@Valid @RequestBody Topics topics) {
        topicsRepository.save(topics);
        return ResponseEntity.status(HttpStatus.CREATED).body(topics);
    }

    //GET	/topics	return all topics.
    @GetMapping("/topics")
    public ResponseEntity<List<Topics>> listAllTopics() {
        List<Topics> topics = topicsRepository.findAll();
        return ResponseEntity.ok(topics);
    }

    //GET	/articles/{articleId}/topics	return all topics associated with article given by articleId.
    @GetMapping("articles/{articleId}/topics")
    public ResponseEntity<List<Topics>> viewAssociatedTopics(@PathVariable Long articleId) {
        Article showArticle = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        List<Topics> showTopicsByArticles = showArticle.getTopics();
        return ResponseEntity.ok(showTopicsByArticles);
    }

    //POST	/articles/{articleId}/topics	associate the topic with the article given by articleId. If topic does not already exist, it is created.
    @PostMapping("/articles/{articleId}/topics")
    public ResponseEntity<Article> associateOrCreateTopic(@PathVariable Long articleId, @RequestBody Topics topics) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(ResourceNotFoundException::new);
        List<Topics> associateTopics = topicsRepository.findTopicByName(topics.getName());
        if ( associateTopics.isEmpty() ) {
            topicsRepository.save(topics);
        } else {
            throw new ResourceNotFoundException();
        }
        article.getTopics().add(topics);
        articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }

    //PUT	/topics/{id}	update the given topic.
    @PutMapping("/topics/{id}")
    public ResponseEntity<Topics> updateTopic(@PathVariable Long id, @Valid @RequestBody Topics updatedTopic) {
        Topics topics = topicsRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        updatedTopic.setId(id);
        topicsRepository.save(updatedTopic);
        return ResponseEntity.ok(topics);
    }

    //DELETE	/topics/{id}	delete the given topic.
    @DeleteMapping("/topics/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable Long id) {
        Topics topic = topicsRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        topicsRepository.delete(topic);
    }


    //DELETE	/articles/{articleId}/topics/{topicId}
    // delete the association of a topic for the given article. The topic & article themselves remain.
    @DeleteMapping("/articles/{articleId}/topics/{topicId}}")
    public ResponseEntity<Article> deleteAssociationOfTopics(@PathVariable Long topicId, @PathVariable Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        Topics topics = topicsRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        article.getTopics().remove(topics);
        articleRepository.save(article);
        return ResponseEntity.ok(article);
    }

    //GET	/topics/{topicId}/articles	return all articles associated with the topic given by topicId.
    @GetMapping("/topics/{topicId}/articles")
    public ResponseEntity<List<Article>> listArticleTopics(@PathVariable Long topicId) {
        Topics topics = topicsRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        List<Article> articles = topics.getArticle();
        return ResponseEntity.ok(articles);
    }

}