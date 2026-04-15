package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    Long id;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    String name;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Email не корректный")
    String email;
}
