package boot.dao;


import boot.model.BannerChange;
import java.util.List;

/**Интерфейс служит для определения функций хранилища данных
 * об действиях над интернет-баннерами
 @author Артемьев Р.А.
 @version 17.09.2019 */
public interface BannerChangeDAO
{

    /**Получить список всех действий над баннерами*/
    List<BannerChange> getAllBannersChanges();

    /**Получить список действий над баннерами в зависимости от типа отбора:
     * 1 - по id действия над баннером(класс BannerChange)
     * 2 - по id баннера(класс Banner)*/
    List<BannerChange> getBannersChanges(Integer id, Integer type);

    /**Добавить действие над баннером*/
    Integer addBannerChange(BannerChange bannerChange);
}
