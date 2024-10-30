package org.example.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.example.springbootdeveloper.common.constant.ResponseMessage;
import org.example.springbootdeveloper.dto.request.BookRequestDto;
import org.example.springbootdeveloper.dto.request.BookRequestUpdateDto;
import org.example.springbootdeveloper.dto.response.BookResponseDto;
import org.example.springbootdeveloper.dto.response.ResponseDto;
import org.example.springbootdeveloper.entity.Book;
import org.example.springbootdeveloper.entity.Category;
import org.example.springbootdeveloper.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // 1. 게시글 생성(Post)
    public ResponseDto<BookResponseDto> createBook(BookRequestDto requestDto) {
        //createBook 메서드는 BookRequestDto를 매개변수로 받아 새로운 책을 생성하고, 결과를 ResponseDto<BookResponseDto> 형태로 반환한다
        Book book = new Book(
                null, requestDto.getWriter(), requestDto.getTitle(),
                requestDto.getContent(), requestDto.getCategory()
                // requestDto.getWriter(), requestDto.getTitle(), requestDto.getContent(), requestDto.getCategory()는 클라이언트로부터 전달된 요청 데이터에서 작성자, 제목, 내용, 카테고리 정보를 가져와 초기화 시킨다
        );

        Book savedBook = bookRepository.save(book);
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, convertToResponseDto(savedBook));

        //esponseMessage.SUCCESS는 성공 메시지를 나타내며, convertToResponseDto(savedBook)는 저장된 savedBook 객체를 BookResponseDto로 변환한다
    }

    // 2. 전체 책 조회
    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                // .map((book) -> convertToResponseDto(book))
                .collect(Collectors.toList());
        // getAllBooks 메서드는 BookResponseDto를 매개변수로 받아 모든 책을 조회하고 그 결과를
        // bookRepository.findAll() 형식으로 반환한다
    }

    // 3. 특정 ID 책 조회
    public BookResponseDto getBookById(Long id) {
        // getBookById 메서드는 Long 타입의 id를 매개변수로 받아 해당 ID에 대한 책 정보를 조회하고, BookResponseDto 형태로 반환한다
        try {
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다: " + id));
            return convertToResponseDto(book);
            // bookRepository.findById(id)를 사용하여 id에 해당하는 책을 찾느다
            //책이 없다면 , orElseThrow 메서드를 사용하여 에러 메세지를을 보낸다.
            //찾은 책에 대한 내용은 book에 저장한다
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new BookResponseDto();

        }
    }

    // 3-1. 제목에 특정 단어가 포함된 책 조회
    public List<BookResponseDto> getBooksByTitleContaining(String keyword) {
        //getBooksByTitleContaining 메서드는  keyword를 매개변수로 받아, 키워드를 포함하는 제목을 가진 책들을 조회한다
        List<Book> books = bookRepository.findByTitleContaining(keyword);
        // bookRepository.findByTitleContaining(keyword)를 사용하여 제목을사용하여  책 목록을 데이터베이스에서 조회한다
        return books.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        // .map(this::convertToResponseDto)은 엔터티를 rsponseDto 로 변환하난 역할을 한다
        // collect(Collectors.toList()); 는 결과를 리스트 형태로 수집하여 반환한다

    }

    // 3-2. 카테고리별 책 조회
    public List<BookResponseDto> getBooksByCategory(Category category) {
        //getBooksByCategory 메서드는 카테고리를 매개변수로 받아 각 카테고리에 포함된 책들을 조회한다
        return bookRepository.findByCategory(category)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        //.map(this::convertToResponseDto)은 엔터티를 rsponseDto 로 변환하난 역할을 한다
        // collect(Collectors.toList()); 는 결과를 리스트 형태로 수집하여 반환한다
    }

    // 3-3. 카테고리 & 작성자별 책 조회
    public List<BookResponseDto> getBooksByCategoryAndWriter(Category category, String writer) {
        // getBooksByCategoryAndWriter 메서드는 C category와 writer를 매개변수로 받아, 해당 카테고리와 작성자에 사용하여 책들을 조회한다
        List<Book> books;
        // books 를 저장하는 데 사용한다

        if (category == null) {
            books = bookRepository.findByWriter(writer);
        } else {
            books = bookRepository.findByCategoryAndWriter(category, writer);
        }
        // 카테고리가 빈값이라면 작성자에 따른 책만 조회한다
        // 그게 아니라면 ookRepository.findByCategoryAndWriter(category, writer)를 사용하여 주어진 카테고리와 작성자에 해당하는 책 목록을 조회한다

        return books.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // 4, 특정 ID 책 수정
    public BookResponseDto updateBook(Long id, BookRequestUpdateDto updateDto) {
        // updateBook 메서드는 id와  updateDto를 매개변수로 받아, 해당 ID의 책 정보를 수정하고, 결과를 BookResponseDto 형태로 반환한다
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다: " + id));
       // bookRepository.findById(id)를 호출하여 주어진 id에 해당하는 책을 조회한다

        book.setTitle(updateDto.getTitle());
        book.setContent(updateDto.getContent());
        book.setCategory(updateDto.getCategory());

        //updateDto에서 제목, 내용, 카테고리 정보를 가져와서 각각의 book 을 수정한다

        Book updatedBook = bookRepository.save(book);
        return convertToResponseDto(updatedBook);
        // bookRepository.save(book)를 호출하여 업데이트된 book 객체를 저장한다
        //convertToResponseDto(updatedBook)를 호출하여 업데이트된 updatedBook 객체를 BookResponseDto 형태로 변경 후 반환 한다
    }

    // 5. 책 삭제
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
        // deleteBook 메서드는  id를 매개변수로 받아, 해당 ID에 대한 책을 삭제한다
        // void 형식이므로 반환하지 않는다
    }


    // Entity -> Response Dto 변환
    private BookResponseDto convertToResponseDto(Book book) {
        return new BookResponseDto(
                book.getId(), book.getWriter(), book.getTitle()
                , book.getContent(), book.getCategory()
        );
    }
}