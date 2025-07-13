package self.project.task_manager.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
    private boolean isActive;
}
