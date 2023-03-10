package task7.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public enum Role {

    ADMIN,
    OPERATOR,
    METER;

    public SimpleGrantedAuthority getSimpleGrantedAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }

    public static List<Role> getRoles() {
        return List.of(values());
    }
}

//@AllArgsConstructor
//@NoArgsConstructor
//@Setter
//@Getter
//public class Role {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//    @ManyToMany(mappedBy = "roles")
//    private List<User> users = new ArrayList<>();
//
//}
