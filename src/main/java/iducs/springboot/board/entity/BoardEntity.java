package iducs.springboot.board.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import iducs.springboot.board.domain.Board;
import iducs.springboot.board.domain.User;

@Entity
@Table(name = "board")
public class BoardEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id; // database에서 sequence number, primary key 역할
	
	private String title; 
	
	@ManyToOne
	@JoinColumn(name="fk_question_writer")
	private UserEntity writer;	

	@OneToMany(mappedBy="question", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy("createTime DESC")
	private List<AnswerEntity> answers = new ArrayList<AnswerEntity>();
	
	@Lob
	private String contents;
	private LocalDateTime createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserEntity getWriter() {
		return writer;
	}
	public void setWriter(UserEntity writer) {
		this.writer = writer;
	}
	
	//answer관련 작업 내역
	public List<AnswerEntity> getAnswers(){
		return answers;
	}
	public void setAnswers(List<AnswerEntity> answers) {
		this.answers = answers;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	public Board buildDomain() {
		Board question = new Board();
		question.setId(id);
		question.setTitle(title);
		question.setWriter(writer.buildDomain());
		question.setContents(contents);
		question.setCreateTime(createTime);
		return question;
	}
	public void buildEntity(Board question) {
		UserEntity userEntity = new UserEntity();
		userEntity.buildEntity(question.getWriter());
		
		id = question.getId();
		title = question.getTitle();
		writer = userEntity;
		contents = question.getContents();
		createTime = question.getCreateTime();
	}
}