package site.orangefield.blogv4junit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import site.orangefield.blogv4junit.domain.book.Book;
import site.orangefield.blogv4junit.domain.book.BookRepository;
import site.orangefield.blogv4junit.web.dto.BookRespDto;
import site.orangefield.blogv4junit.web.dto.BookSaveReqDto;

// 서비스 테스트는 서비스 메서드를 때려야 stub이 발동한다
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks // Mockito 환경에 같이 떠있는 북레파지토리가 주입된다
    private BookService bookService;

    @Mock // 가짜로 테스트만 진행할 수 있게 객체를 만들어준다
    private BookRepository bookRepository; // = 본 코드를 보고 만든 가짜 레파지토리

    @Test
    public void 책등록하기_테스트() {
        // given
        BookSaveReqDto reqDto = new BookSaveReqDto();
        reqDto.setTitle("스프링부트");
        reqDto.setAuthor("필드쨩");

        // stub (행동 정의; 가설)
        Mockito.when(bookRepository.save(reqDto.toEntity())).thenReturn(new Book(1L, "스프링부트", "필드쨩"));

        // when
        BookRespDto respDto = bookService.책등록하기(reqDto);

        // then
        assertEquals(1L, respDto.getId());
        assertEquals("스프링부트", respDto.getTitle());
        assertEquals("필드쨩", respDto.getAuthor());
    }

    @Test
    public void 책한건가져오기_테스트() {

        Optional<Book> bookOp = Optional.of(new Book(1L, "스프링부트", "필드쨩"));

        // given
        Long id = 1L;

        // stub (행동 정의; 가설)
        Mockito.when(bookRepository.findById(id)).thenReturn(bookOp);

        // when
        BookRespDto respDto = bookService.책한건가져오기(id);

        // then
        assertEquals(1L, respDto.getId());
        assertEquals("스프링부트", respDto.getTitle());
        assertEquals("필드쨩", respDto.getAuthor());
    }

}
