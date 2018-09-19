package sk.tsystems.forum.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tsystems.forum.entity.Member;
import sk.tsystems.forum.entity.Rank;
import sk.tsystems.forum.entity.Section;
import sk.tsystems.forum.service.member.MemberService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MemberController {

    @Autowired
    private MemberService ms;

    private Member loggedMember;

    @RequestMapping("/loginmember")
    public String login(String username, String password, Model model) {
        if (username != null && password != null) {
            loggedMember = ms.login(username, password);
            if(loggedMember.getRank()==Rank.BANNED){
                loggedMember = null;
                model.addAttribute("message", "You are banned!");
                return "loginmember";
            }
            return "redirect:/";
        }
        return "loginmember";
    }

    @RequestMapping("/registermember")
    public String register(Member member) {
        if (member.getUsername() != null) {
            member.setRank(Rank.ADMIN);
            ms.register(member);
            loggedMember = ms.login(member.getUsername(), member.getPassword());
            return "redirect:/";
        }
        return "registermember";
    }

    @RequestMapping("/logoutmember")
    public String logoutMember(){
        loggedMember = null;
        return "redirect:/";
    }

    @RequestMapping("/banmember")
    public String banMember(long id){
        Member m = ms.getMember(id);
        if(m.getRank().equals(Rank.GENERAL) || m.getRank().equals(Rank.ADMIN)) {
            m.setRank(Rank.BANNED);
        } else{
            m.setRank(Rank.GENERAL);
        }
        return "adminpanel";
    }

    @RequestMapping("/promotemember")
    public String promoteMember(long id){
        Member m = ms.getMember(id);
        m.setRank(Rank.ADMIN);
        return "adminpanel";
    }

    @RequestMapping("/deletemember")
    public String deleteMember(long id){
        if(id != 0){
            ms.deleteMember(id);
        }
        return "adminpanel";
    }

    public Member getLoggedMember() {
        return loggedMember;
    }

    public void setLoggedMember(Member loggedMember) {
        this.loggedMember = loggedMember;
    }

    public boolean isLogged(){
        return loggedMember != null;
    }

    public String getLoggedUsername(){
        if(isLogged()){
            return loggedMember.getUsername();
        }
        return "anonymous";
    }

    public boolean isAdmin(){
        if(loggedMember!=null) {
            return (loggedMember.getRank() == Rank.ADMIN);
        }
        return false;
    }

    public boolean isMember(){
        if(loggedMember!=null) {
            if (loggedMember.getRank() == Rank.ADMIN){
                return true;
            }
            if (loggedMember.getRank() == Rank.GENERAL){
                return true;
            }
        }
        return false;
    }
}
