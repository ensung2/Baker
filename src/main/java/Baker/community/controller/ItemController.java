package Baker.community.controller;

import Baker.community.dto.AddItemDto;
import Baker.community.dto.ItemDto;
import Baker.community.dto.UpdateItemDto;
import Baker.community.entity.Item;
import Baker.community.service.ItemImgService;
import Baker.community.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemImgService itemImgService;

    @PostMapping("/recipe/new")
    public ResponseEntity<Item> addItem(@RequestBody AddItemDto addItemDto) {
        Item savedItem = itemService.save(addItemDto);
        /*
         * 200 OK : 요청이 성공적으로 수행되었음
         * 201 CREATED : 요청이 성공적으로 수행되었고, 새로운 리소스가 생성되었음
         * 400 BAD REQUEST : 요청 값이 잘못되어 요청에 실패했음
         * 403 FORBIDDEN : 권한이 없어 요청에 실패했음
         * 404 NOT FOUND : 요청 값으로 찾은 리소스가 없어 요청에 실패했음
         * 500 INTERNAL SERVER ERROR : 서버 상에 문제가 있어 요청에 실패했음
         */
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedItem);
    }

    @GetMapping("/recipe/new")
    public ResponseEntity<List<ItemDto>> findAllItem() {
        List<ItemDto> itemDtos = itemService.findAll()
                .stream()
                .map(ItemDto::new)
                .toList();

        return ResponseEntity.ok()
                .body(itemDtos);

    }

    @GetMapping("/recipe/new/{id}")
    public ResponseEntity<ItemDto> findArticle(@PathVariable long id) {
        Item item = itemService.findById(id);

        return ResponseEntity.ok()
                .body(new ItemDto(item));
    }

    @DeleteMapping("/list/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable long id) {
        itemService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/recipe/new/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable long id,
                                           @RequestBody UpdateItemDto updateItem) {
        Item updatedItem = itemService.updateItem(id, updateItem);

        return ResponseEntity.ok()
                .body(updatedItem);
    }
}
