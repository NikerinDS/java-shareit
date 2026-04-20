package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {UserController.class})
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;

    @Test
    @SneakyThrows
    void createUser_shouldReturnOkAndUserDtoWithId() {
        UserDto newUser = new UserDto();
        newUser.setName("user");
        newUser.setEmail("address@test.com");

        UserDto expectedUser = new UserDto();
        expectedUser.setId(1L);
        expectedUser.setName(newUser.getName());
        expectedUser.setEmail(newUser.getEmail());

        Mockito.when(userService.createUser(newUser)).thenReturn(expectedUser);

        MvcResult mvcResult = mockMvc.perform(
                post("/users")
                        .content(mapper.writeValueAsString(newUser))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String responseBodyStr = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserDto actualUser = mapper.readValue(responseBodyStr, UserDto.class);

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    @SneakyThrows
    void createUser_shouldReturnBadRequest_whenNameIsEmpty() {
        UserDto newUser = new UserDto();
        newUser.setName("");
        newUser.setEmail("address@test.com");

        mockMvc.perform(
                post("/users")
                        .content(mapper.writeValueAsString(newUser))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @SneakyThrows
    void createUser_shouldReturnBadRequest_whenEmailIsEmpty() {
        UserDto newUser = new UserDto();
        newUser.setName("user");
        newUser.setEmail("");

        mockMvc.perform(
                post("/users")
                        .content(mapper.writeValueAsString(newUser))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @SneakyThrows
    void createUser_shouldReturnBadRequest_whenEmailIsIncorrect() {
        UserDto newUser = new UserDto();
        newUser.setName("user");
        newUser.setEmail("wrong_email");

        mockMvc.perform(
                post("/users")
                        .content(mapper.writeValueAsString(newUser))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @SneakyThrows
    void updateUser_shouldReturnOkAndUserDto() {
        UserDto expectedUser = new UserDto();
        expectedUser.setId(1L);
        expectedUser.setName("new name");
        expectedUser.setEmail("changed_address@test.com");

        Mockito.when(userService.updateUser(expectedUser)).thenReturn(expectedUser);

        MvcResult mvcResult = mockMvc.perform(
                patch("/users/1")
                        .content(mapper.writeValueAsString(expectedUser))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String responseBodyStr = mvcResult.getResponse().getContentAsString();
        UserDto actualUser = mapper.readValue(responseBodyStr, UserDto.class);

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    @SneakyThrows
    void updateUser_shouldReturnBadRequest_whenUserIdDoNotMatch() {
        UserDto expectedUser = new UserDto();
        expectedUser.setId(1L);
        expectedUser.setName("new name");
        expectedUser.setEmail("changed_address@test.com");

        Mockito.when(userService.updateUser(expectedUser)).thenReturn(expectedUser);

        mockMvc.perform(
                patch("/users/2")
                        .content(mapper.writeValueAsString(expectedUser))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();
    }


    @Test
    @SneakyThrows
    void getUserById_shouldReturnOkAndUserDto() {
        UserDto expectedUser = new UserDto();
        expectedUser.setId(1L);
        expectedUser.setName("user");
        expectedUser.setEmail("address@test.com");

        Mockito.when(userService.getUserById(1L)).thenReturn(expectedUser);

        MvcResult mvcResult = mockMvc.perform(
                get("/users/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String responseBodyStr = mvcResult.getResponse().getContentAsString();
        UserDto actualUser = mapper.readValue(responseBodyStr, UserDto.class);

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    @SneakyThrows
    void deleteUser_shouldReturnOk() {
        mockMvc.perform(delete("/users/1")).andExpect(status().isOk()).andReturn();
    }
}