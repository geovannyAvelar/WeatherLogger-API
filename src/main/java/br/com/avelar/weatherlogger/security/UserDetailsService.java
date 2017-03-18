package br.com.avelar.weatherlogger.security;

import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import br.com.avelar.weatherlogger.authority.Authority;
import br.com.avelar.weatherlogger.exceptions.UserNotActivatedException;
import br.com.avelar.weatherlogger.user.User;
import br.com.avelar.weatherlogger.user.UserRepository;

@Component("userDetailsService")
public class UserDetailsService 
                       implements org.springframework.security.core.userdetails.UserDetailsService {
  private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(final String login) {

    log.debug("Authenticating {}", login);
    String lowercaseLogin = login.toLowerCase();
    
    User userFromDatabase;
    
    if (lowercaseLogin.contains("@")) {
      userFromDatabase = userRepository.findByEmail(lowercaseLogin);
    } else {
      userFromDatabase = userRepository.findByUsernameCaseInsensitive(lowercaseLogin);
    }

    userFromDatabase = userRepository.findByEmail(lowercaseLogin);

    if (userFromDatabase == null) {
      throw new UsernameNotFoundException(
          "User " + lowercaseLogin + " was not found in the database");
    } else if (!userFromDatabase.isActivated()) {
      throw new UserNotActivatedException("User " + lowercaseLogin + " is not activated");
    }

    Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    for (Authority authority : userFromDatabase.getAuthorities()) {
      GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
      grantedAuthorities.add(grantedAuthority);
    }

    return new org.springframework.security.core.userdetails.User(userFromDatabase.getEmail(),
        userFromDatabase.getPassword(), grantedAuthorities);

  }
}
