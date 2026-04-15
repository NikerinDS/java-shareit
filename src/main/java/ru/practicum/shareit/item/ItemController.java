package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@Valid @RequestBody ItemDto itemDto,
                                              @RequestHeader("X-Sharer-User-Id") Long userId) {
        itemDto.setOwnerId(userId);
        log.info("Запрос на создание предмета:{}", itemDto);
        ItemDto createdUser = itemService.createItem(itemDto);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemDto> updateItem(@RequestBody ItemDto itemDto,
                                              @PathVariable Long id,
                                              @RequestHeader("X-Sharer-User-Id") Long userId) {
        itemDto.setId(id);
        itemDto.setOwnerId(userId);
        log.info("Запрос на обновление предмета:{}", itemDto);
        ItemDto updateItem = itemService.updateItem(itemDto);
        return new ResponseEntity<>(updateItem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id,
                           @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Запрос на удаление предмета с id:{} от пользователя с id:{}", id, userId);
        itemService.deleteItem(id, userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable Long id) {
        log.info("Запрос на получение предмета с id:{}", id);
        return new ResponseEntity<>(itemService.getItemById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<ItemDto>> getItemsByOwnerId(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Запрос на получение списка собственных предметов от пользователя с id:{}", userId);
        return new ResponseEntity<>(itemService.getItemsByOwnerId(userId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> searchItemsByNameAndDescription(@RequestParam String text) {
        log.info("Поиск предметов по строке:{}", text);
        if (text.isBlank()) return new ResponseEntity<>(List.of(), HttpStatus.OK);
        return new ResponseEntity<>(itemService.searchItemsByNameAndDescription(text), HttpStatus.OK);
    }
}
