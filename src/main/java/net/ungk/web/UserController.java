package net.ungk.web;

import java.awt.List;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.ungk.domain.User;
import net.ungk.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		
		if(user == null) {
			System.out.println("login faile! userId not correct");
			return "redirect:/users/loginForm";
		}
		if(!user.matchPassword(password)) {
			System.out.println("login faile! password not correct!");
			return "redirect:/users/loginForm";
		}
		
		System.out.println("login Success!");
		session.setAttribute(HttpSessionUtils.User_SESSION_KEY, user);
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.User_SESSION_KEY);
		return "redirect:/";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/form")
	public String form() {
		return "user/form";
	}
	
	@PostMapping("")
	public String create(User user) {
		System.out.println("user : " + user);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "user/list";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		if (!sessionedUser.matchId(id)) {
			throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
		}
		
		User user = userRepository.findById(id).get();
		model.addAttribute("user", user);
		return "user/updateForm";
	}
	
	@PostMapping("/{id}")
	public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
		
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);

		if (!sessionedUser.matchId(id)) {
			throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
		}
		
		//사용자가 있으면 update, 없으면 insert를 해주는 방식으로 동작한다.
		User user = userRepository.findById(id).get();
		user.update(updatedUser);
		userRepository.save(user);
//		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY,user);
		return "redirect:/users";
	}
	
}