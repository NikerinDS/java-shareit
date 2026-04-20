package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoMapper;
import ru.practicum.shareit.utility.exceptions.EmailAlreadyRegisteredException;
import ru.practicum.shareit.utility.exceptions.NotFoundException;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.isEmailRegistered(userDto.getEmail())) {
            throw new EmailAlreadyRegisteredException("Пользователь с такой почтой уже зарегистрирован");
        }
        return UserDtoMapper.fromUser(userRepository.create(UserDtoMapper.toUser(userDto)));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User oldUser = userRepository.getById(userDto.getId())
                .orElseThrow(() -> new NotFoundException("Не найден пользователь с id=" + userDto.getId()));
        if (userDto.getName() == null || userDto.getName().isBlank()) {
            userDto.setName(oldUser.getName());
        }
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            userDto.setEmail(oldUser.getEmail());
        } else if (!userDto.getEmail().equals(oldUser.getEmail())
                   && userRepository.isEmailRegistered(userDto.getEmail())) {
            throw new EmailAlreadyRegisteredException("Пользователь с такой почтой уже зарегистрирован");
        }

        return UserDtoMapper.fromUser(userRepository.update(UserDtoMapper.toUser(userDto)));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден пользователь с id=" + userId));
        userRepository.delete(userId);
    }

    @Override
    public UserDto getUserById(Long userId) {
        return UserDtoMapper.fromUser(userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден пользователь с id=" + userId)));
    }
}
