// package com.dev.pizza.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
// import com.dev.pizza.model.Item;
// import org.springframework.core.convert.converter.Converter;
// import org.springframework.lang.Nullable;
// import com.dev.pizza.repository.ItemRepository;

// @Component
// public class ItemService implements Converter<String, Item> {

//     private ItemRepository itemRepository;

//     @Autowired
//     public void ItemByIdConverter(ItemRepository itemRepository) {
//         this.itemRepository = itemRepository;
//     }

//     @Override
//     @Nullable
//     public Item convert(String id) {
//         return itemRepository.findById(Long.valueOf(id));
//     }
// }
