package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.utility.BaseInMemoryRepository;

import java.util.List;

@Repository
public class ItemRepositoryInMemory extends BaseInMemoryRepository<Item> implements ItemRepository {
    @Override
    public List<Item> getByOwnerId(Long ownerId) {
        return elements.values().stream().filter(item -> item.getOwnerId().equals(ownerId)).toList();
    }

    @Override
    public List<Item> search(String searchString) {
        return elements.values().stream()
                .filter(Item::getAvailable)
                .filter(item -> item.getName().toLowerCase().contains(searchString.toLowerCase())
                                || item.getDescription().toLowerCase().contains(searchString.toLowerCase()))
                .toList();
    }
}
