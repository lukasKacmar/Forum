package sk.tsystems.forum.service.topic;

import sk.tsystems.forum.entity.Section;
import sk.tsystems.forum.entity.Topic;

import java.util.List;

public interface TopicService {

    void addTopic(Topic topic);

    Topic getTopic(Long id);

    List<Topic> getTopics(Section section);

    List<Topic> findTopics(String searchText);

    long getCount(Section section);

    void updateTopic(Topic topic);

    void deleteTopic(long id);
}
