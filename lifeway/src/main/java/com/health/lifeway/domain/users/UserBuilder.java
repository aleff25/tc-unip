package com.health.lifeway.domain.users;

public class UserBuilder {

    String name;
    String cpf;
    String email;
    String password;

    public UserBuilder() {
    }

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        return new User(this);
    }
}
