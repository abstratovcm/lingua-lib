package com.penguin.shiro;

import com.penguin.model.User;
import com.penguin.repository.UserRepository;

import javax.naming.InitialContext;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRealm implements Realm {

    private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);

    private UserRepository userRepository;

    public UserRealm() {
        try {
            InitialContext ic = new InitialContext();
            userRepository = (UserRepository) ic.lookup("java:module/UserRepository");
        } catch (Exception e) {
            logger.error("Error initializing UserRepository", e);
        }
    }

    @Override
    public String getName() {
        return "UserRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
        String username = userPassToken.getUsername();
        User user = null;
        try {
            user = userRepository.findUserByUsername(username);
        } catch (Exception e) {
            logger.error("Error occurred when finding the user by username", e);
        }

        if (user == null) {
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }

        return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
    }
}
