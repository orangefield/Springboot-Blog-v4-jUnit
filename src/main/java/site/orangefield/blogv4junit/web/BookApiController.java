package site.orangefield.blogv4junit.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.orangefield.blogv4junit.service.BookService;
import site.orangefield.blogv4junit.web.dto.BookRespDto;
import site.orangefield.blogv4junit.web.dto.BookSaveReqDto;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService;

    @PostMapping("/api/book")
    public ResponseEntity<?> bookSave(@RequestBody BookSaveReqDto reqDto) {
        BookRespDto respDto = bookService.책등록하기(reqDto);
        return new ResponseEntity<>(respDto, HttpStatus.CREATED);
    }

    // 대표적 - 정상(200), insert(201), 인증안됨(403), 클라이언트요청잘못(404), 서버쪽에러(500)
    @GetMapping("/api/book/{id}")
    public ResponseEntity<?> bookFindOne(@PathVariable Long id) {
        BookRespDto respDto = bookService.책한건가져오기(id);
        // return ResponseEntity.ok().body(respDto); 이렇게 쓸 수도 있지만 쓸 수 있는 상태코드가 적다
        return new ResponseEntity<>(respDto, HttpStatus.OK);
    }
}
