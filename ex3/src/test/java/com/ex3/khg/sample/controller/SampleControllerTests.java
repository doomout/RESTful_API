package com.ex3.khg.sample.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ex3.khg.support.TestTokenHelper;

@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestTokenHelper testTokenHelper;

    @Test
    public void testListWithJwtToken() throws Exception {
        mockMvc.perform(get("/api/v1/samples/list")
                        .with(testTokenHelper.bearerAuth("user1", "USER")))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"AAA\",\"BBB\",\"CCC\"]"));
    }
}
