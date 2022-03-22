package edu.greenriver.sdev.myspringproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @author Ruslan Bessarab
 * @version 1.0
 *
 * This class represents authorities for different users
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorityId;

    private String role;

    @ManyToOne
    @JoinColumn(name = "userId")
    @ToString.Exclude
    private User user;

    @Override
    public String getAuthority() {
        return role;
    }
}
