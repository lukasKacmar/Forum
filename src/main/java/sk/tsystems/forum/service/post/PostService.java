package sk.tsystems.forum.service.post;

import sk.tsystems.forum.entity.Like;
import sk.tsystems.forum.entity.Member;
import sk.tsystems.forum.entity.Post;
import sk.tsystems.forum.entity.Topic;

import java.util.List;

public interface PostService {

    void addPost(Post post);

    void updatePost(long id, String content);

    Post getPost(long id);

    List<Post> getPosts(Topic topic);

    long getCount(Topic topic);

    void deletePost(long id);

    void likePost(Like like);

    void unlikePost(Post post, Member member);

    Like getLike(Post post, Member member);

    long getLikesCount(Post post);
}
