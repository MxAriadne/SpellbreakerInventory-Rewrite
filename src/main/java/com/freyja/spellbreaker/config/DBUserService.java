package com.freyja.spellbreaker.config;

import com.freyja.spellbreaker.entities.User;
import com.freyja.spellbreaker.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 * The goal of this class is to overwrite the SpringSecurity user data service and implement our own
 * using the database.
 */
@Service
public class DBUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not present"));
    }
    public void createUser(UserDetails user) {
        userRepository.save((User) user);
    }
}