package se.sdaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.sdaproject.model.Topics;

import java.util.List;

@Repository
public interface TopicsRepository extends JpaRepository<Topics, Long> {
    List<Topics> findTopicByName(String name);
}