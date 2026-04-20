package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.utility.BaseInMemoryRepository;

@Repository
public class UserRepositoryInMemory extends BaseInMemoryRepository<User> implements UserRepository {
    @Override
    public boolean isEmailRegistered(String email) {
        return elements.values().stream().anyMatch(user -> user.getEmail().equals(email));
    }
}
