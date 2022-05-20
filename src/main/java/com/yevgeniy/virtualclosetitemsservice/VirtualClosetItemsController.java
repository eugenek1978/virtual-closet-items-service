package com.yevgeniy.virtualclosetitemsservice;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Controller
public class VirtualClosetItemsController {

    public static final String ADD_ITEM_PATH = "api/virtual-closet/items" ;

    @PostMapping(ADD_ITEM_PATH)
    public ResponseEntity<Void> addItem(@Valid VirtualClosetItemModel item){
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(1).toUri();
        return ResponseEntity.created(location).build();
    }

}
