package sk.tsystems.forum.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tsystems.forum.entity.Member;
import sk.tsystems.forum.entity.Rank;
import sk.tsystems.forum.service.member.MemberService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MemberController {

    @Autowired
    private MemberService ms;

    private Member loggedMember;

    @RequestMapping("/loginmember")
    public String login(String username, String password, Model model) {
        if(username != null && password != null) {
            if(username.equals("") && password.equals("")) {
                return "loginmember";
            }
            else if(username.equals("") || password.equals("")) {
                model.addAttribute("message", "Invalid login!");
            }

            loggedMember = ms.login(username, password);

            if(loggedMember != null) {
                if(loggedMember.getRank()==Rank.BANNED){
                    loggedMember = null;
                    model.addAttribute("message", "You are banned!");
                    return "loginmember";
                }
                return "redirect:/";
            }
            else {
                model.addAttribute("message", "Invalid login!");
                return "loginmember";
            }
        }

        if(username == null && password != null) {
            model.addAttribute("message", "Invalid login!");
        }
        else if(username != null) {
            model.addAttribute("message", "Invalid login!");
        }

        return "loginmember";
    }

    @RequestMapping("/registermember")
    public String register(Member member, Model model) {
        if(member.getEmail() != null && member.getUsername() != null && member.getPassword() != null) {
            boolean invalidForm = false;

            if(member.getEmail().equals("") || ms.emailExists(member.getEmail())) {
                invalidForm = true;
                model.addAttribute("emailMessage", "Invalid Email!");
            }

            if(member.getUsername().equals("") || ms.usernameExists(member.getUsername())) {
                invalidForm = true;
                model.addAttribute("usernameMessage", "Invalid Username!");
            }

            if(member.getPassword().equals("")) {
                invalidForm = true;
                model.addAttribute("passwordMessage", "Invalid Password!");
            }
            else if (!member.validatePassword()) {
                invalidForm = true;
                model.addAttribute("passwordMessage", "Password doesn't match!");
            }

            if(invalidForm) {
                return "registermember";
            }
            else {
                member.setRank(Rank.GENERAL);
                ms.register(member);
                loggedMember = ms.login(member.getUsername(), member.getPassword());
                return "redirect:/";
            }
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
        return "realadminpanel";
    }

    @RequestMapping("/promotemember")
    public String promoteMember(long id){
        Member m = ms.getMember(id);
        m.setRank(Rank.ADMIN);
        return "realadminpanel";
    }

    @RequestMapping("/deletemember")
    public String deleteMember(long id){
        Member m = ms.getMember(id);
        m.setRank(Rank.DELETED);
        return "realadminpanel";
    }

    @RequestMapping("/accessadminpanel")
    public String accessAdminPanel(){
        return "realadminpanel";
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

    public boolean isAdmin() {
        return loggedMember != null && (loggedMember.getRank() == Rank.ADMIN);
    }

    public boolean isMember() {
        return loggedMember != null && (loggedMember.getRank() == Rank.ADMIN || loggedMember.getRank() == Rank.GENERAL);
    }

    public boolean isDeleted(Member member){
        return member.getRank() == Rank.DELETED;
    }

    public boolean isBanned(long id){
        return ms.getMember(id).getRank() == Rank.BANNED;
    }
}
