package data.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import business.api.exceptions.InvalidTokenException;
import data.daos.AuthorizationDao;
import data.daos.TokenDao;
import data.daos.UserDao;
import data.entities.Role;
import data.entities.User;
import data.entities.Token;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private AuthorizationDao authorizationDao;

    private boolean isTokenValid(User user)  {
        Token token = tokenDao.findByUser(user);
        if (token != null) {
            System.out.println("@@@@@@@@@@@@@@@@Estado token: " + token.detailsTokenStatus());

            if (token.isTokenExpired(Calendar.getInstance())) {
              
                return false;
            }
        } 
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        // añadir la validación de que el token no este caducado tb.
        User user = userDao.findByTokenValue(username);
        if (user == null) {
            user = userDao.findByUsernameOrEmail(username);
            if (user == null) {
                throw new UsernameNotFoundException("Usuario no encontrado");
            } else {
                return this.userBuilder(user.getUsername(), user.getPassword(), Arrays.asList(Role.AUTHENTICATED),true);
            }
        } else {
            // Validación de que el token no este caducado
            /*
             * Token token = tokenDao.findByUser(user); if (token != null) { if (token.isTokenExpired(Calendar.getInstance())) { throw new
             * UsernameNotFoundException("Token caducado"); } }
             */
                List<Role> roleList = authorizationDao.findRoleByUser(user);
                return this.userBuilder(user.getUsername(), new BCryptPasswordEncoder().encode(""), roleList, isTokenValid(user));
            
        }
    }

    private org.springframework.security.core.userdetails.User userBuilder(String username, String password, List<Role> roles, boolean isTokenValid) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        //boolean credentialsNonExpired = isTokenValid;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.roleName()));
        }
        return new org.springframework.security.core.userdetails.User(username, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
    }
}
