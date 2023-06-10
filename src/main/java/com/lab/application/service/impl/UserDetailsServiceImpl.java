package com.lab.application.service.impl;

import com.lab.application.entity.SecurityUser;
import com.lab.application.entity.User;
import com.lab.application.repository.UserRepository;
import com.lab.application.security.SecurityService;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }

    public List<User> findAllUsers(String filterText){

        if(filterText == null || filterText.isEmpty()){
            return userRepository.findAll();
        } else {
            return userRepository.search(filterText);
        }
    }
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
    public void saveUser(User user) {
        if(user == null){
            Notification.show("User is null");
        }else{
            String password = user.getPassword();
            String encodedPassword = new BCryptPasswordEncoder().encode(password);
            user.setPassword(encodedPassword);
            userRepository.save(user);
        }
    }

    public void updateUser(User user) {
        if(user == null){
            Notification.show("User is null");
        }else{
            String password = user.getPassword();
            String encodedPassword = new BCryptPasswordEncoder().encode(password);
            user.setPassword(encodedPassword);
            userRepository.save(user);
        }
    }

    public void updateProfile(User user) {
        if(user == null){
            Notification.show("User is null");
        }else{
            userRepository.save(user);
        }
    }

    public User getCurrentUser() {
        Optional<User> user =  userRepository.findByUsername(SecurityService.getUsername());
        return user.orElse(null);
    }

}
