package sk.tsystems.forum.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tsystems.forum.entity.Like;
import sk.tsystems.forum.entity.Post;
import sk.tsystems.forum.service.post.PostService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PostController {

    @Autowired
    private PostService ps;

    @Autowired
    private MemberController mc;

    @Autowired
    private TopicController tc;

    private Post currentPost;

    private String searchText;

    @RequestMapping("/addpost")
    public String addPost(Post post){
        post.setMember(mc.getLoggedMember());
        post.setTopic(tc.getCurrentTopic());
        ps.addPost(post);
        return "redirect:/topic?id=" + tc.getCurrentTopic().getId();
    }

    @RequestMapping("/editpost")
    public String editPost(long id){
        if(id != 0) {
            currentPost = ps.getPost(id);
            return "editpost";
        }
        return "redirect:/";
    }

    @RequestMapping("/updatepost")
    public String updatePost(String content){
        currentPost.setContent(content);
        ps.updatePost(currentPost);
        return "redirect:/topic?id=" + tc.getCurrentTopic().getId();
    }

    @RequestMapping("/deletepost")
    public String deletePost(long id){
        ps.deletePost(id);
        return "redirect:/topic?id=" + tc.getCurrentTopic().getId();
    }

    @RequestMapping("/likepost")
    public String likePost(long id){
        ps.likePost(new Like(mc.getLoggedMember(),ps.getPost(id)));
        return "redirect:/topic?id=" + tc.getCurrentTopic().getId();
    }

    @RequestMapping("/unlikepost")
    public String unlikePost(long id){
        ps.unlikePost(ps.getPost(id), mc.getLoggedMember());
        return "redirect:/topic?id=" + tc.getCurrentTopic().getId();
    }

    @RequestMapping("/findpost")
    public String findPost(String searchText) {
        this.searchText = searchText;
        return "redirect:/search";
    }

    @RequestMapping("/search")
    public String findPost2() {
        return "search";
    }


    public boolean isMembersPost(long id){
        return mc.isAdmin() || (mc.getLoggedMember() != null && mc.getLoggedMember().getId() == id);
    }

    public boolean hasMemberLiked(long id){
        return (mc.getLoggedMember() != null && ps.getLike(ps.getPost(id),mc.getLoggedMember()) != null);
    }

    public boolean isMemberLogged(){
        return mc.getLoggedMember() != null;
    }

    public long getCurrentPostId(){
        return currentPost.getId();
    }

    public String getSearchText() {
        return searchText;
    }
}
