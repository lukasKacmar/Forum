package sk.tsystems.forum;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tsystems.forum.service.member.MemberService;
import sk.tsystems.forum.service.member.MemberServiceJPA;
import sk.tsystems.forum.service.post.PostService;
import sk.tsystems.forum.service.post.PostServiceJPA;
import sk.tsystems.forum.service.section.SectionService;
import sk.tsystems.forum.service.section.SectionServiceJPA;
import sk.tsystems.forum.service.topic.TopicService;
import sk.tsystems.forum.service.topic.TopicServiceJPA;

@SpringBootApplication
@Configuration
public class SpringMain {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringMain.class, args); 
	}
	
//	@Bean
//	public CommandLineRunner runner(MainConsole mainConsole) {
////	Full anonymous implementation of CommandLineRunner	
////		return new CommandLineRunner() {
////			@Override
////			public void run(String... arg0) throws Exception {
////				mainConsole.run();
////			}
////		};
//		
////	Implementation using lambda with typed parameter 
////		return (String... args0) -> {
////			mainConsole.run();
////		};
//		
////	Implementation with clean lambda
//		return (args0) -> mainConsole.run();
//	}
	
//	@Bean
//	public MainConsole xy() {
//		return new MainConsole();
//	}
	@Bean
	public MemberService ms(){
		return new MemberServiceJPA();
	}
	@Bean
	public SectionService ss(){
		return new SectionServiceJPA();
	}
	@Bean
	public TopicService ts(){
		return new TopicServiceJPA();
	}
	@Bean
	public PostService ps(){
		return new PostServiceJPA();
	}
}
