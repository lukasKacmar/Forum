package sk.tsystems.forum.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.service.topic.TopicService;


@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class TopicController {

    @Autowired
    private TopicService ts;

    @Autowired
    private MemberController mc;

    @Autowired
    private SectionController sc;

    private Topic currentTopic;

    @RequestMapping("/addtopic")
    public String addTopic(Topic topic) {
        topic.setMember(mc.getLoggedMember());
        topic.setSection(sc.getCurrentSection());
        ts.addTopic(topic);
        return "redirect:/section?id=" + sc.getCurrentSection().getId();
    }

    @RequestMapping("/edittopic")
    public String editTopic(long id){
        if(id != 0){
            currentTopic = ts.getTopic(id);
            return "edittopic";
        }
        return "redirect:/";
    }

    @RequestMapping("/updatetopic")
    public String updatePost(String title, String content){
        currentTopic.setTitle(title);
        currentTopic.setContent(content);
        ts.updateTopic(currentTopic);
        return "redirect:/section?id=" + sc.getCurrentSection().getId();
    }

    @RequestMapping("/deletetopic")
    public String deleteTopic(long id) {
        ts.deleteTopic(id);
        return "redirect:/section?id=" + sc.getCurrentSection().getId();
    }

    @RequestMapping("/topic")
    public String accessTopic(long id) {
        if (id != 0) {
            currentTopic = ts.getTopic(id);
            currentTopic.setViews(currentTopic.getViews() + 1);
            return "topic";
        }
        return "redirect:/";
    }

    public boolean isMembersTopic(long id){
        return mc.isAdmin() || mc.getLoggedMember() != null && mc.getLoggedMember().getId() == id;
    }

    public Topic getCurrentTopic() {
        return currentTopic;
    }

    public void setCurrentTopic(Topic currentTopic) {
        this.currentTopic = currentTopic;
    }

    public long getCurrentTopicId(){
        return currentTopic.getId();
    }
}
