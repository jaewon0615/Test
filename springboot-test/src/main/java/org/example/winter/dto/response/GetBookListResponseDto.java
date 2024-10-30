package org.example.winter.dto.response;

// src/main/java/org/example/springboot_test/dto/response/GetBookListResponseDto.java


import java.util.List;

public class GetBookListResponseDto {
    private List<GetBookResponseDto> books;

    // 생성자
    public GetBookListResponseDto(List<GetBookResponseDto> books) {
        this.books = books;
    }

    // Getter
    public List<GetBookResponseDto> getBooks() {
        return books;
    }
}

