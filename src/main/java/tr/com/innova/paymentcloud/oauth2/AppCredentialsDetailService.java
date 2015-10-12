package tr.com.innova.paymentcloud.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.com.innova.paymentcloud.oauth2.repository.AppCredential;
import tr.com.innova.paymentcloud.oauth2.repository.AppCredentialRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mehme on 12.10.2015.
 */
@Service
public class AppCredentialsDetailService implements UserDetailsService{

    private final AppCredentialRepository appCredentialRepository;

    @Autowired
    public AppCredentialsDetailService(AppCredentialRepository appCredentialRepository) {
        this.appCredentialRepository = appCredentialRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AppCredential appCredential = appCredentialRepository.findByApiKey(s);

        if(appCredential == null) {
            throw new UsernameNotFoundException(String.format("App credential %s is invalid", appCredential));
        }

        return new AppCredentialRepositoryDetails(appCredential);
    }

    public static final class AppCredentialRepositoryDetails extends AppCredential implements UserDetails {

        public AppCredentialRepositoryDetails(AppCredential credential) {
            super(credential);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            return authorities;
        }


        @Override
        public String getUsername() {
            return getApiKey();
        }

        @Override
        public String getPassword() {
            return getApiSecret();
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
}
