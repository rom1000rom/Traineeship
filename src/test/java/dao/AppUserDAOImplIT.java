package dao;


import boot.App;
import boot.dao.AppUserDAOImpl;
import boot.model.AppUser;
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
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/*Используя специальный runner  SpringJUnit4ClassRunner, мы
 инициализируем Spring контест автоматически при запуске теста*/
@RunWith(SpringJUnit4ClassRunner.class)
/*Аннотация  @ContextConfiguration указывает, как именно мы хотим
сконфигурировать контекст.*/
@ContextConfiguration(loader= AnnotationConfigContextLoader.class,
        classes = App.class)
@SqlGroup({
        @Sql("/app-users-table.sql"),
        @Sql("/app-users-data.sql")})//Выполняем перед каждым тестом инициализирующие sql-скрипты
@ActiveProfiles("test")//Активизируем профиль для тестирования
public class AppUserDAOImplIT
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    AppUserDAOImpl testedObject = new AppUserDAOImpl();

    @After
    public void tearDown()
    {
        //Метод dropTables удаляет указанную таблицу
        //JdbcTestUtils.deleteFromTables() очищает таблицы, не удаляя их
        //JdbcTestUtils.countRowsInTable() подсчитывает текущее количество строк в таблице
        JdbcTestUtils.dropTables(jdbcTemplate,  "app_users");
    }

    @Test
    public void testFillAppUserIsNull()
    {
        Map<String, Object> source = new HashMap<String, Object>();
        assertNull(testedObject.fillAppUser(source));
    }

    @Test
    public void testFillAppUser()
    {
        AppUser expected = new AppUser(1, "TEST2", "TEST3");

        Map<String, Object> source = new HashMap<String, Object>();
        source.put("app_user_id", 1);
        source.put("app_user_name", "TEST2");
        source.put("app_user_encryted_password", "TEST3");

        assertEquals(expected, testedObject.fillAppUser(source));
    }

    @Test
    public void testGetAppUserByName()
    {
        AppUser expected = new AppUser(1, "TEST_NAME",
                "TEST_PASSWORD");
        AppUser actual = testedObject.getAppUserByName("TEST_NAME");
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAppUserByNameNotExist()
    {
        assertNull(testedObject.getAppUserByName("TEST_NAME2"));
    }

    @Test
    public void testAddAppUserIsNull()
    {
        assertNull(testedObject.addAppUser(null));
    }

    @Test
    public void testAddAppUserAlreadyExist()
    {
        AppUser expected = new AppUser(1, "TEST_NAME",
                "TEST_PASSWORD");
        assertNull(testedObject.addAppUser(expected));
    }

    @Test
    public void testAddAppUser()
    {
        AppUser expected = new AppUser(2, "TEST_NAME3",
                "TEST_PASSWORD");

       testedObject.addAppUser(expected);

        assertEquals(expected, testedObject.getAppUserByName("TEST_NAME3"));
    }

}
