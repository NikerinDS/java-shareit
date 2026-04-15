package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemDto {
    Long id;

    @NotBlank(message = "Имя предмета не может быть пустым")
    String name;

    @NotBlank(message = "Имя предмета не может быть пустым")
    String description;

    @NotNull(message = "Доступность предмета должна быть заполнена")
    Boolean available;

    Long ownerId;

    Long requestId;
}
