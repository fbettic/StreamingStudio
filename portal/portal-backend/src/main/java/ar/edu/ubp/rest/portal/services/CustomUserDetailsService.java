package ar.edu.ubp.rest.portal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.models.users.CustomUserDetails;
import ar.edu.ubp.rest.portal.repositories.interfaces.IUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private IUserRepository iUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUserDetails user =  this.iUserRepository.getUserByEmail(username);

        if(user==null){
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

}
