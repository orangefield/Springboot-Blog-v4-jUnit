package site.orangefield.blogv4junit.web.dto;

import lombok.Setter;
import site.orangefield.blogv4junit.domain.book.Book;

// MessageConverter는 누구를 때릴까? 생성자? Setter? - 내 블로그
@Setter
public class BookSaveReqDto {
    private String title;
    private String author;

    public Book toEntity() {
        return new Book(this.title, this.author);
    }
}
