package boot.dao;

import boot.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**Класс-реализация интерфейса AppUserDAO для работы с базой данных.
@author Артемьев Р.А.
@version 21.10.2019 */
@Repository
public class AppUserDAOImpl implements AppUserDAO
{
    /**Запрос для получения записи из таблицы app_users по имени пользователя*/
    private static final String APP_USER_QUERY_BY_NAME
            = "Select app_user_id, app_user_name, app_user_encryted_password From app_users WHERE app_user_name = ?;";

    /**Запрос для добавления новой записи в таблицу app_users*/
    private static final String ADD_APP_USER
            = "INSERT INTO app_users(app_user_name, app_user_encryted_password)\nVALUES ( ?, ?);\n";

    /**Запрос для получения максимального значения Id в таблице app_users*/
    private static final String MAX_ID
            = "SELECT max(app_user_id) FROM app_users;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**Метод возвращает из базы данных объект класса AppUser по его имени.
     @param name имя пользователя
     @return объект класса AppUser, или Null если такового нет.*/
    @Override
    public AppUser getAppUserByName(String name)
    {
        List<Map<String, Object>> resalt = jdbcTemplate.queryForList(APP_USER_QUERY_BY_NAME, name);
        if (resalt.isEmpty())
        {
            return null;
        }
        return this.fillAppUser(resalt.get(0));
    }

    /**Добавить пользователя приложения
     * @param appUser объект представляющий пользователя
     * @return id добавленной записи или null, если пользователь с таким именем уже существует
     * или в качестве аргумента стоит null*/
    @Override
    public Integer addAppUser(AppUser appUser) {
        if(appUser == null)
        {
            return null;
        }

        if(this.getAppUserByName(appUser.getName()) != null)
        {
            return null;
        }

        jdbcTemplate.update(ADD_APP_USER, appUser.getName(), appUser.getEncrytedPassword());
        return jdbcTemplate.queryForObject(MAX_ID, Integer.class);
    }

    /**Метод создаёт, заполняет и возвращает экземпляр класса AppUser.
     @param rs данные полученные из базы данных
     @return объект класса AppUser*/
    public AppUser fillAppUser(Map<String, Object> rs)
    {
        if (rs.isEmpty())
        {
            return null;
        }

        Integer id = (Integer)rs.get("app_user_id");
        String name = (String)rs.get("app_user_name");
        String encrytedPassword = (String)rs.get("app_user_encryted_password");

        return new AppUser(id, name, encrytedPassword);
    }
}
