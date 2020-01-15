package boot.dao;

import boot.model.Banner;

import java.util.List;

/**Интерфейс служит для определения функций хранилища данных об интернет-баннерах
 @author Артемьев Р.А.
 @version 13.09.2019 */
public interface BannerDAO
{
    /**Получить список всех баннеров*/
    List<Banner> getAllBanners();

    /**Метод возвращает из базы данных объект класса Banner по его id.
     @param id id баннера
     @return объект класса Banner, или Null если такового нет.*/
    Banner getBanner(Integer id);

    /**Метод удаляет из базы данных объект класса Banner по его id.
     @param id id баннера
     @return id удалённого баннера или null, если в параметре null,
     или баннер с таким id не существует*/
   Integer deleteBanner(Integer id);

    /**Метод добавляет в базу данных информацию о баннере.
     * @param banner - объект баннера который нужно добавить
     * @return id добавленного баннера или null, если в параметре null*/
    Integer addBanner(Banner banner);

    /**Метод редактирует информацию о баннере в базе данных.
     * @param banner - объект баннера которым нужно обновить существующий
     * @return номер отредактированного баннера или null, если в параметр null
     * или баннера с таким id в базе не существует*/
    Integer updateBanner(Banner banner);
}
