package boot.dao;

import boot.model.AppUser;


/**Интерфейс служит для определения функций хранилища данных о пользователях приложения
 @author Артемьев Р.А.
 @version 21.10.2019 */
public interface AppUserDAO
{
    /**Метод возвращает из базы данных объект класса AppUser по его имени.
     @param name имя пользователя
     @return объект класса AppUser, или Null если такового нет.*/
    AppUser getAppUserByName(String name);

    /**Добавить пользователя приложения
     * @param appUser объект представляющий пользователя
     * @return id добавленного пользователя*/
    Integer addAppUser(AppUser appUser);
}
