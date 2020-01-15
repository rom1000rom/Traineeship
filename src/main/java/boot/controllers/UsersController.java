package boot.controllers;


import boot.model.AppUser;
import boot.services.BannersAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**Класс представляет собой REST-контроллёр, содержащий методы для
 * обработки стандартных Http-запросов в отношении пользователей приложения.
 @author Артемьев Р.А.
 @version 24.10.2019 */
@RestController
@RequestMapping("/users")
public class UsersController
{
    @Autowired
    private BannersAdminService bannersAdminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    /*@RequestBody говорит, что параметр будет именно в теле запроса
      @Valid - аннотация, которая активирует механизм валидации для данного бина*/
    public ResponseEntity<AppUser> addAppUser(@RequestBody @Valid AppUser appUser)
    {
        //Кодируем пароль перед добавлением в базу
        String pas = passwordEncoder.encode(appUser.getEncrytedPassword());
        appUser.setEncrytedPassword(pas);
        Integer id = bannersAdminService.addUser(appUser);
        appUser.setAppUserId(id);
        return ResponseEntity.status(201).body(appUser);
    }
}
