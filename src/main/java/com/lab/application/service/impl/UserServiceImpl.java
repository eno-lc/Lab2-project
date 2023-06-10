package com.lab.application.service.impl;

import com.lab.application.repository.UserRepository;
import com.lab.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public long getUsersCount() {
        return userRepository.count();
    }

}
