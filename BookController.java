package org.example.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.common.constant.ApiMappingPattern;
import org.example.springbootdeveloper.dto.request.BookRequestDto;
import org.example.springbootdeveloper.dto.request.BookRequestUpdateDto;
import org.example.springbootdeveloper.dto.response.BookResponseDto;
import org.example.springbootdeveloper.dto.response.ResponseDto;
import org.example.springbootdeveloper.entity.Category;
import org.example.springbootdeveloper.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// 각 메서드는 Json을 반환한다

@RequestMapping(ApiMappingPattern.BOOK)
// ApiMappingPattern에 정의된 기본 url 경로를 지정해준다
@RequiredArgsConstructor
// 초기화 되지 않은 final 필드나 @NonNull이 붙은 필드에 대해 생성자를 생성
public class BookController {
    // Service 객체를 주입 받아 사용하는 변수
    private final BookService bookService;

    // 생성자 주입 - RequiredArgsConstructor로 대체
//    public BookController(BookService bookService) {
//        this.bookService = bookService;
//    }

    // 책 생성
    @PostMapping
    // Post의 요청을 받아 책을 생성

    // createBook 메서드는 책 생성 요청을 처리하며, ResponseEntity<ResponseDto<BookResponseDto>> 형태로 응답한다
    public ResponseEntity<ResponseDto<BookResponseDto>> createBook(@RequestBody BookRequestDto requestDto) {

        ResponseDto<BookResponseDto> result = bookService.createBook(requestDto);
        // bookService.createBook(requestDto)를 호출해 책을 생성한 후 결과를 result에 저장한다
        return ResponseEntity.status(HttpStatus.OK).body(result);
        // http의 형태로 반환한다
    }

    // 전체 책 조회  시험 사용
    @GetMapping
    // 기본 경로로 요청이 들어오면
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        // getAllBooks 전체 책을 조회하는 메서드이다
        List<BookResponseDto> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
        //getAllBooks 를 사용하여 모든 책을 조회하고 그 결과를 books에 저장한다
    }

    // 단건 책 조회
    @GetMapping("/{id}")
    // 아이디를 사용하여 특정 책을 조회할수 있는 기능이다
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id) {
        BookResponseDto book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    // getBookById는 주어진 아이디에 해당하는 책을 조회하여 PathVariable을 사용하여 값을 전달 받는다
        // getBookById(id)을 사용하여 조회하고 결과를 book에 저장한다

    }

    // 제목에 특정 단어가 포함된 책 조회
    @GetMapping("/search/title")
    public ResponseEntity<List<BookResponseDto>> getBooksByTitleContaining(
            @RequestParam String keyword
    ) {
        List<BookResponseDto> books = bookService.getBooksByTitleContaining(keyword);
        return ResponseEntity.ok(books);
    }
    // 제목에 특정 단어가 포함된 책을 조회하는 기능으로 ResponseEntity<List<BookResponseDto>> 형식으로 응답한다
    // bookService.getBooksByTitleContaining(keyword) 호출하여 특정 단어를 포함하는 책을 조회하고 books 에 저장한다

    // 카테고리별 책 조회
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BookResponseDto>> getBooksByCategory(@PathVariable Category category) {
        List<BookResponseDto> books = bookService.getBooksByCategory(category);
        return ResponseEntity.ok(books);

        // 책을 카테고리 별로 조회하는 기능으로 getBooksByCategory메서드를 사용하여 카테고리별로 조회한다
        // 카테고리에 해당하는 책을 조회하여 books 에 저장한다
    }

    // 카테고리와 작성자별 책 조회
    @GetMapping("/search/category-writer")
    public ResponseEntity<List<BookResponseDto>> getBooksByCategoryAndWriter(
            @RequestParam(required = false) Category category,
            @RequestParam String writer
    ) {
        List<BookResponseDto> books = bookService.getBooksByCategoryAndWriter(category, writer);
        return ResponseEntity.ok(books);

        // 카테고리와 작성자로 책을 조회하는 기능으로
        // getBooksByCategoryAndWriter 주어진 카테고리와 작성자로 책을 조회한다
        // bookService.getBooksByCategoryAndWriter(category, writer) 를 호출하여 책을 조회하고 그 결과를 books 에 저장한다
    }

    // 특정 id 책 수정
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(
            @PathVariable Long id, @RequestBody BookRequestUpdateDto requestDto
    ) {
        BookResponseDto updatedBook = bookService.updateBook(id, requestDto);
        return ResponseEntity.ok(updatedBook);
        // 특정 아이디를 사용하여 단건의 책을 수정하는 기능으로
        // PathVariable을 사용하여 url의 경로를 이용하여 책에 접근하여 책을 수정한다
        // 결과를 updateBook 에 저장한다
    }

    // 특정 id 책 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
        // 특정 id를 사용하여 책에 접근하여 특정 책만을 삭제하는 기능이다
    }
}