package sk.tsystems.forum.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
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
    public String login(String username, String password) {
        if (username != null && password != null) {
            loggedMember = ms.login(username, password);
            return "redirect:/";
        }
        return "loginmember";
    }

    @RequestMapping("/registermember")
    public String register(Member member) {
        if (member.getUsername() != null) {
            member.setRank(Rank.GENERAL);
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
}
