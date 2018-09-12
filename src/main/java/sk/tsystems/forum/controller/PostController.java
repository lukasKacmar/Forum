package sk.tsystems.forum.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
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

    @RequestMapping("/addpost")
    public String addPost(Post post){
        post.setMember(mc.getLoggedMember());
        post.setTopic(tc.getCurrentTopic());
        ps.addPost(post);
        return "redirect:/";
    }



}
