package org.unibl.etf.forum.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.unibl.etf.forum.exceptions.InvalidUsernameException;
import org.unibl.etf.forum.models.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new InvalidUsernameException("Ne postoji korisnik."));
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException{
        return userRepository.findByEmail(email).orElseThrow(() -> new InvalidUsernameException("Ne postoji korisnik."));
    }

    public List<UserEntity> findAllInactiveUsers() {
        return userRepository.findAllByStatusFalse();
    }
}