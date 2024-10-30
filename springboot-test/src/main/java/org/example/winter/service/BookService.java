package org.example.winter.service;

import lombok.RequiredArgsConstructor;

import org.example.winter.common.constant.ResponseMessage;
import org.example.winter.dto.response.GetBookResponseDto;
import org.example.winter.dto.response.PostBookResponseDto;

import org.example.winter.entity.Book;

import org.example.winter.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // 1. 게시글 생성(Post)
    public Book<GetBookResponseDto> createBook(GetBookResponseDto requestDto) {
        Book book = new Book(
                null, requestDto.getTitle(), requestDto.getAuthor(),
                 requestDto.getCategory()
        );

        Book savedBook = bookRepository.save(book);
        return GetBookResponseDto.Success(ResponseMessage.SUCCESS, convertToResponseDto(savedBook));
   }

    // 2. 전체 책 조회
    public List<GetBookResponseDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                // .map((book) -> convertToResponseDto(book))
                .collect(Collectors.toList());
    }

    // 3. 특정 ID 책 조회
    public GetBookResponseDto getBookById(Long id) {
        try {
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다: " + id));
            return convertToResponseDto(book);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new GetBookResponseDto();
        }
    }
//
//    // 3-1. 제목에 특정 단어가 포함된 책 조회
//    public List<BookResponseDto> getBooksByTitleContaining(String keyword) {
//        List<Book> books = bookRepository.findByTitleContaining(keyword);
//        return books.stream()
//                .map(this::convertToResponseDto)
//                .collect(Collectors.toList());
//    }
//
//    // 3-2. 카테고리별 책 조회
//    public List<BookResponseDto> getBooksByCategory(Category category) {
//        return bookRepository.findByCategory(category)
//                .stream()
//                .map(this::convertToResponseDto)
//                .collect(Collectors.toList());
//    }
//
//    // 3-3. 카테고리 & 작성자별 책 조회
//    public List<BookResponseDto> getBooksByCategoryAndWriter(Category category, String writer) {
//        List<Book> books;
//
//        if (category == null) {
//            books = bookRepository.findByWriter(writer);
//        } else {
//            books = bookRepository.findByCategoryAndWriter(category, writer);
//        }
//
//        return books.stream()
//                .map(this::convertToResponseDto)
//                .collect(Collectors.toList());
//    }
//
//    // 4, 특정 ID 책 수정
//    public BookResponseDto updateBook(Long id, BookRequestUpdateDto updateDto) {
//        Book book = bookRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다: " + id));
//
//        book.setBook_title(updateDto.getTitle());
//        book.setBook_author(updateDto.getAuthor());
//        book.setCategory(updateDto.getCategory());
//
//        Book updatedBook = bookRepository.save(book);
//        return convertToResponseDto(updatedBook);
//    }
//
//    // 5. 책 삭제
//    public void deleteBook(Long id) {
//        bookRepository.deleteById(id);
//    }

    // Entity -> Response Dto 변환
    private GetBookResponseDto convertToResponseDto(Book book) {
        return new GetBookResponseDto(
                book.getId(), book.getAuthor(),book.getTitle()
                , book.getCategory()
        );
    }

    public Book createBook(GetBookResponseDto requestDto) {
    }
}