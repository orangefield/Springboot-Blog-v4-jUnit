package site.orangefield.blogv4junit.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.orangefield.blogv4junit.domain.book.Book;
import site.orangefield.blogv4junit.domain.book.BookRepository;
import site.orangefield.blogv4junit.web.dto.BookRespDto;
import site.orangefield.blogv4junit.web.dto.BookSaveReqDto;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    // title, author = BookSaveReqDto
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto 책등록하기(BookSaveReqDto reqDto) {
        Book bookEntity = bookRepository.save(reqDto.toEntity());

        // Book 대신 Dto를 리턴하니까 Lazy Loading 발생X
        return new BookRespDto().toDto(bookEntity);
    }

    // @Transactional(readOnly = true) // 영속성 컨텍스트에서 변경감지를 하지 않게 한다 (고립성 공부)
    public BookRespDto 책한건가져오기(Long id) {
        // <findById 사용하고, BookRespDto로 응답해라>
        Optional<Book> bookOp = bookRepository.findById(id);

        if (bookOp.isPresent()) {
            Book bookEntity = bookOp.get();
            return new BookRespDto().toDto(bookEntity);
        } else {
            throw new RuntimeException("해당 책을 찾을 수 없습니다.");
        }
    }
}
