package ru.practicum.shareit.item;

import lombok.Data;
import ru.practicum.shareit.utility.Identifiable;

@Data
public class Item implements Identifiable {
    Long id;
    String name;
    String description;
    Boolean available;
    Long ownerId;
    Long requestId;
}
