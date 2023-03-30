package com.yoona.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.apache.logging.log4j.MarkerManager.exists;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;
    // 1. 웹 api 테스트할 때 사용
    // 2. 스프링 mvc 테스트의 시작점
    // 3. 이 클래스를 통해 HTTP, GET, POST 등에 대한 API 테스트를 할 수 있음

    @Test
    public void hello() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello"))
        //MockMvc를 통해 /hello 주소로 HTTP GET 요청을 한다.

                .andExpect(status().isOk())
                //mvc.perform의 결과를 검증
                //HTTP Header의 Status 검증
                //200, 404, 500 등의 상태를 검증
                //여기선, Ok 즉, 200인지 아닌지를 검증

                .andExpect(content().string(hello));
                //mvc.perform의 결과 검증
                //응답 본문의 내용을 검증
                //Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증
    }

    @Test
    public void helloDto_return() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name",name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(result ->  jsonPath("$.name", is(name)))
                .andExpect(result ->  jsonPath("$.amount", is(amount)));
    }
}