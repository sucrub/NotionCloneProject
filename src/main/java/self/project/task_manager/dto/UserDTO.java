package self.project.task_manager.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private String role;
    private boolean isActive;
    private String accessToken;
}
