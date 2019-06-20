package iducs.springboot.board.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import iducs.springboot.board.domain.Board;
import iducs.springboot.board.domain.User;
import iducs.springboot.board.exception.ResourceNotFoundException;
import iducs.springboot.board.repository.UserRepository;
import iducs.springboot.board.service.BoardService;
import iducs.springboot.board.service.UserService;


@Controller
@RequestMapping("/questions")
public class BoardController {
	@Autowired BoardService boardService; // 의존성 주입(Dependency Injection) : 
	
	@GetMapping("")
	public String getAllUser(Model model, HttpSession session) {
		List<Board> questions = boardService.getQuestions();
		model.addAttribute("questions", questions);
		return "/questions/list"; 
	}	
	
	/*
	@GetMapping("")
	public String getUsers(Model model, HttpSession session, Long pageNo) { //@PathVariable(value = "pageNo") Long pageNo) {
		if(pageNo == null)
			pageNo = new Long(1);
		model.addAttribute("users", userService.getUsers(pageNo));
		if(session.getAttribute("user") == null) {
			System.out.println("id error : ");
			return "redirect:/users/login-form";
		}
		return "/users/list";
	}	
	*/
	
	
	//제목 기준 정렬
	@GetMapping("/title")
	public String getAllUserByTitle(Model model, HttpSession session, String title) {
		List<Board> questions = boardService.getQuestionsByTitle(title);
		model.addAttribute("questions", questions);
		return "/questions/list"; 
	}	
	
	@PostMapping("") 
	// public String createUser(Question question, Model model, HttpSession session) {
	public String createUser(String title, String contents, Model model, HttpSession session) {
		User sessionUser = (User) session.getAttribute("user");
		Board newQuestion = new Board(title, sessionUser, contents);		
		// Question newQuestion = new Question(question.getTitle(), writer, question.getContents());	
		boardService.saveQuestion(newQuestion);
		return "redirect:/questions"; // get 방식으로  리다이렉션 - Controller를 통해 접근
	}
	// 본인것만 수정 삭제 가능하도록 비교
	@GetMapping("/{id}")
	public String getQuestionById(@PathVariable(value = "id") Long id, Model model, HttpSession session) {
		User sessionUser = (User)session.getAttribute("user");
		Board question = boardService.getQuestionById(id);
		User writer = question.getWriter();
		if(sessionUser.equals(writer))
			model.addAttribute("same", "같다");
		model.addAttribute("question", question);
		return "/questions/info";
	}
	@GetMapping("/{id}/form")
	public String getUpdateForm(@PathVariable(value = "id") Long id, Model model) {
		Board question = boardService.getQuestionById(id);
		model.addAttribute("question", question);
		return "/questions/edit2";
	}
	/*
	@GetMapping("/form")
	public String getCreateForm(Model model) {
		return "/questions/register";
	}*/
	
	@PutMapping("/{id}")
	public String updateQuestionById(@PathVariable(value = "id") Long id, @Valid Board formQuestion, String title, String contents, Model model) {
		Board question = boardService.getQuestionById(id);
		
		question.setContents(formQuestion.getContents());
		question.setTitle(formQuestion.getTitle());
		
		boardService.updateQuestion(question);		
		return "redirect:/questions/" + id;
	}
	@DeleteMapping("/{id}")
	public String deleteQuestionById(@PathVariable(value = "id") Long id, Model model) {
		Board question = boardService.getQuestionById(id);
		boardService.deleteQuestion(question);
		model.addAttribute("userId", question.getWriter().getUserId());
		return "redirect:/questions";
	}
}
