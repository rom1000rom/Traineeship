package boot.dao;


import boot.model.BannerChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**Класс-реализация интерфейса BannerChangeDAO для работы с базой данных.
 @author Артемьев Р.А.
 @version 17.09.2019 */
@Repository
public class BannerChangeDAOImpl implements BannerChangeDAO
{
    /**Запрос для получения записи из таблицы banners_changes по id действия над баннером*/
    private static final String BANNER_CHANGE_QUERY
            = "Select banner_change_id, banner_id, admin_name, type_change, description_change,  date_change" +
            " From banners_changes WHERE banner_change_id = ?;";

    /**Запрос для получения списка записей из таблицы banners_changes по id баннера*/
    private static final String BANNER_CHANGES_QUERY_BY_BANNER_ID
            = "Select banner_change_id, banner_id, admin_name, type_change, description_change,  date_change" +
            " From banners_changes WHERE banner_id = ?;";

    /**Запрос для добавления новой записи в таблицу banners_changes*/
    private static final String ADD_BANNER_CHANGE
            = "INSERT INTO banners_changes" +
            "( banner_id, admin_name, type_change, description_change,  date_change)\n" +
            "VALUES ( ?, ?, ?, ?, ? );\n";

    /**Запрос для получения всех записей из таблицы banners_changes*/
    private static final String BANNERS_CHANGES_QUERY
            = "Select banner_change_id, banner_id, admin_name, type_change, description_change,  date_change" +
            " From banners_changes ORDER BY banner_change_id DESC;";

    /**Запрос для получения количества баннеров в таблице banners*/
    private static final String COUNT_BANNERS_CHANGES
            = "SELECT count( * ) FROM banners_changes";

    /**Запрос для получения максимального значения Id в таблице banners*/
    private static final String MAX_ID
            = "SELECT max(banner_change_id) FROM banners_changes;";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**Метод заполняет данными из базы данных и возвращает список всех экземпляров класса BannerChange.
     @return список объектов класса BannerChange*/
    @Override
    public List<BannerChange> getAllBannersChanges()
    {
        List<Map<String, Object>> listResalt = jdbcTemplate.queryForList(BANNERS_CHANGES_QUERY);
        List<BannerChange> listBannerChange = new ArrayList<>();

        Iterator<Map<String, Object>> it = listResalt.iterator();
        if (!it.hasNext())
        {
            return listBannerChange;
        }

        while(it.hasNext())
        {
            Map<String, Object> row = it.next();
            listBannerChange.add(this.fillBannerChange(row));
        }
        return listBannerChange;
    }

     /**Метод позволяет получить список действий над баннерами в зависимости от типа отбора:
      * 1 - по id действия над баннером
      * 2 - по id баннера
      *@param id - id номер действия над баннером, баннера  в зависимости
      *          от выбранного типа отбора
      *@param type - тип отбора
      *@return список действий над баннерами или null, если введены неккоректные параметры*/
    @Override
    public List<BannerChange> getBannersChanges(Integer id, Integer type)
    {
        if(id == null || type == null)
        {
            return null;
        }
        List<Map<String, Object>> listResalt;
        switch (type)
        {
            case 1: //по id действия над баннером
                listResalt = jdbcTemplate.queryForList(BANNER_CHANGE_QUERY, id);
                break;
            case 2: //по id баннера
                listResalt = jdbcTemplate.queryForList(BANNER_CHANGES_QUERY_BY_BANNER_ID, id);
                break;
            default:
                return null;
        }
        List<BannerChange> listBannerChange = new ArrayList<>();
        Iterator<Map<String, Object>> it = listResalt.iterator();
        if (!it.hasNext())
        {
            return listBannerChange;
        }

        while(it.hasNext())
        {
            Map<String, Object> row = it.next();
            listBannerChange.add(this.fillBannerChange(row));
        }
        return listBannerChange;
    }

    /**Метод добавляет в базу данных объект класса BannerChange.
     * @param bannerChange - действие над баннером
     * @return id добавленной записи или null, если в параметре null*/
    @Override
    public Integer addBannerChange(BannerChange bannerChange)
    {
        if(bannerChange == null)
        {
            return null;
        }
        jdbcTemplate.update(ADD_BANNER_CHANGE, bannerChange.getBannerId(), bannerChange.getAdminName(),
                bannerChange.getTypeChange(), bannerChange.getDescriptionChange(),
                bannerChange.getDateChange());
        return jdbcTemplate.queryForObject(MAX_ID, Integer.class);
    }

    /**Метод создаёт, заполняет и возвращает экземпляр класса BannerChange.
     @param rs данные полученные из базы данных
     @return объект класса BannerChange */
    public BannerChange fillBannerChange(Map<String, Object> rs)
    {
        if (rs.isEmpty())
        {
            return null;
        }

        Integer bannerChangeId = (Integer)rs.get("banner_change_id");
        Integer bannerId = (Integer)rs.get("banner_id");
        String adminName = (String) rs.get("admin_name");
        String typeChange = (String)rs.get("type_change");
        String descriptionChange = (String)rs.get("description_change");
        Timestamp dateChange = (Timestamp)rs.get("date_change");

        return new BannerChange(bannerChangeId, bannerId, adminName, typeChange,
                descriptionChange, dateChange.toLocalDateTime().toLocalDate());
    }

    /**Метод возвращает количество изменений баннеров в базе данных
     @return количество баннеров */
    public Integer getBannersChangesCount()
    {
        return jdbcTemplate.queryForObject(COUNT_BANNERS_CHANGES, Integer.class);
    }
}
