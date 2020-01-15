package controllers;

import boot.App;
import boot.controllers.UsersController;
import boot.model.AppUser;
import boot.services.BannersAdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=App.class)
/*Аннотация @WebMvcTest(UsersController.class) создаёт тестовое окружение с настроенным
Spring MVC и входящим в него Jackson, в том виде, в каком они настроены в реальном приложении.*/
@WithMockUser
@AutoConfigureMockMvc(addFilters = false)//Без этого testAddAppUser
//не срабатывает, хотя сама REST служба работает исправно
@WebMvcTest(UsersController.class)
public class UsersControllerTest
{
    @MockBean
    private BannersAdminService service;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddAppUser() throws Exception
    {
        Integer id = 1;
        AppUser expected = new AppUser(0, "TEST_NAME",
                "ENCR_PASSWORD");
        AppUser actual = new AppUser(id, "TEST_NAME",
                "TEST_PASSWORD");

        ObjectMapper objectMapper = new ObjectMapper();
        when(passwordEncoder.encode(actual.getEncrytedPassword())).thenReturn("ENCR_PASSWORD");
        when(service.addUser(actual)).thenReturn(id);


        mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(actual))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(expected)));//Конвертируем в json
    }

}
