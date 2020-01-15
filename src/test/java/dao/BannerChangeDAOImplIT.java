package dao;

import boot.App;
import boot.dao.BannerChangeDAOImpl;
import boot.model.BannerChange;
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

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        @Sql("/banners-change-table.sql"),
        @Sql("/banners-change-data.sql")})//Выполняем перед каждым тестом инициализирующие sql-скрипты
@ActiveProfiles("test")//Активизируем профиль для тестирования
public class BannerChangeDAOImplIT
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    BannerChangeDAOImpl testedObject = new BannerChangeDAOImpl();

    @After
    public void tearDown()
    {
        //Метод dropTables удаляет указанную таблицу
        //JdbcTestUtils.deleteFromTables() очищает таблицы, не удаляя их
        //JdbcTestUtils.countRowsInTable() подсчитывает текущее количество строк в таблице
        JdbcTestUtils.dropTables(jdbcTemplate,  "banners_changes");
    }

    @Test
    public void testGetAllBannersChanges()
    {
        BannerChange expected = new BannerChange(1, 1, "1",
                "CREATE", null, LocalDate.parse("2016-09-21"));

        List<BannerChange> actual = testedObject.getAllBannersChanges();

        assertEquals(expected, actual.get(3));
    }

    @Test
    public void testGetBannersChanges()
    {
        BannerChange expected = new BannerChange(3, 1, "2",
                "DELETE", null, LocalDate.parse("2016-09-21"));

        BannerChange actual = testedObject.getBannersChanges(3, 1).get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBannersChangesByBannerId()
    {
        BannerChange expected = new BannerChange(4, 5, "1",
                "CREATE", null, LocalDate.parse("2016-09-21"));

        BannerChange actual = testedObject.getBannersChanges(5, 2).get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBannersChangesIsNull()
    {
        assertNull(testedObject.getBannersChanges(null, 1));
        assertNull(testedObject.getBannersChanges(null, null));
        assertNull(testedObject.getBannersChanges(1, null));
        assertNull(testedObject.getBannersChanges(1, 0));
        assertNull(testedObject.getBannersChanges(1, 4));
    }

    @Test
    public void testAddBannerChange()
    {
        BannerChange expected = new BannerChange(5, 2, "3",
                "CREATE", null, LocalDate.parse("2016-09-21"));

        Integer id = testedObject.addBannerChange(expected);
        BannerChange actual = testedObject.getBannersChanges(id, 1).get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void testFillBannerChangeIsNull()
    {
        Map<String, Object> source = new HashMap<String, Object>();
        assertNull(testedObject.fillBannerChange(source));
    }

    @Test
    public void testFillBannerChange()
    {
        String date = "2016-09-21";
        String dateTime = "2016-09-21 00:00:00";
        BannerChange expected = new BannerChange(1, 2, "1",
                "CREATE", "", LocalDate.parse(date));

        Map<String, Object> source = new HashMap<String, Object>();
        source.put("banner_change_id", 1);
        source.put("banner_id", 2);
        source.put("admin_name", "1");
        source.put("type_change", "CREATE");
        source.put("description_change", "");
        source.put("date_change", Timestamp.valueOf(dateTime));

        assertEquals(expected, testedObject.fillBannerChange(source));
    }

    @Test
    public void testGetBannersChangesCount()
    {
        assertTrue(testedObject.getBannersChangesCount().equals(4));
    }
}
