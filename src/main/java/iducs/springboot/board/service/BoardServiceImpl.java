package iducs.springboot.board.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import iducs.springboot.board.domain.Answer;
import iducs.springboot.board.domain.Board;
import iducs.springboot.board.domain.User;
import iducs.springboot.board.entity.AnswerEntity;
import iducs.springboot.board.entity.BoardEntity;
import iducs.springboot.board.entity.UserEntity;
import iducs.springboot.board.repository.BoardRepository;

@Service("boardService")

public class BoardServiceImpl implements BoardService {
	
	@Autowired BoardRepository repository;

	@Override
	public Board getQuestionById(long id) {
		BoardEntity entity = repository.findById(id).get();
		Board question = entity.buildDomain();
		
		// 질문 entity 객체로부터 AnswerEntity ArrayList -> Answer ArrayList
		List<Answer> answerList = new ArrayList<Answer>();
		for(AnswerEntity answerEntity : entity.getAnswers())
			answerList.add(answerEntity.buildDomain());
		question.setAnswers(answerList);
		
		return question;
	}
	
	@Override
	public List<Board> getQuestions() {
		/*
		 * 1. Repository로 부터 모든 자료를 가져와 Enitiy 리스트에 저장한다.
		 * 2. 
		 */
		List<BoardEntity> entities = repository.findAll(new Sort(Sort.Direction.DESC, "createTime"));
		
		List<Board> questions = new ArrayList<Board>();
		for(BoardEntity entity : entities) {
			Board question = entity.buildDomain();
			questions.add(question);
		}
		return questions;			
	}
	

	@Override
	public List<Board> getQuestionsByTitle(String title) {
		// TODO Auto-generated method stub
		List<BoardEntity> entities = repository.findAll(new Sort(Sort.Direction.ASC, "title"));
		
		List<Board> questions = new ArrayList<Board>();
		for(BoardEntity entity : entities) {
			Board question = entity.buildDomain();
			questions.add(question);
		}
		return questions;	
	}
	
	@Override
	public List<Board> getQuestionsByUser(String name) {
		// TODO Auto-generated method stub
		List<BoardEntity> entities = repository.findAll(new Sort(Sort.Direction.ASC, "writer"));
		
		List<Board> questions = new ArrayList<Board>();
		for(BoardEntity entity : entities) {
			Board question = entity.buildDomain();
			questions.add(question);
		}
		return questions;	
	}

	@Override
	public List<Board> getQuestionsByPage(int index, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveQuestion(Board question) {
		BoardEntity entity = new BoardEntity();
		entity.buildEntity(question);
		repository.save(entity);
		
	}

	@Override
	public void updateQuestion(Board question) {
		BoardEntity entity = new BoardEntity();
		entity.buildEntity(question);
		repository.save(entity);
		
	}

	@Override
	public void deleteQuestion(Board question) {
		BoardEntity entity = new BoardEntity();
		entity.buildEntity(question);
		repository.delete(entity);
		
	}

	

}
