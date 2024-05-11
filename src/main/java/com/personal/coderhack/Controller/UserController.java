package com.personal.coderhack.Controller;

import com.personal.coderhack.Entity.User;
import com.personal.coderhack.Exceptions.*;
import com.personal.coderhack.Service.UserServiceImpl;
import com.personal.coderhack.dto.ErrorMessageDto;
import com.personal.coderhack.dto.Score;
import com.personal.coderhack.dto.SuccessMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users = userServiceImpl.getAllUser();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId){
        try{
            User user = userServiceImpl.getUserById(userId);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }catch(UserNotFoundException e){
            log.error(e.getMessage());
            return new ResponseEntity(new ErrorMessageDto("User not found"),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<User> createNewUser(@RequestBody User user){
        try{
            User savedUser = userServiceImpl.save(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        }catch(InvalidUserIdException e){
            log.error(e.getMessage());
            return new ResponseEntity(new ErrorMessageDto("User Id already exist"), HttpStatus.BAD_REQUEST);
        }catch(InvalidBadgeListException e){
            log.error(e.getMessage());
            return new ResponseEntity(new ErrorMessageDto("Badge List should be empty"), HttpStatus.BAD_REQUEST);
        }catch(ScoreNotZeroException e){
            log.error(e.getMessage());
            return new ResponseEntity(new ErrorMessageDto("Score Must be zero"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUserById(@RequestBody Score score, @PathVariable Long userId){
        try{
            User updatedUser = userServiceImpl.updateUserById(score, userId);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }catch(InvalidScoreException e){
            log.error(e.getMessage());
            return new ResponseEntity(new ErrorMessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch(UserNotFoundException e){
            log.error(e.getMessage());
            return new ResponseEntity(new ErrorMessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUserById(@PathVariable Long userId){
        try{
            userServiceImpl.deleteUserById(userId);
            return new ResponseEntity(new SuccessMessage("user deleted successfully"),HttpStatus.OK);
        }catch(UserNotFoundException e){
            log.error(e.getMessage());
            return new ResponseEntity(new ErrorMessageDto("User not found"),HttpStatus.NOT_FOUND);
        }

    }

}
