package ru.practicum.shareit.item;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.practicum.shareit.item.dto.ItemDto;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ItemController.class})
class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    ItemService itemService;


    @Test
    @SneakyThrows
    void createItem_shouldReturnOkAndItemDtoWithId() {
        ItemDto newItem = new ItemDto();
        newItem.setName("item");
        newItem.setDescription("test item");
        newItem.setAvailable(true);
        newItem.setOwnerId(1L);

        ItemDto expectedItem = new ItemDto();
        expectedItem.setId(1L);
        expectedItem.setName("item");
        expectedItem.setDescription("test item");
        expectedItem.setAvailable(true);
        expectedItem.setOwnerId(1L);

        Mockito.when(itemService.createItem(newItem)).thenReturn(expectedItem);

        MvcResult mvcResult = mockMvc.perform(
                post("/items")
                        .content(mapper.writeValueAsString(newItem))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", "1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String responseBodyStr = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ItemDto actualItem = mapper.readValue(responseBodyStr, ItemDto.class);

        Assertions.assertEquals(expectedItem, actualItem);
    }

    @Test
    @SneakyThrows
    void createItem_shouldReturnBadRequest_whenNameIsEmpty() {
        ItemDto newItem = new ItemDto();
        newItem.setName("");
        newItem.setDescription("test item");
        newItem.setAvailable(true);
        newItem.setOwnerId(1L);

        mockMvc.perform(
                post("/items")
                        .content(mapper.writeValueAsString(newItem))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", "1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @SneakyThrows
    void createItem_shouldReturnBadRequest_whenDescriptionIsEmpty() {
        ItemDto newItem = new ItemDto();
        newItem.setName("item");
        newItem.setDescription("");
        newItem.setAvailable(true);
        newItem.setOwnerId(1L);

        mockMvc.perform(
                post("/items")
                        .content(mapper.writeValueAsString(newItem))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", "1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @SneakyThrows
    void createItem_shouldReturnBadRequest_whenAvailableIsEmpty() {
        ItemDto newItem = new ItemDto();
        newItem.setName("item");
        newItem.setDescription("test item");
        newItem.setOwnerId(1L);

        mockMvc.perform(
                post("/items")
                        .content(mapper.writeValueAsString(newItem))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", "1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @SneakyThrows
    void createItem_shouldReturnBadRequest_whenHeaderUserIdIsAbsent() {
        ItemDto newItem = new ItemDto();
        newItem.setName("item");
        newItem.setDescription("test item");
        newItem.setAvailable(true);
        newItem.setOwnerId(1L);

        mockMvc.perform(
                post("/items")
                        .content(mapper.writeValueAsString(newItem))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @SneakyThrows
    void updateItem_shouldReturnOkAndItemDto() {
        ItemDto expectedItem = new ItemDto();
        expectedItem.setId(1L);
        expectedItem.setName("item");
        expectedItem.setDescription("test item");
        expectedItem.setAvailable(true);
        expectedItem.setOwnerId(1L);

        Mockito.when(itemService.updateItem(expectedItem)).thenReturn(expectedItem);

        MvcResult mvcResult = mockMvc.perform(
                patch("/items/1")
                        .content(mapper.writeValueAsString(expectedItem))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", "1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String responseBodyStr = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ItemDto actualItem = mapper.readValue(responseBodyStr, ItemDto.class);

        Assertions.assertEquals(expectedItem, actualItem);
    }

    @Test
    @SneakyThrows
    void updateItem_shouldReturnBadRequest_whenHeaderUserIdIsAbsent() {
        ItemDto expectedItem = new ItemDto();
        expectedItem.setId(1L);
        expectedItem.setName("item");
        expectedItem.setDescription("test item");
        expectedItem.setAvailable(true);
        expectedItem.setOwnerId(1L);

        Mockito.when(itemService.updateItem(expectedItem)).thenReturn(expectedItem);

        mockMvc.perform(
                patch("/items/1")
                        .content(mapper.writeValueAsString(expectedItem))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    @SneakyThrows
    void deleteItem_shouldReturnOk() {
        mockMvc.perform(delete("/items/1").header("X-Sharer-User-Id", "1")).andExpect(status().isOk()).andReturn();
    }

    @Test
    @SneakyThrows
    void deleteItem_shouldReturnBadRequest_whenHeaderUserIdIsAbsent() {
        mockMvc.perform(delete("/items/1")).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @SneakyThrows
    void getItemById_shouldReturnOkAndItemDto() {
        ItemDto expectedItem = new ItemDto();
        expectedItem.setId(1L);
        expectedItem.setName("item");
        expectedItem.setDescription("test item");
        expectedItem.setAvailable(true);
        expectedItem.setOwnerId(1L);

        Mockito.when(itemService.getItemById(1L)).thenReturn(expectedItem);

        MvcResult mvcResult = mockMvc.perform(
                get("/items/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String responseBodyStr = mvcResult.getResponse().getContentAsString();
        ItemDto actualItem = mapper.readValue(responseBodyStr, ItemDto.class);

        Assertions.assertEquals(expectedItem, actualItem);
    }

    @Test
    @SneakyThrows
    void getItemsByOwnerId_shouldReturnOkAndListOfItemDto() {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("item");
        itemDto.setDescription("test item");
        itemDto.setAvailable(true);
        itemDto.setOwnerId(1L);

        Mockito.when(itemService.getItemsByOwnerId(1L)).thenReturn(List.of(itemDto));

        MvcResult mvcResult = mockMvc.perform(
                get("/items")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("X-Sharer-User-Id", "1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String responseBodyStr = mvcResult.getResponse().getContentAsString();
        List<ItemDto> actualItems = mapper.readValue(responseBodyStr, new TypeReference<>(){});

        Assertions.assertIterableEquals(List.of(itemDto), actualItems);
    }

    @Test
    @SneakyThrows
    void getItemsByOwnerId_shouldReturnBadRequest_whenHeaderUserIdIsAbsent() {
        mockMvc.perform(
                get("/items")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @SneakyThrows
    void searchItemsByNameAndDescription_shouldReturnOkAndListOfItemDto() {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("item");
        itemDto.setDescription("test item");
        itemDto.setAvailable(true);
        itemDto.setOwnerId(1L);

        Mockito.when(itemService.searchItemsByNameAndDescription("test")).thenReturn(List.of(itemDto));

        MvcResult mvcResult = mockMvc.perform(
                get("/items/search?text=test")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String responseBodyStr = mvcResult.getResponse().getContentAsString();
        List<ItemDto> actualItems = mapper.readValue(responseBodyStr, new TypeReference<>(){});

        Assertions.assertIterableEquals(List.of(itemDto), actualItems);
    }

    @Test
    @SneakyThrows
    void searchItemsByNameAndDescription_shouldReturnOkAndEmptyLis_whenTextIsEmpty() {
        MvcResult mvcResult = mockMvc.perform(
                get("/items/search?text=")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String responseBodyStr = mvcResult.getResponse().getContentAsString();
        List<ItemDto> actualItems = mapper.readValue(responseBodyStr, new TypeReference<>(){});

        Assertions.assertTrue(actualItems.isEmpty());
    }
}