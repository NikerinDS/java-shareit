package ru.practicum.shareit.user;

import java.util.Optional;

public interface UserRepository {
    User create(User user);

    User update(User user);

    void delete(Long userId);

    Optional<User> getById(Long userId);

    boolean isEmailRegistered(String email);
}
