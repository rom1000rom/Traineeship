package boot.services;

import boot.model.AppUser;
import boot.model.Banner;
import boot.model.BannerChange;

import java.util.List;

/**Интерфейс служит для определения серсисных функций по работе с баннерами.
 @author Артемьев Р.А.
 @version 19.09.2019 */
public interface BannersAdminService
{
    /*Типы действий над баннерами*/
    String CREATE = "CREATE";
    String UPDATE = "UPDATE";
    String DELETE = "DELETE";

    /**Метод возвращает список всех баннеров, экземпляров класса Banner.
     @return список объектов класса Banner*/
    List<Banner> getAllBanners();

     /**Метод возвращает из базы данных объект класса Banner по его id.
     @param id id баннера
     @return объект класса Banner, или Null если такового нет.*/
    Banner getBanner(Integer id);

    /**Метод заполняет данными из базы данных и возвращает список всех действий над баннерами
     * экземпляров класса BannerChange.
     @return список объектов класса BannerChange*/
    List<BannerChange> getAllBannersChanges();

    /**Метод позволяет получить список действий над баннерами в зависимости от типа отбора:
     * 1 - по id действия над баннером
     * 2 - по id баннера
     *@param id - id номер действия над баннером, баннера или администратора в зависимости
     *          от выбранного типа отбора
     *@param type - тип отбора
     *@return список действий над баннерами или null, если введены неккоректные параметры*/
    List<BannerChange> getBannersChanges(Integer id, Integer type);

    /**Метод добавляет в базу данных информацию о баннере
     * и об этом действии в целях аудита в рамках одной транзакции.
     * @param banner - объект баннера который нужно добавить
     * @return id добавленного баннера или null, если в параметре null*/
    Integer addBanner(Banner banner);

    /**Метод удаляет из базы данных объект класса Banner по его id и добавляет
     * в в неё информацию об этом действии в целях аудита в рамках одной транзакции.
     @param id id баннера
     @return id удалённого баннера или null, если в параметре null,
     или баннер с таким id не существует*/
    Integer deleteBanner(Integer id);

    /**Метод редактирует информацию о баннере в базе данных и добавляет в неё
     * информацию об этом действии в целях аудита в рамках одной транзакции.
     * @param banner - объект баннера которым нужно обновить существующий
     * @return номер отредактированного баннера или null, если в параметр null
     * или баннера с таким id в базе не существует*/
    Integer updateBanner(Banner banner);

    /**Метод добавляет в базу данных информацию о пользователе.
     * @param appUser - объект пользователя который нужно добавить
     * @return id добавленного пользователя или null, если в параметре null или
     * пользователь с таким именем уже существует*/
    Integer addUser(AppUser appUser);
}
