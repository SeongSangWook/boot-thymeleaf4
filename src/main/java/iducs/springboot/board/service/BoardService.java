package iducs.springboot.board.service;

import java.util.List;

import iducs.springboot.board.domain.Board;

public interface BoardService {
	Board getQuestionById(long id); // primary key인 id 값을 가진 질문 조회
	//Board getBoardById(long id); // primary key인 id 값을 가진 질문 조회
	
	List<Board> getQuestions(); // 모든 질문  조회
	//List<Board> getBoards(); // 모든 질문  조회
	
	List<Board> getQuestionsByTitle(String title); // name으로 조회
	//List<Board> getBoardsByTitle(String title); // name으로 조회
	
	List<Board> getQuestionsByUser(String name); // name으로 조회
	//List<Board> getBoardsByUser(String name); // name으로 조회
	
	List<Board> getQuestionsByPage(int index, int size); // 페이지로 조회
	//List<Board> getBoardsByPage(int index, int size); // 페이지로 조회
	
	
	void saveQuestion(Board question); // 질문 생성
	
	void updateQuestion(Board question); // 질문 수정
	
	void deleteQuestion(Board question); // 질문 삭제
	
	/*
	void saveBoard(Board board); // 질문 생성
	
	void updateBoard(Board board); // 질문 수정
	
	void deleteBoard(Board board); // 질문 삭제
	*/
}
