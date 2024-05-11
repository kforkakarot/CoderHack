package com.personal.coderhack.Entity;

import com.personal.coderhack.dto.Badges;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private Long userId;
    private String username;
    private Long score;
    private HashSet<Badges> badgesList;

    public User(Long userId, String username, long score) {
        this.userId = userId;
        this.username = username;
        this.score = score;
    }

    public User(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
