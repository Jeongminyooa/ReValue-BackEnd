package kbsc.greenFunding.security;

import kbsc.greenFunding.entity.User;
import kbsc.greenFunding.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserJpaRepository userJpaRepo;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userJpaRepo.findById(Long.valueOf(userId)).orElseThrow();

        UserPrincipal userPrincipal = UserPrincipal.create(user);

        return userPrincipal;
    }
}
