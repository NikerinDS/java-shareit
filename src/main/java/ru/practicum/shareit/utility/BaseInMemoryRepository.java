package ru.practicum.shareit.utility;

import java.util.HashMap;
import java.util.Optional;

public class BaseInMemoryRepository<T extends Identifiable> {
    protected final HashMap<Long, T> elements = new HashMap<>();
    private Long nextId = 1L;

    public T create(T newElement) {
        newElement.setId(getNextId());
        elements.put(newElement.getId(), newElement);
        return newElement;
    }

    public T update(T newElement) {
        elements.put(newElement.getId(), newElement);
        return newElement;
    }

    public void delete(Long elementId) {
        elements.remove(elementId);
    }

    public Optional<T> getById(Long elementId) {
        return Optional.ofNullable(elements.get(elementId));
    }

    private Long getNextId() {
        return nextId++;
    }
}
