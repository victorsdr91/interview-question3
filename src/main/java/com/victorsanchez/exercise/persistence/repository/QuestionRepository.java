package com.victorsanchez.exercise.persistence.repository;

import com.victorsanchez.exercise.persistence.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionRepository
        extends JpaRepository<QuestionEntity, Long> {

}
