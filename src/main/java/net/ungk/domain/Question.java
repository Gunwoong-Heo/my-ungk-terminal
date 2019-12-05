package net.ungk.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.ungk.web.HttpSessionUtils;

@Entity
public class Question extends AbstractEntity{

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	@JsonProperty
	private User writer;
	
	@JsonProperty
	private String title;
	
	@Lob
	@JsonProperty
	private String contents;
	
	@JsonProperty
	private Integer countOfAnswers = 0;
	
	@OneToMany(mappedBy = "question")
//	@OrderBy("createDate DESC")
	@OrderBy("id DESC")
	private List<Answer> answers;
	
	public Question() {

	}

	public Question(User writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}
	
	

	public void update(String title, String contents) {
		// TODO Auto-generated method stub
		this.title = title;
		this.contents = contents;
	}

	public boolean isSameWriter(User loginUser) {
		// TODO Auto-generated method stub
		return this.writer.equals(loginUser);
	}

	public void addAnswer() {
		// TODO Auto-generated method stub
		this.countOfAnswers += 1;
	}
	
	public void deleteAnswer() {
		// TODO Auto-generated method stub
		this.countOfAnswers -= 1;
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + (int) (id ^ (id >>> 32));
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Question other = (Question) obj;
//		if (id != other.id)
//			return false;
//		return true;
//	}

	
}