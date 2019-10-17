package com.health.lifeway.domain.users.impl;

import com.health.lifeway.domain.commons.crypt.CryptUtils;
import com.health.lifeway.domain.commons.exception.ErrorCode;
import com.health.lifeway.domain.commons.exception.LifewayError;
import com.health.lifeway.domain.commons.exception.LifewayException;
import com.health.lifeway.domain.users.User;
import com.health.lifeway.domain.users.UserBuilder;
import com.health.lifeway.domain.users.UserRepository;
import com.health.lifeway.domain.users.UserService;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final CryptUtils cryptUtils;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository, CryptUtils cryptUtils) {
        this.userRepository = userRepository;
        this.cryptUtils = cryptUtils;
    }

    @Override
    public User add(String name, String email, String password) {
        LOG.info("Storing new User [{}]", email);
        String msg = String.format("Already found a user with this email, %s", email);
        this.userRepository.findByEmail(email).ifPresent(u -> callException(msg));
        String cryptPwd = cryptUtils.crypt(password);
        User user = new UserBuilder().name(name).email(email).password(cryptPwd).build();
        return this.userRepository.save(user);
    }

    @Override
    public User update(String id, User user) {
        LOG.info("Updating user, with id = {} ", id);
        String msg = String.format("Already found a user with this id, %s", user.getId());
        return Option.of(this.user(id))
                .peek(oldUser -> validateVehicle(oldUser, user))
                .onEmpty(() -> callException(msg))
                .get();
    }

    private void validateVehicle(User oldUser, User newUser) {
        Validate.isTrue(oldUser.getId().equals(newUser.getId()),
                "Please, inform the correctly user to found");
        this.userRepository.save(newUser);
    }

    @Override
    public void remove(String id) {
        LOG.info("Removing user, with id = {} ", id);

        String msg = String.format("Not found vehicle with plate %s to remove from park", id);
        Option.of(this.user(id))
                .peek(this.userRepository::delete)
                .onEmpty(() -> callException(msg));
    }

    @Override
    public User user(String email) {
        initValidation(email);
        LOG.info("Start searching for user with email {}", email);
        return this.userRepository.findByEmail(email).orElseThrow(userNotFound(email));
    }

    @Override
    public User user(String email, String password) {
        initValidation(email);
        LOG.info("Start searching for user with email {}", email);
        User user = this.userRepository.findByEmail(email).orElseThrow(userNotFound(email));
        boolean compare = this.cryptUtils.compare(password, user.getPassword());
        if (compare) {
            return user;
        } else {
            ErrorCode code = ErrorCode.NOT_FOUND;
            String msg = "Please inform the correct password";
            throw new LifewayException(code.getCode(), msg, code.getHttpCode());
        }
    }

    private void initValidation(String email) {
        Validate.notBlank(email, "Please, inform the email for found the user");
    }

    private static Supplier<LifewayException> userNotFound(String id) {
        ErrorCode code = ErrorCode.NOT_FOUND;
        String msg = String.format("User not found with id %s informed", id);
        return () -> new LifewayException(code.getCode(), msg, code.getHttpCode());
    }

    @Override
    public List<User> list() {
        LOG.info("Start search all users");
        return Try.of(this.userRepository::findAll)
                .getOrElse(ArrayList::new);
    }

    @Override
    public List<User> listByName(String name) {
        LOG.info("Start search all users by name");
        return Try.of(() -> this.userRepository.findAllByNameIsLike(name))
                .getOrElse(ArrayList::new);
    }

    @Override
    public List<User> listByEmail(String email) {
        LOG.info("Start search all users by email");
        return null;
    }

    private static void callException(String msg) {
        LOG.info(msg);
        ErrorCode error = ErrorCode.BAD_REQUEST;
        LifewayError lifewayError = new LifewayError.Builder(error.getCode(), msg).build();
        throw new LifewayException(lifewayError, error.getHttpCode());
    }

}
