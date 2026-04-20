package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto);

    ItemDto updateItem(ItemDto itemDto);

    void deleteItem(Long itemId, Long userId);

    ItemDto getItemById(Long itemId);

    List<ItemDto> getItemsByOwnerId(Long ownerId);

    List<ItemDto> searchItemsByNameAndDescription(String searchString);
}
