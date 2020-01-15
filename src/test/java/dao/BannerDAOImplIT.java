package dao;

import boot.App;
import boot.dao.BannerDAOImpl;
import boot.model.Banner;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/*Используя специальный runner  SpringJUnit4ClassRunner, мы
 инициализируем Spring контест автоматически при запуске теста*/
@RunWith(SpringJUnit4ClassRunner.class)
/*Аннотация  @ContextConfiguration указывает, как именно мы хотим
сконфигурировать контекст.*/
@ContextConfiguration(loader= AnnotationConfigContextLoader.class,
        classes = App.class)
@SqlGroup({
        @Sql("/banners-table.sql"),
        @Sql("/banners-table.sql"),
        @Sql("/banners-data.sql")})//Выполняем перед каждым тестом инициализирующие sql-скрипты
@ActiveProfiles("test")//Активизируем профиль для тестирования
public class BannerDAOImplIT
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    BannerDAOImpl testedObject = new BannerDAOImpl();

    @After
    public void tearDown()
    {
        //Метод dropTables удаляет указанную таблицу
        //JdbcTestUtils.deleteFromTables() очищает таблицы, не удаляя их
        //JdbcTestUtils.countRowsInTable() подсчитывает текущее количество строк в таблице
        JdbcTestUtils.dropTables(jdbcTemplate,  "banners");
    }

    @Test
    public void testFillBannerIsNull()
    {
        Map<String, Object> source = new HashMap<String, Object>();
        assertNull(testedObject.fillBanner(source));
        int i = new int[5][5].length;
    }

    @Test
    public void testGetBannersCount()
    {
        assertTrue(testedObject.getBannersCount().equals(2));
    }

    @Test
    public void testFillBanner()
    {
        Banner expected = new Banner(1, "TEST", 2, 3,
                "TEST2", "TEST3");

        Map<String, Object> source = new HashMap<String, Object>();
        source.put("banner_id", 1);
        source.put("img_src", "TEST");
        source.put("width", 2);
        source.put("height", 3);
        source.put("target_url", "TEST2");
        source.put("lang_id", "TEST3");

        assertEquals(expected, testedObject.fillBanner(source));
    }

    @Test
    public void testGetAllBanners()
    {
        Banner expected = new Banner(2, "TEST", 2, 3,
                "TEST2", "TEST3");

            List<Banner> actual = testedObject.getAllBanners();
        assertEquals(expected, actual.get(1));
    }

    @Test
    public void testGetBannerIsNull()
    {
        assertNull(testedObject.getBanner(null));
    }

    @Test
    public void testGetBanner()
    {
        Banner expected = new Banner(1, "TEST", 0, 0,
                "TEST", "TEST");
        Banner actual = testedObject.getBanner(1);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBannerNotExist()
    {
        assertNull(testedObject.getBanner(0));
    }

    @Test
    public void testDeleteBannerIsNull()
    {
        assertNull(testedObject.deleteBanner(null));
    }

    @Test
    public void testDeleteBannerNotExist()
    {
        assertNull(testedObject.deleteBanner(0));
    }

    @Test
    public void testDeleteBanner()
    {
        testedObject.deleteBanner(1);
        assertNull(testedObject.getBanner(1));
    }

    @Test
    public void testAddBannerIsNull()
    {
        assertNull(testedObject.addBanner(null));
    }

    @Test
    public void testAddBanner()
    {
        Banner expected = new Banner(4, "TEST", 3, 3,
                "TEST2", "TEST3");

        testedObject.addBanner(expected);
        Integer id = testedObject.addBanner(expected);

        assertEquals(expected, testedObject.getBanner(id));
    }

    @Test
    public void testUpdateBannerIsNull()
    {
        assertNull(testedObject.updateBanner(null));
    }

    @Test
    public void testUpdateBannerNotExist()
    {
        Banner banner = new Banner(0, "TEST", 3, 3,
                "TEST2", "TEST3");
        assertNull(testedObject.updateBanner(banner));
    }

    @Test
    public void testUpdateBanner()
    {
        Banner expected = new Banner(2, "TEST", 4, 5,
                "TEST3", "TEST4");
        testedObject.updateBanner(expected);
        assertEquals(expected, testedObject.getBanner(2));
    }
}
