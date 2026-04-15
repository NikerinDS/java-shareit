package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.utility.exceptions.WrongUserException;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Запрос на создание пользователя:{}", userDto);
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Long id) {
        if (userDto.getId() == null) {
            userDto.setId(id);
        } else if (!userDto.getId().equals(id)) {
            throw new WrongUserException("Не совпадает id пользователя в адресе запроса(" +
                                         id + ") и в теле запроса(" + userDto.getId() + ")");
        }
        log.info("Запрос на обновление пользователя:{}", userDto);
        UserDto updatedUser = userService.updateUser(userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        log.info("Запрос на получение пользователя по id:{}", id);
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        log.info("Запрос на удаление пользователя по id:{}", id);
        userService.deleteUser(id);
    }
}
