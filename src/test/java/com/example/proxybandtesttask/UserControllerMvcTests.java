package com.example.proxybandtesttask;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.proxybandtesttask.controller.UserController;
import com.example.proxybandtesttask.model.dto.UserRequestDto;
import com.example.proxybandtesttask.model.User;
import com.example.proxybandtesttask.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureWebMvc
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(classes = {UserController.class})
class UserControllerMvcTests {

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createUser_Ok() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("test@gmail.com");
        userRequestDto.setName("testName");
        User responseUser = new User();
        responseUser.setEmail(userRequestDto.getEmail());
        responseUser.setName(userRequestDto.getName());

        String request = objectMapper.writeValueAsString(userRequestDto);
        when(userService.create(
                userRequestDto.getName(), userRequestDto.getEmail())).thenReturn(responseUser);

        MvcResult mvcResult = mockMvc.perform(
                        post("/users/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isOk()).andReturn();
        assertNotNull(mvcResult.getResponse().getContentAsString());
        verify(userService, times(1))
                .create(userRequestDto.getName(), userRequestDto.getEmail());
    }

    @Test
    void updateUser_Ok() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("test@gmail.com");
        userRequestDto.setName("NewTestName");
        User responseUser = new User();
        responseUser.setEmail(userRequestDto.getEmail());
        responseUser.setName(userRequestDto.getName());
        String request = objectMapper.writeValueAsString(userRequestDto);

        when(userService.update(userRequestDto, "test@gmail.com")).thenReturn(responseUser);
        MvcResult mvcResult = mockMvc.perform(
                        put("/users/update?email=test@gmail.com")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isOk()).andReturn();
        assertNotNull(mvcResult);
        verify(userService, times(1)).update(userRequestDto, "test@gmail.com");
    }

    @Test
    void deleteUser_Ok() throws Exception {
        Mockito.doNothing().when(userService).delete("test@gmail.com");
        MvcResult mvcResult = mockMvc.perform(
                        delete("/users/delete?email=test@gmail.com")
                )
                .andExpect(status().isOk()).andReturn();
        assertNotNull(mvcResult);
        verify(userService, times(1)).delete("test@gmail.com");
    }

    @Test
    void getAllUsers_Ok() throws Exception {
        User existedUser = new User();
        existedUser.setId(476140939L);
        existedUser.setEmail("email1");
        existedUser.setName("Name");
        List<User> users = new ArrayList<>();
        users.add(existedUser);
        String request = objectMapper.writeValueAsString(users);
        when(userService.getAll()).thenReturn(users);
        MvcResult mvcResult = mockMvc.perform(
                        get("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isOk()).andReturn();
        assertNotNull(mvcResult);
        verify(userService, times(1)).getAll();
    }
}
