package com.oktenria.services;

import com.oktenria.entities.User;
import com.oktenria.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteUser(ObjectId id) {
        userRepository.deleteById(id);
    }

    public void updateUser(ObjectId id, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setName(updatedUser.getName());
            existingUser.setImage(updatedUser.getImage());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setSubscribe(updatedUser.getSubscribe());

            userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User with id " + id + " does not exist");
        }
    }

    public User getUserByEmailOrPhone(String emailOrPhone) {
        if (emailOrPhone.contains("@")) {
            return userRepository.findByEmail(emailOrPhone);
        } else {
            return userRepository.findByPhone(emailOrPhone);
        }
    }
}
