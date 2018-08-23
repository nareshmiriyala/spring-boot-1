package com.agility.spring.controllers;

import com.agility.spring.dto.UserDTO;
import com.agility.spring.models.User;
import com.agility.spring.models.UserDAO;
import com.agility.spring.response.ApiResponse;
import com.agility.spring.response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

  private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

  @Autowired
  private UserDAO userDAO;

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public User create(@RequestParam("email") String email,
                       @RequestParam("name") String name) {
    User user;
    try {
      user = new User(email, name);
      userDAO.save(user);
    } catch (Exception ex) {
      return null;
    }
    return user;
  }

  @RequestMapping(value ="/{userId}", method = RequestMethod.GET)
  public User getUser(@PathVariable("userId") long id) {
    try {
      User user = userDAO.findById(id).orElse(null);
      return user;
    } catch (Exception ex) {
      return null;
    }
  }

  @RequestMapping(value ="/{id}", method = RequestMethod.GET,
  consumes = "application/json;version=2")
  public String getUserV2(@PathVariable("userId") long id) {
    try {
      User user = userDAO.findById(id).orElse(null);
      return "User have email: " + user.getEmail();
    } catch (Exception ex) {
      return null;
    }
  }

  @RequestMapping(value ="/{userId}", method = RequestMethod.GET,
  headers = {"X-API-Version=3"})
  public String getUserV3(@PathVariable("userId") long id) {
    return "This is api version 3 get user by id ";
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public List<User> getAll() {
    List<User> users = new ArrayList<>();
    userDAO.findAll().forEach(users::add);
    return users;
  }

  @RequestMapping(method = RequestMethod.POST)
  public ApiResponse addUser(@RequestBody UserDTO userDTO) {
    logger.info("UserDTO: " + userDTO);
    User user = new User(userDTO);
    logger.info("User: " + user);
    userDAO.save(user);
    return new ApiResponse(Status.OK, user);
  }

}
