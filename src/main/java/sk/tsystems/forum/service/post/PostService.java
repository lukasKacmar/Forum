package sk.tsystems.forum.service.post;

import sk.tsystems.forum.entity.Post;
import sk.tsystems.forum.entity.Topic;

import java.util.List;

public interface PostService {

    void addPost(Post post);

    void updatePost(long id, String content);

    Post getPost(long id);

    List<Post> getPosts(Topic topic);

    long getCount(Topic topic);
}
