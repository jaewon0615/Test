package org.example.winter.dto.response;

// src/main/java/org/example/springboot_test/dto/response/GetBookResponseDto.java

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetBookResponseDto {
    private Long id;
    private String author;
    private String title;
    private String category;


}

