package com.oktenria.repositories;

import com.oktenria.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByPhone(String phone);

    User findByEmail(String email);
}
