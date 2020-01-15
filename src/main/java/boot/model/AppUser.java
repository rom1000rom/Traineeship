package boot.model;


import javax.validation.constraints.NotNull;
import java.util.Objects;

/**Класс для хранения данных о пользователе приложения.
 @author Артемьев Р.А.
 @version 21.10.2019 */
public class AppUser
{
    @NotNull
    private Integer appUserId;

    @NotNull
    private String name;

    @NotNull
    private String encrytedPassword;

    public AppUser(@NotNull Integer appUserId, @NotNull String name, @NotNull String encrytedPassword) {
        this.appUserId = appUserId;
        this.name = name;
        this.encrytedPassword = encrytedPassword;
    }

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncrytedPassword() {
        return encrytedPassword;
    }

    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(appUserId, appUser.appUserId) &&
                Objects.equals(name, appUser.name) &&
                Objects.equals(encrytedPassword, appUser.encrytedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appUserId, name, encrytedPassword);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "appUserId=" + appUserId +
                ", name='" + name + '\'' +
                ", encrytedPassword='" + encrytedPassword + '\'' +
                '}';
    }
}
