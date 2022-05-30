package site.orangefield.blogv4junit.web.dto;

import site.orangefield.blogv4junit.domain.book.Book;

public class BookRespDto {
    private Long id;
    private String title;
    private String author;

    public BookRespDto toDto(Book bookEntity) {
        this.id = bookEntity.getId();
        this.title = bookEntity.getTitle();
        this.author = bookEntity.getAuthor();
        return this;
    }

    // User를 받고 싶다면?
    // UserRespDto를 따로 만들어서 받아야 한다
    // private UserRespDto user;

}
