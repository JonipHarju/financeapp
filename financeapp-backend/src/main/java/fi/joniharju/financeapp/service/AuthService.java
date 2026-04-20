package fi.joniharju.financeapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fi.joniharju.financeapp.dto.RegisterRequest;
import fi.joniharju.financeapp.entity.AppUser;
import fi.joniharju.financeapp.repository.AppUserRepository;

@Service
public class AuthService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        if (appUserRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }
        if (appUserRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        AppUser user = new AppUser(request.getUsername(), hashedPassword, request.getEmail());
        appUserRepository.save(user);
    }

}
