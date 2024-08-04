package com.anuj.ecommerce.service;

import com.anuj.ecommerce.exception.UserException;
import com.anuj.ecommerce.model.User;

public interface UserService {

    public User findUsetById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

}
