package net.ungk.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long>{
	//구현체를 구현하는 부분은 framework가 해줌
}