package controllers;

import boot.App;
import boot.controllers.BannersController;
import boot.model.Banner;
import boot.services.BannersAdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=App.class)
/*Аннотация @WebMvcTest(BannersController.class) создаёт тестовое окружение с настроенным
Spring MVC и входящим в него Jackson, в том виде, в каком они настроены в реальном приложении.*/
@WebMvcTest(BannersController.class)
/*Аннотация @WithMockUser создает пользователя, который уже аутентифицирован, в тестовых целях.
По умолчанию его учетные данные: user, password*/
@WithMockUser
@AutoConfigureMockMvc(addFilters = false)//Без этого testAddBanner и testDeleteBanner
//не срабатывают, хотя сами REST службы работают исправно
public class BannersControllerTest
{
    @MockBean
    private BannersAdminService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetBannerIsNull() throws Exception
    {
        Integer id = 2;

        when(service.getBanner(id)).thenReturn(null);

        mockMvc.perform(get("/banners/" + id))
                .andExpect(status().is(404));//Проверяем Http-ответ
String str;
    }

    @Test
    public void testGetBanner() throws Exception
    {
        Integer id = 2;
        Banner expected = new Banner(id, "TEST", 2, 3,
                "TEST2", "TEST3");
        /*Задаём поведения mock объекта BannersAdminService: мы говорим, что будет
        вызван метод getBanner(id) и что в ответ он должен вернуть объект expected*/
        when(service.getBanner(id)).thenReturn(expected);

        mockMvc.perform(get("/banners/" + id))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        new ObjectMapper().writeValueAsString(expected)));//Конвертируем в json
    }

    @Test
    public void testGetAllBanners() throws Exception
    {
        List<Banner> listBanner = new ArrayList<>();

        Banner expectedFirst = new Banner(1, "TEST", 2, 3,
                "TEST2", "TEST3");

        Banner expectedSecond = new Banner(2, "TEST4", 5, 6,
                "TEST5", "TEST6");

        listBanner.add(expectedFirst);
        listBanner.add(expectedSecond);

        when(service.getAllBanners()).thenReturn(listBanner);

        mockMvc.perform(get("/banners"))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        new ObjectMapper().writeValueAsString(listBanner)));//Конвертируем в json
    }

    @Test
    public void testAddBanner() throws Exception
    {
        Integer id = 1;
        Banner expected = new Banner(id, "TEST", 2, 3,
                "TEST2", "TEST3");
        ObjectMapper objectMapper = new ObjectMapper();
        when(service.addBanner(expected)).thenReturn(id);
        mockMvc.perform(post("/banners")
                .content(objectMapper.writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(expected)));//Конвертируем в json
    }

    @Test
    public void testUpdateBannerNotExist() throws Exception
    {
        Integer id = 0;
        Banner expected = new Banner(id, "TEST", 2, 3,
                "TEST2", "TEST3");
        ObjectMapper objectMapper = new ObjectMapper();
        when(service.updateBanner(expected)).thenReturn(null);

        mockMvc.perform(put("/banners/" + id)//Совершаем тестовый Http-запрос
                .content(objectMapper.writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(405));//Проверяем Http-ответ
    }

    @Test
    public void testDeleteBannerNotExist() throws Exception
    {
        Integer id = 1;

        when(service.getBanner(id)).thenReturn(null);

        mockMvc.perform(delete("/banners/" + id))
                .andExpect(status().is(404));//Проверяем Http-ответ
    }

    @Test
    public void testDeleteBanner() throws Exception
    {
        Integer id = 1;
        Banner expected = new Banner(id, "TEST", 2, 3,
                "TEST2", "TEST3");
        ObjectMapper objectMapper = new ObjectMapper();

        when(service.getBanner(id)).thenReturn(expected);
        when(service.deleteBanner(id)).thenReturn(id);

        mockMvc.perform(delete("/banners/" + id))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(expected)));//Конвертируем в json
    }
}
