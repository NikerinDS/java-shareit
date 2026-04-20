package ru.practicum.shareit.item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Item create(Item item);

    Item update(Item item);

    void delete(Long itemId);

    Optional<Item> getById(Long itemId);

    List<Item> getByOwnerId(Long ownerId);

    List<Item> search(String searchString);
}
