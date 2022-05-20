package com.yevgeniy.virtualclosetitemsservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class VirtualClosetItemsControllerTest {

    @Autowired
    MockMvc mockMvc;

    String exampleItemModelJson = "{\"name\":\"itemName\"}";

    @Test
    void createVirtualClosetItem() throws Exception {
        VirtualClosetItemModel item = new VirtualClosetItemModel();


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("http://localhost/" + VirtualClosetItemsController.ADD_ITEM_PATH)
                .accept(MediaType.APPLICATION_JSON).content(exampleItemModelJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertEquals("http://localhost/" + VirtualClosetItemsController.ADD_ITEM_PATH + "/1",
                response.getHeader(HttpHeaders.LOCATION));

    }
}
