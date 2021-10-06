package com.alhessan.testproject.web.security;

import com.abolkog.springboot.tut.error.NotFoundException;
import com.alhessan.testproject.web.User;
import com.alhessan.testproject.web.UserRepository;
import com.alhessan.testproject.web.error.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Khalid Elshafie <abolkog@gmail.com>
 * @Created 08/10/2018 9:20 PM.
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return new User("khalid",passwordEncoder().encode("password"), AuthorityUtils.NO_AUTHORITIES);
        User user = userRepository.findByUser_name(username);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return user;
    }

    public void save(User user) {
        String idForEncode = "bcrypt";
        Map encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());

        PasswordEncoder passwordEncoder =
                new DelegatingPasswordEncoder(idForEncode, encoders);


        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        this.userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
