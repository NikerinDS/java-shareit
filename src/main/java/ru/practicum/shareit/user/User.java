package ru.practicum.shareit.user;

import lombok.Data;
import ru.practicum.shareit.utility.Identifiable;

@Data
public class User implements Identifiable {
    Long id;
    String name;
    String email;
}
