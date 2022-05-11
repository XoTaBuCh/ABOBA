package com.tabletka.model.user;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(
            message = "Email shouldn't be blank",
            groups  = Validation.Create.class
    )
    @Email(
            message = "Email is invalid",
            groups  = Validation.Create.class
    )
    private String email;

    @JsonProperty(access = Access.WRITE_ONLY)
    @NotBlank(
            message = "Password shouldn't be blank",
            groups  = Validation.Create.class
    )
    @Size(
            min     = 6,
            max     = 255,
            message = "Password length should be from 6 to 255",
            groups  = Validation.Create.class
    )
    @Pattern(
            regexp  = "^\\w{1,255}$",
            message = "Password should contain only letters of latin alphabet, numbers and '_' symbol",
            groups  = Validation.Create.class
    )
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public UserDetails getUserDetails() {
        return new org.springframework.security.core.userdetails.User(
                this.getUsername(),
                this.getPassword(),
                this.isAccountNonExpired(),
                this.isAccountNonLocked(),
                this.isCredentialsNonExpired(),
                this.isEnabled(),
                this.getRole().getAuthorities()
        );
    }

    private boolean isActive() {
        return status.equals(Status.ACTIVE);
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRole().getAuthorities();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return isActive();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return isActive();
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return isActive();
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return isActive();
    }
}
