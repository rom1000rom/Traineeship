package boot.services;

import boot.dao.AppUserDAO;
import boot.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**Класс позволяет получить из источника данных объект пользователя и сформировать из него
 объект UserDetails который будет использоваться контекстом Spring Security.
 Для этого реализуем единственный метод loadUserByUsername(String username)
 интерфейса UserDetailsService .
 @author Артемьев Р.А.
 @version 26.10.2019 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserDAO appUserDAO;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser = appUserDAO.getAppUserByName(userName);

        if (appUser == null) {
            System.out.println("User " + userName + " not found!");
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        System.out.println("Found User: " + appUser);

        List<GrantedAuthority> grantList = new ArrayList<>();
        //В нашем приложении предусмотрена одна роль USER
        GrantedAuthority authority = new SimpleGrantedAuthority("USER");
        grantList.add(authority);

        UserDetails userDetails = (UserDetails) new User(appUser.getName(),
                appUser.getEncrytedPassword(), grantList);

        return userDetails;
    }

}