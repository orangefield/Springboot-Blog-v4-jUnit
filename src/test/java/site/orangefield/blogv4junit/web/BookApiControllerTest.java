package site.orangefield.blogv4junit.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import site.orangefield.blogv4junit.domain.book.Book;
import site.orangefield.blogv4junit.domain.book.BookRepository;
import site.orangefield.blogv4junit.web.dto.BookSaveReqDto;

/**
 * <컨트롤러를 테스트하는 방법>
 * 1. 실제환경과 동일하게 테스트 (@SpringbootTest):모든 것이 메모리에 다 올라감 = 통합테스트
 * 2. 내가 원하는 컨트롤러, 서비스, 레파지토리만 분리해서 메모리에 올리고 테스트
 * (@SpringbootTest(class={BookApiController.class, BookService.class,
 * BookRepository.class}))
 * 3. Controller, ControllerAdvice, Filter, WebMvcConfigurer(WEB.xml 비슷) 테스트
 */

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {

    @Autowired
    private TestRestTemplate rt;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void bookSave_테스트() throws JsonProcessingException {
        // given
        BookSaveReqDto reqDto = new BookSaveReqDto();
        reqDto.setTitle("제목1");
        reqDto.setAuthor("필드쨩");

        String body = new ObjectMapper().writeValueAsString(reqDto); // json으로 변환
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        // when
        // json으로 응답받기 때문에 String으로 받는다
        ResponseEntity<String> response = rt.exchange("/api/book", HttpMethod.POST, request, String.class);

        // then
        DocumentContext dc = JsonPath.parse(response.getBody()); // DocumentContext : json 분석 도구
        String author = dc.read("$.author");
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("필드쨩", author);
    }

    @Test
    public void bookFindOne_테스트() {
        // given
        Long id = 1L;
        bookRepository.save(new Book(1L, "더미제목1", "더미작가1"));

        // when
        ResponseEntity<String> response = rt.exchange("/api/book/" + id, HttpMethod.GET, null, String.class);

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        String author = dc.read("$.author");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("더미작가1", author);
    }

}
