package boot.dao;

import boot.model.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

/**Класс-реализация интерфейса BannerDAO для работы с базой данных.
@author Артемьев Р.А.
@version 13.09.2019 */
@Repository
public class BannerDAOImpl implements BannerDAO
{
    /**Запрос для получения записи из таблицы banners по его id*/
    private static final String BANNER_QUERY
            = "Select banner_id, img_src, width, height, target_url, lang_id FROM banners WHERE banner_id = ?;";

    /**Запрос для получения всех записей из таблицы banners*/
    private static final String BANNERS_QUERY
            = "Select banner_id, img_src, width, height, target_url, lang_id" +
            " From banners ORDER BY banner_id;";

    /**Запрос для удаления записи из таблицы banners по его id*/
    private static final String DELETE_BANNER
            = "DELETE FROM banners WHERE banner_id = ?;";

    /**Запрос для добавления нового баннера в таблицу banners*/
    private static final String ADD_BANNER
            = "INSERT INTO banners( img_src, width, height, target_url, lang_id )\n" +
            "VALUES ( ?, ?, ?, ?, ? );\n";

    /**Запрос для добавления нового баннера в таблицу banners*/
    private static final String UPDATE_BANNER
            = "UPDATE banners\n" +
            "  SET img_src = ?, width = ?, height = ?, target_url = ?, lang_id = ?\n" +
            "  WHERE banner_id = ?;";

    /**Запрос для получения количества баннеров в таблице banners*/
    private static final String COUNT_BANNERS
            = "SELECT count( * ) FROM banners";

    /**Запрос для получения максимального значения Id в таблице banners*/
    private static final String MAX_ID
            = "SELECT max(banner_id ) FROM banners;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**Метод заполняет данными из базы данных и возвращает список экземпляров класса Banner.
     @return список объектов класса Banner*/
    @Override
    public List<Banner> getAllBanners()
    {
        List<Map<String, Object>> listResalt = jdbcTemplate.queryForList(BANNERS_QUERY);
        List<Banner> listBanner = new ArrayList<>();

        Iterator<Map<String, Object>> it = listResalt.iterator();
        if (!it.hasNext())
        {
            return listBanner;
        }

        while(it.hasNext())
        {
            Map<String, Object> row = it.next();
            listBanner.add(this.fillBanner(row));
        }
        return listBanner;
    }

    /**Метод возвращает из базы данных объект класса Banner по его id.
     @param id id баннера
     @return объект класса Banner, или Null если такового нет.*/
    @Override
    public Banner getBanner(Integer id)
    {
        List<Map<String, Object>> resalt = jdbcTemplate.queryForList(BANNER_QUERY, id);
        if (resalt.isEmpty())
        {
            return null;
        }
        return this.fillBanner(resalt.get(0));
    }

    /**Метод удаляет из базы данных объект класса Banner по его id.
     @param id id баннера
     @return id удалённого баннера или null, если в параметре null,
     или баннер с таким id не существует*/
    @Override
    public Integer deleteBanner(Integer id)
    {
        if(id == null)
        {
            return null;
        }

        if(this.getBanner(id) == null)
        {
            return null;
        }
        jdbcTemplate.update(DELETE_BANNER, id);
        return  id;
    }



    /**Метод добавляет в базу данных информацию о баннере.
     * @param banner - объект баннера который нужно добавить
     * @return id добавленного баннера или null, если в параметре null*/
    @Override
    public Integer addBanner(Banner banner)
    {
        if(banner == null)
        {
            return null;
        }
        jdbcTemplate.update(ADD_BANNER, banner.getImgSrc(), banner.getWidth(),
                banner.getHeight(), banner.getTargetUrl(), banner.getLangId());
        return jdbcTemplate.queryForObject(MAX_ID, Integer.class);
    }

    /**Метод редактирует информацию о баннере в базе данных.
     * @param banner - объект баннера которым нужно обновить существующий
     * @return номер отредактированного баннера или null, если в параметр null
     * или баннера с таким id в базе не существует*/
    @Override
    public Integer updateBanner(Banner banner)
    {
        if(banner == null)
        {
            return null;
        }

        if(this.getBanner(banner.getBannerId()) == null)
        {
            return null;
        }
        jdbcTemplate.update(UPDATE_BANNER, banner.getImgSrc(), banner.getWidth(),
                banner.getHeight(), banner.getTargetUrl(), banner.getLangId(), banner.getBannerId() );
        return banner.getBannerId();
    }

    /**Метод создаёт, заполняет и возвращает экземпляр класса Banner.
     @param rs данные полученные из базы данных
     @return объект класса Banner */
    public Banner fillBanner(Map<String, Object> rs)
    {
        if (rs.isEmpty())
        {
            return null;
        }

        Integer id = (Integer)rs.get("banner_id");
        String imgSrc = (String)rs.get("img_src");
        Integer width = (Integer)rs.get("width");
        Integer height = (Integer)rs.get("height");
        String targetUrl = (String)rs.get("target_url");
        String langId = (String)rs.get("lang_id");

        return new Banner(id, imgSrc, width, height, targetUrl, langId);
    }

    /**Метод возвращает количество баннеров в базе данных
     @return количество баннеров */
    public Integer getBannersCount()
    {
        Integer count = jdbcTemplate.queryForObject(COUNT_BANNERS, Integer.class);
        return count;
    }
}
