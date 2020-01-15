package boot.services;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**Класс позволяет получить информацию о текущем пользователе(principal)
 @author Артемьев Р.А.
 @version 27.10.2019 */
@Service
public class CurrentPrincipalService
{
    /**Метод возвращает имя текущего пользователя(principal)
     * @return имя пользователя*/
    public String getCurrentPrincipalName()
    {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
