package sk.tsystems.forum.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tsystems.forum.entity.Category;
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

    private Category currentCategory;

    private Model model;

    @RequestMapping("/addcategory")
    public String addCategory(Category category){
        ss.addCategory(category);
        return "redirect:/";
    }

    @RequestMapping("/deletecategory")
    public String deleteCategory(long id){
        if(id != 0){
            ss.deleteCategory(id);
        }
        return "redirect:/";
    }

    @RequestMapping("/addsection")
    public String addSection(Section section){
        section.setMember(mc.getLoggedMember());
        section.setCategory(currentCategory);
        ss.addSection(section);
        return "adminpanel";
    }

    @RequestMapping("/editsection")
    public String editSection(long id){
        if (id != 0){
            currentSection = ss.getSection(id);
            return "editsection";
        }
        return "redirect:/";
    }

    @RequestMapping("/updatesection")
    public String updateSection(String name, String description){
        currentSection.setName(name);
        currentSection.setDescription(description);
        ss.updateSection(currentSection);
        return "adminpanel";
    }

    @RequestMapping("/deletesection")
    public String deleteSection(long id){
        if(id != 0){
            ss.deleteSection(id);
        }
        return "adminpanel";
    }

    @RequestMapping("/section")
    public String accessSection(long id){
        if(id != 0) {
            currentSection = ss.getSection(id);
            return "section";
        }
        return "redirect:/";
    }

    @RequestMapping("/category")
    public String accessCategory(long id, Model model){
        if(id != 0) {
            currentCategory = ss.getCategory(id);
            model.addAttribute("category", "Current category:");
            return "adminpanel";
        }
        return "redirect:/";
    }

    public boolean isCategoryNull(){
        return (currentCategory == null);
    }

    public boolean isCategoryNotNull(){
        return (currentCategory != null);
    }

    public String getCurrentCategoryName(){
        return currentCategory.getName();
    }

    public Section getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(Section currentSection) {
        this.currentSection = currentSection;
    }

    public Category getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }
}
