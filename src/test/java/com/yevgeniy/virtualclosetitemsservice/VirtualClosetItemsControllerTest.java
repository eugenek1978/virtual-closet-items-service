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

import static com.yevgeniy.virtualclosetitemsservice.VirtualClosetItemModel.NAME_IS_MANDATORY;
import static com.yevgeniy.virtualclosetitemsservice.VirtualClosetItemsController.ADD_ITEM_PATH;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VirtualClosetItemsControllerTest {

    private static final String BASE_URL = "http://localhost/";

    @Autowired
    MockMvc mockMvc;

    String exampleItemModelJson = "{\"name\":\"itemName\"}";
    String emptyItemModelJson = "{}";

    @Test
    void createVirtualClosetItemTest() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(BASE_URL + ADD_ITEM_PATH)
                .accept(MediaType.APPLICATION_JSON).content(exampleItemModelJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertEquals(BASE_URL + ADD_ITEM_PATH + "/1",
                response.getHeader(HttpHeaders.LOCATION));

    }

    @Test
    void createVirtualClosetItem_emptyInput_failAllMandatoryValidations() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(BASE_URL + ADD_ITEM_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .content(emptyItemModelJson)
                .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(requestBuilder)
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                    .andExpect(jsonPath("$.status", is(400)))
                    .andExpect(jsonPath("$.errors").isArray())
                    .andExpect(jsonPath("$.errors", hasSize(1)))
                    .andExpect(jsonPath("$.errors", hasItem(NAME_IS_MANDATORY)));

    }

}
