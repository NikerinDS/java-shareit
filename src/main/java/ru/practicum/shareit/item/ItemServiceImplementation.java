package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.utility.exceptions.NotFoundException;
import ru.practicum.shareit.utility.exceptions.WrongUserException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImplementation implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemDto createItem(ItemDto itemDto) {
        userRepository.getById(itemDto.getOwnerId())
                .orElseThrow(() -> new NotFoundException("Не найден пользователь с id=" + itemDto.getOwnerId()));
        return ItemDtoMapper.fromItem(itemRepository.create(ItemDtoMapper.toItem(itemDto)));
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto) {
        Item oldItem = itemRepository.getById(itemDto.getId())
                .orElseThrow(() -> new NotFoundException("Не найден предмет с id=" + itemDto.getId()));
        userRepository.getById(itemDto.getOwnerId())
                .orElseThrow(() -> new NotFoundException("Не найден пользователь с id=" + itemDto.getOwnerId()));
        if (!oldItem.getOwnerId().equals(itemDto.getOwnerId())) {
            throw new WrongUserException("Пользователь может изменять только свои предметы");
        }
        if (itemDto.getName() == null || itemDto.getName().isBlank()) {
            itemDto.setName(oldItem.getName());
        }
        if (itemDto.getDescription() == null || itemDto.getDescription().isBlank()) {
            itemDto.setDescription(oldItem.getDescription());
        }
        if (itemDto.getAvailable() == null) {
            itemDto.setAvailable(oldItem.getAvailable());
        }
        return ItemDtoMapper.fromItem(itemRepository.update(ItemDtoMapper.toItem(itemDto)));
    }

    @Override
    public void deleteItem(Long itemId, Long userId) {
        Item item = itemRepository.getById(itemId)
                .orElseThrow(() -> new NotFoundException("Не найден предмет с id=" + itemId));
        if (!item.getOwnerId().equals(userId)) {
            throw new WrongUserException("Пользователь может удалять только свои предметы");
        }
        itemRepository.delete(itemId);
    }

    @Override
    public ItemDto getItemById(Long itemId) {
        return ItemDtoMapper.fromItem(itemRepository.getById(itemId)
                .orElseThrow(() -> new NotFoundException("Не найден предмет с id=" + itemId)));
    }

    @Override
    public List<ItemDto> getItemsByOwnerId(Long ownerId) {
        userRepository.getById(ownerId)
                .orElseThrow(() -> new NotFoundException("Не найден пользователь с id=" + ownerId));
        return itemRepository.getByOwnerId(ownerId).stream().map(ItemDtoMapper::fromItem).toList();
    }

    @Override
    public List<ItemDto> searchItemsByNameAndDescription(String searchString) {
        return itemRepository.search(searchString).stream().map(ItemDtoMapper::fromItem).toList();
    }
}
