package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user);

    void deleteUser(Long userId);

    UserDto getUserById(Long userId);
}
