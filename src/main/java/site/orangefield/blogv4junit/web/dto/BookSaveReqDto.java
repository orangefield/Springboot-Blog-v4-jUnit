package site.orangefield.blogv4junit.web.dto;

import lombok.Getter;
import lombok.Setter;
import site.orangefield.blogv4junit.domain.book.Book;

// MessageConverter는 누구를 때릴까? 생성자? Setter? - 내 블로그
@Getter // 본코드에서는 필요 없음(컨트롤러 테스트를 위한 것)
@Setter
public class BookSaveReqDto {
    private String title;
    private String author;

    public Book toEntity() {
        return Book.builder().title(this.title).author(this.author).build();
    }
}
