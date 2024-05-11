package com.personal.coderhack.Service;

import com.personal.coderhack.Entity.User;
import com.personal.coderhack.Exceptions.*;
import com.personal.coderhack.Repository.UserRepository;
import com.personal.coderhack.dto.Badges;
import com.personal.coderhack.dto.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        List<User> users = userRepository.findAll();
        Collections.sort(users, (User u1, User u2) ->{
            if(u1.getScore() > u2.getScore()) return 1;
            else if(u1.getScore() < u2.getScore()) return -1;
            return 0;
        });
        return users;
    }

    @Override
    public User getUserById(Long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())throw new UserNotFoundException("User not found");
        return user.get();
    }

    @Override
    public void deleteUserById(Long userId) throws UserNotFoundException{
        if(userRepository.findById(userId).isEmpty()) throw new UserNotFoundException("User not found");
        userRepository.deleteById(userId);
    }

    @Override
    public User updateUserById(Score score, Long userId) throws InvalidScoreException, UserNotFoundException {
        if(score.getScore() < 0 || score.getScore() > 100) throw new InvalidScoreException("Score must be in the range (0 , 100)");
        if(userRepository.findById(userId).isEmpty()) throw new UserNotFoundException("User does not exist");

        User userToUpdate = userRepository.findById(userId).get();
        HashSet<Badges> badges =userToUpdate.getBadgesList();

        if(score.getScore() >= 1 && score.getScore() < 30) badges.add(Badges.CODENINJA);
        else if(score.getScore() >= 30 && score.getScore() < 60) badges.add(Badges.CODECHAMP);
        else badges.add(Badges.CODEMASTER);

        userToUpdate.setScore(score.getScore());
        userToUpdate.setBadgesList(badges);

        return userRepository.save(userToUpdate);
    }

    @Override
    public User save(User user) throws InvalidUserIdException, InvalidBadgeListException, ScoreNotZeroException {
        //userId check;
        Optional<User> existingUser = userRepository.findById(user.getUserId());
        if(!existingUser.isEmpty()) throw new InvalidUserIdException("User Id already exist");

        //check  empty badge list
        if(user.getBadgesList().size() > 0) throw new InvalidBadgeListException("Badge List should be empty");

        //check for zero score
        if(user.getScore() != 0) throw new ScoreNotZeroException("Score is not zero");

        return userRepository.save(user);
    }
}
