package com.project.QuizApp.service;

import com.project.QuizApp.model.Question;
import com.project.QuizApp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    //GET ALL QUESTION
    public ResponseEntity<List<Question>>getAllQuestions(){
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(questionDao.findAll(), HttpStatus.BAD_REQUEST);
    }



    //GET BY CATEGORY DAO
    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.BAD_REQUEST);
    }


    //ADD DAO
    public ResponseEntity<String> addQuestion(Question question) {

        try {
            questionDao.save(question);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
        }
        //if try block fails
        return new ResponseEntity<>("failed",HttpStatus.BAD_REQUEST) ;
    }


    //UPDATE DAO
    public ResponseEntity<String> updateQuestion(Integer id, Question updatedQuestion){
        return questionDao.findById(id)
                .map(q -> {
                    q.setQuestionTitle(updatedQuestion.getQuestionTitle());
                    q.setOption1(updatedQuestion.getOption1());
                    q.setOption2(updatedQuestion.getOption2());
                    q.setOption3(updatedQuestion.getOption3());
                    q.setOption4(updatedQuestion.getOption4());
                    q.setCategory(updatedQuestion.getCategory());
                    q.setRightAnswer(updatedQuestion.getRightAnswer());
                    q.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
                    questionDao.save(q);
                    return ResponseEntity.ok("Question updated successfully");
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found"));
    }


    //DELETEDAO
    public ResponseEntity<String> deleteQuestion(Integer id){
        if(questionDao.existsById(id)){
            questionDao.deleteById(id);
            return ResponseEntity.ok("Question deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }
    }

    //SEARCH BY CATEGORY





}
