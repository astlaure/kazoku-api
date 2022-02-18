package com.astlaure.kazoku.users;

import com.astlaure.kazoku.users.models.CreateUserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
    }

    public void create(CreateUserDto createUser) {
        User user = User.builder()
            .name(createUser.getName())
            .username(createUser.getEmail())
            .password(passwordEncoder.encode(createUser.getPassword()))
            .build();
        userRepository.save(user);
    }
}
