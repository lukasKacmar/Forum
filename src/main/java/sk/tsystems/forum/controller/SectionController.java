package sk.tsystems.forum.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tsystems.forum.entity.Section;
import sk.tsystems.forum.service.section.SectionService;
import sk.tsystems.forum.service.topic.TopicService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SectionController {

    @Autowired
    private SectionService ss;

    @Autowired
    private TopicService ts;

    @Autowired
    private MemberController mc;

    private Section currentSection;

    private Model model;

    @RequestMapping("/addsection")
    public String addSection(Section section){
        if(mc.getLoggedMember() != null) {
            section.setMember(mc.getLoggedMember());
            ss.addSection(section);
        }
        return "redirect:/";
    }

    @RequestMapping("/section")
    public String accessSection(long id){
        if(id != 0) {
            currentSection = ss.getSection(id);
            return "section";
        }
        return "redirect:/";
    }

    public Section getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(Section currentSection) {
        this.currentSection = currentSection;
    }
}
