package com.personal.coderhack.Service;

import com.personal.coderhack.Entity.User;
import com.personal.coderhack.Exceptions.*;
import com.personal.coderhack.dto.Score;

import java.util.List;

public interface UserService {

    List<User> getAllUser();
    User getUserById(Long userId) throws UserNotFoundException;
    void deleteUserById(Long userId) throws UserNotFoundException;
    User updateUserById(Score score, Long userId) throws InvalidScoreException, UserNotFoundException;
    User save(User user) throws InvalidUserIdException, InvalidBadgeListException, ScoreNotZeroException;
}
