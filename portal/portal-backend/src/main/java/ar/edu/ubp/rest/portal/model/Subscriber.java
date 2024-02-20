package ar.edu.ubp.rest.portal.model;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ar.edu.ubp.rest.portal.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Subscriber", schema = "dbo", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Subscriber implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer subscriberId;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String lastname;
    @Column(nullable = false)
    String email;
    @Column(nullable = false)
    String phone;
    @Column(nullable = false)
    String password;
    @Column(nullable = false, columnDefinition = "BIT")
    Boolean validated;

    @Column(nullable = true, columnDefinition = "DATE")
    LocalDate birth;
    
    @Transient
    @Enumerated(EnumType.STRING)
    final Role role = Role.SUBSCRIBER;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
