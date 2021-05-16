package bo.ucb.edu.vic19.bl;

import bo.ucb.edu.vic19.dao.AuthDao;
import bo.ucb.edu.vic19.dao.UserDao;
import bo.ucb.edu.vic19.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private AuthDao authDao;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(AuthDao authDao){
        this.authDao = authDao;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = authDao.findAdminByEmail(username);
        logger.error(username);
        if(user != null){

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        } else {
            throw new UsernameNotFoundException("User '"+username+"' not found!");
        }
    }
}
