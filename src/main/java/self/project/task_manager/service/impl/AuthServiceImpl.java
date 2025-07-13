package self.project.task_manager.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.SecretKey;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.project.task_manager.dto.UserDTO;
import self.project.task_manager.model.Role;
import self.project.task_manager.model.User;
import self.project.task_manager.repository.UserRepository;
import self.project.task_manager.service.AuthService;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final SecretKey jwtSecretKey;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, SecretKey jwtSecretKey) {
        this.userRepository = userRepository;
        this.jwtSecretKey = jwtSecretKey;
    }

    @Override
    public UserDTO register(User user) {
        Optional<User> userOpt = userRepository.findByEmail(user.getEmail());
        if(userOpt.isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }
        // Encrypt password using JWT
        String encryptedPassword = Jwts.builder()
                .setSubject(user.getPassword())
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
        user.setPassword(encryptedPassword);
        user.setActive(true);
        user.setRole(Role.USER.getRoleName());
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }
        User user = userOpt.get();
        // Encrypt the input password to compare with stored encrypted password
        String encryptedInputPassword = Jwts.builder()
                .setSubject(password)
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
        if (!user.getPassword().equals(encryptedInputPassword)) {
            throw new RuntimeException("Invalid email or password");
        }
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        // Generate access token for the user
        String accessToken = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
        userDTO.setAccessToken(accessToken);
        return userDTO;
    }
}
