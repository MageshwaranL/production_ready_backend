package com.backend.controller;

import com.backend.entity.User;
import com.backend.exception.UserNotFoundException;
import com.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @PostMapping("/add")
    private ResponseEntity<User> addUser(@RequestBody User user)
    {
      User response=userRepository.save(user);
      return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> allUsers()
    {
      return new ResponseEntity<>(userRepository.findAll(),HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User updatedUser)
    {
        User user=userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User "+id+" Not Found"));
        user.setName(updatedUser.getName());
        User updatedUser1=userRepository.save(user);
        return new ResponseEntity<>(updatedUser1,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id)
    {
        User user=userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User "+id+" Not Found"));
        userRepository.deleteById(id);
        return new ResponseEntity<>(user,HttpStatus.ACCEPTED);

    }
}
