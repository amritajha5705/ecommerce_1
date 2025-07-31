package Service;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class AuthService {

    @Autowired
    private Repository.userRepository.UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register new user
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER"); // Default role
        return userRepository.save(user);
    }

    // Optional: Check if email already exists
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // Optional: Authenticate manually (not needed if using Spring Security login)
    public Optional<Object> login(String email, String rawPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}


