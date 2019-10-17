package com.health.lifeway.domain.commons.crypt;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.health.lifeway.domain.users.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Component
public class CryptPassword {

    public CryptPassword() {
    }

    public boolean verifyPwd(String password, User user) {
        byte[] bcryptHashBytes = BCrypt.withDefaults().hash(6, password.getBytes(StandardCharsets.UTF_8));
        BCrypt.Result result = BCrypt.verifyer().verify(user.getPassword().toCharArray(), bcryptHashBytes);
        return result.verified;
    }

    public byte[] hashPwd(String password) {
        return BCrypt.with(new SecureRandom()).hash(6, password.getBytes(StandardCharsets.UTF_8));
    }
}
