package com.personal.coderhack.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.personal.coderhack.Entity.User;
import com.personal.coderhack.Exceptions.*;
import com.personal.coderhack.Repository.UserRepository;
import com.personal.coderhack.dto.Badges;
import com.personal.coderhack.dto.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private HashSet<Badges> badgelist;

    @BeforeEach
    public void setup(){
        badgelist = new HashSet<>();
        badgelist.add(Badges.CODECHAMP);
        badgelist.add(Badges.CODEMASTER);
        badgelist.add(Badges.CODENINJA);
    }

    @Test
    public void testGetAllUser() {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "User1", 50L));
        users.add(new User(2L, "User2", 30L));
        users.add(new User(3L, "User3", 70L));

        when(userRepository.findAll()).thenReturn(users);

        List<User> sortedUsers = userService.getAllUser();

        assertEquals(3, sortedUsers.size());
        assertEquals(2L, sortedUsers.get(0).getUserId());
        assertEquals(1L, sortedUsers.get(1).getUserId());
        assertEquals(3L, sortedUsers.get(2).getUserId());
    }

    @Test
    public void testGetUserById_UserFound() throws UserNotFoundException {
        User user = new User(1L, "User1", 50);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User retrievedUser = userService.getUserById(1L);

        assertNotNull(retrievedUser);
        assertEquals(1L, retrievedUser.getUserId());
    }

    @Test
    public void testGetUserById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    public void testDeleteUserById_UserFound() throws UserNotFoundException {
        User user = new User(1L, "User1", 50);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.deleteUserById(1L));
    }

    @Test
    public void testDeleteUserById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(1L));
    }
    

    @Test
    public void testUpdateUserById_InvalidScore() {
        User user = new User(1L, "User1", 50);
        Score score = new Score(150L);
        assertThrows(InvalidScoreException.class, () -> userService.updateUserById(score, 1L));
    }

    @Test
    public void testUpdateUserById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Score score = new Score(75L);
        assertThrows(UserNotFoundException.class, () -> userService.updateUserById(score, 1L));
    }


    @Test
    public void testSave_ExistingUserId() {
        User user = new User(1L, "User1", 0);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(InvalidUserIdException.class, () -> userService.save(user));
    }


    @Test
    public void testSave_NonEmptyBadgeList() {
        User user = new User(1L, "User1", 0);
        HashSet<Badges> badges = new HashSet<>();
        badges.add(Badges.CODENINJA);
        user.setBadgesList(badges);

        assertThrows(InvalidBadgeListException.class, () -> userService.save(user));
    }

}
