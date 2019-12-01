package net.ungk.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.ungk.domain.Question;
import net.ungk.domain.QuestionRepository;
import net.ungk.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/form")
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "user/loginForm";
		}
		return "qna/form";
	}

	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "user/loginForm";
		}

		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute("question", questionRepository.findById(id).get());
		return "qna/show";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		
		try {
			Question question = questionRepository.findById(id).get();
			hasPermission(session, question);
			model.addAttribute("question", question);
			return "qna/updateForm";
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "user/loginForm";
		}
		
//		if (!HttpSessionUtils.isLoginUser(session)) {
//			return "user/loginForm";
//		}
//		User loginUser = HttpSessionUtils.getUserFromSession(session);
//		Question question = questionRepository.findById(id).get();
//		if (!question.isSameWriter(loginUser)) {
//			return "user/loginForm";
//		}
//		model.addAttribute("question", question);
//		return "qna/updateForm";
	}

	private boolean hasPermission(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			throw new IllegalStateException("로그인이 필요합니다.");
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!question.isSameWriter(loginUser)) {
			throw new IllegalStateException("자신이 쓴 글만 수정,삭제할 수 있습니다.");
		}
		return true;
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
		try {
			Question question = questionRepository.findById(id).get();
			hasPermission(session, question);
			question.update(title, contents);
			questionRepository.save(question);
			return String.format("redirect:/questions/%d", id);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "user/loginForm";
		}
		
//		if (!HttpSessionUtils.isLoginUser(session)) {
//			return "users/loginForm";
//		}
//		User loginUser = HttpSessionUtils.getUserFromSession(session);
//		Question question = questionRepository.findById(id).get();
//		if (!question.isSameWriter(loginUser)) {
//			return "users/loginForm";
//		}
//
//		question.update(title, contents);
//		questionRepository.save(question);
//		return String.format("redirect:/questions/%d", id);
	}

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id, HttpSession session, Model model) {
		try {
			Question question = questionRepository.findById(id).get();
			hasPermission(session, question);
			questionRepository.delete(question);
			return "redirect:/";
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "user/loginForm";
		}
		
//		if (!HttpSessionUtils.isLoginUser(session)) {
//			return "user/loginForm";
//		}
//		User loginUser = HttpSessionUtils.getUserFromSession(session);
//		Question question = questionRepository.findById(id).get();
//		if (!question.isSameWriter(loginUser)) {
//			return "user/loginForm";
//		}
//
//		questionRepository.delete(question);
//		return "redirect:/";
	}
}