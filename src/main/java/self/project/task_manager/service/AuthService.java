package self.project.task_manager.service;

import self.project.task_manager.dto.UserDTO;
import self.project.task_manager.model.User;

public interface AuthService {
    UserDTO register(User user);

    UserDTO login(String email, String password);
}
