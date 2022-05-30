package site.orangefield.blogv4junit.domain.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // 테스트를 정해놓은 순서대로 실행
@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩, h2 동작, 자동 롤백
public class BookRepositoryTest {

    @Autowired // DI
    private BookRepository bookRepository;

    @Autowired
    private EntityManager em;

    // 메서드 실행시마다 auto_increment 초기화
    @BeforeEach
    public void db_init() {
        bookRepository.deleteAll();
        em
                .createNativeQuery("ALTER TABLE book ALTER COLUMN id RESTART WITH 1")
                .executeUpdate();
    }

    // C:유효성검사 필요 [X]
    @Test
    @Order(1)
    public void save_test() {
        // given
        String title = "스프링부트";
        String author = "도라에몽";
        Book book = Book.builder().title(title).author(author).build();

        // when
        Book bookEntity = bookRepository.save(book);

        // then (verify)
        assertEquals(title, bookEntity.getTitle());
        assertEquals(author, bookEntity.getAuthor());
        assertEquals(1, bookEntity.getId());

    } // Rollback

    @Test
    @Order(2)
    public void findById_test() {
        // given
        String title = "더미제목";
        String author = "더미작가";
        Book book = Book.builder().title(title).author(author).build();
        bookRepository.save(book);

        Long id = 1L;

        // when
        Optional<Book> bookOp = bookRepository.findById(id);

        // then
        if (bookOp.isPresent()) {
            Book bookEntity = bookOp.get();

            assertEquals(title, bookEntity.getTitle());
            assertEquals(author, bookEntity.getAuthor());
            assertEquals(1, bookEntity.getId());

        } else {
            assertNotNull(bookOp.get()); // Not Null이냐? -> Null 들어왔음 -> false -> 테스트 실패
        }
    }

}
