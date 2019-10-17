package com.health.lifeway.domain.users;

import java.util.List;

public interface UserService {

    User add(String name, String email, String password);

    User update(String id, User user);

    void remove(String id);

    User user(String email);

    User user(String email, String password);

    List<User> list();

    List<User> listByName(String name);

    List<User> listByEmail(String email);
}
