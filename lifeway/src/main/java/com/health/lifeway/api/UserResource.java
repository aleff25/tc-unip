package com.health.lifeway.api;

import com.health.lifeway.domain.users.User;
import com.health.lifeway.domain.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public User searchUser(@RequestParam String email,
                           @RequestParam String password) {
        return this.userService.user(email, password);
    }

    @PostMapping
    public User store(@RequestBody User user) {
        return this.userService.add(user.getName(), user.getEmail(), user.getPassword());
    }

    @PutMapping
    public User update(@RequestParam String id, @RequestBody User user) {
        return this.userService.update(id, user);
    }

    @DeleteMapping
    public void delete(@RequestParam String id) {
        this.userService.remove(id);
    }
}
