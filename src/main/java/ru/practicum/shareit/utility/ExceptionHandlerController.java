package ru.practicum.shareit.utility;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.utility.exceptions.EmailAlreadyRegisteredException;
import ru.practicum.shareit.utility.exceptions.NotFoundException;
import ru.practicum.shareit.utility.exceptions.WrongUserException;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final MethodArgumentNotValidException e) {
        return new ru.practicum.shareit.utility.ErrorResponse("Ошибка Валидации", e.getMessage());

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final WrongUserException e) {
        return new ru.practicum.shareit.utility.ErrorResponse("Ошибка Валидации", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleValidationException(final EmailAlreadyRegisteredException e) {
        return new ru.practicum.shareit.utility.ErrorResponse("Ошибка Валидации", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        return new ErrorResponse("Объект не найден", e.getMessage());
    }
}
