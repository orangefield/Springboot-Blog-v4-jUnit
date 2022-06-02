package site.orangefield.blogv4junit.web;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.orangefield.blogv4junit.service.BookService;
import site.orangefield.blogv4junit.web.dto.BookRespDto;
import site.orangefield.blogv4junit.web.dto.BookSaveReqDto;

/**
 * 방법3
 */

@WebMvcTest(BookApiController.class) // BookApiController를 IoC에 실제로 띄운다
public class BookApiControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc; // IoC가 들고 있음

    @MockBean // 그래서 DI도 IoC에 해야 한다 (Mock가 아니라)
    private BookService bookService;

    @Test
    public void bookSave_테스트() throws Exception {
        // given
        BookSaveReqDto reqDto = new BookSaveReqDto();
        reqDto.setTitle("제목1");
        reqDto.setAuthor("필드쨩");

        String body = new ObjectMapper().writeValueAsString(reqDto); // json으로 변환
        System.out.println("===========================");
        System.out.println(body);
        System.out.println("===========================");

        // stub
        Mockito.when(bookService.책등록하기(reqDto)).thenReturn(new BookRespDto(1L, "제목1", "필드쨩"));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/book")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON) // 나는 이런 데이터로 돌려받기를 기대합니다
        );

        // then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isCreated())
                // .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("제목1")) // $ =
                // json의 최고 root
                // .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("필드쨩"))
                .andDo(MockMvcResultHandlers.print()); // 마지막에 로그를 쫙 보여줌
    }

}
