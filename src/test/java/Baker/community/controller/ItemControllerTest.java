package Baker.community.controller;

import Baker.community.constant.ItemType;
import Baker.community.dto.AddItemDto;
import Baker.community.dto.UpdateItemDto;
import Baker.community.entity.Item;
import Baker.community.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();

        itemRepository.deleteAll();
    }


    @Test
    @DisplayName("레시피 추가 테스트")
    public void add() throws Exception {
        //given
        final String url = "/recipe/new";
        final ItemType itemType = ItemType.BREAD;
        final String name = "name";
        final String info = "info";
        final String material = "material";
        final String recipe = "recipe";
        final AddItemDto addItemDto = new AddItemDto(itemType, name, info, material, recipe);

        final String addItem = objectMapper.writeValueAsString(addItemDto);
        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(addItem));
        // then
        result.andExpect(status().isCreated());

        List<Item> items = itemRepository.findAll();

        assertThat(items.size()).isEqualTo(1);
        assertThat(items.get(0).getItemType()).isEqualTo(ItemType.BREAD);
        assertThat(items.get(0).getItemName()).isEqualTo(name);
        assertThat(items.get(0).getInfo()).isEqualTo(info);
        assertThat(items.get(0).getMaterial()).isEqualTo(material);
        assertThat(items.get(0).getRecipe()).isEqualTo(recipe);
    }

    @Test
    @DisplayName("레시피 조회 테스트")
    public void findAll() throws Exception {
        // given
        final String url = "/recipe/new";
        final ItemType itemType = ItemType.BREAD;
        final String name = "name";
        final String info = "info";
        final String material = "material";
        final String recipe = "recipe";

        itemRepository.save(Item.builder()
                .itemType(itemType)
                .itemName(name)
                .info(info)
                .material(material)
                .recipe(recipe)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].itemType").value(ItemType.BREAD))
                .andExpect(jsonPath("$[0].itemName").value(name))
                .andExpect(jsonPath("$[0].info").value(info))
                .andExpect(jsonPath("$[0].material").value(material))
                .andExpect(jsonPath("$[0].recipe").value(recipe));
    }

    @Test
    @DisplayName("레시피 수정 테스트")
    public void updateArticle() throws Exception {
        // given
        final String url = "/recipe/new/{id}";
        final ItemType itemType = ItemType.BREAD;
        final String name = "name";
        final String info = "info";
        final String material = "material";
        final String recipe = "recipe";

        Item savedArticle = itemRepository.save(Item.builder()
                .itemType(itemType)
                .itemName(name)
                .info(info)
                .material(material)
                .recipe(recipe)
                .build());

        final ItemType newType = ItemType.COOKIE;
        final String newName = "new name";
        final String newInfo = "new info";
        final String newMaterial = "new Material";
        final String newRecipe = "new recipe";

        UpdateItemDto request = new UpdateItemDto(newType,newName, newInfo, newMaterial, newRecipe);

        // when
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Item item = itemRepository.findById(savedArticle.getId()).get();

        assertThat(item.getItemType()).isEqualTo(ItemType.COOKIE);
        assertThat(item.getItemName()).isEqualTo(newName);
        assertThat(item.getInfo()).isEqualTo(newInfo);
        assertThat(item.getMaterial()).isEqualTo(newMaterial);
        assertThat(item.getRecipe()).isEqualTo(newRecipe);
    }

    }