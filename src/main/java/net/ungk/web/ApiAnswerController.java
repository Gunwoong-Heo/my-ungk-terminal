package net.ungk.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ungk.domain.Answer;
import net.ungk.domain.AnswerRepository;
import net.ungk.domain.Question;
import net.ungk.domain.QuestionRepository;
import net.ungk.domain.Result;
import net.ungk.domain.User;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@PostMapping("")
	public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}

		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(questionId).get();
		Answer answer = new Answer(loginUser, contents, question);
		question.addAnswer();
		return answerRepository.save(answer);
	}

	@PostMapping("/{id}/delete")
	public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인한 유저가 아닙니다.");
		}

		Answer answer = answerRepository.findById(id).get();
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!answer.isSameWriter(loginUser)) {
			return Result.fail("자신의 글만 삭제할 수 있습니다.");
		}
		Question question = questionRepository.findById(questionId).get();
		question.deleteAnswer();
		//하단 코드를 두줄 위로 올리면, Question테이블의 countOfAnswer숫자가 줄어들지 않는다. 
		answerRepository.delete(answer);
		return Result.ok();
	}
}