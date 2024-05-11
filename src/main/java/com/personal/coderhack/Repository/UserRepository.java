package com.personal.coderhack.Repository;

import com.personal.coderhack.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
}
