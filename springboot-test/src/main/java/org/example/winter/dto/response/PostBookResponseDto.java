package org.example.winter.dto.response;

// src/main/java/org/example/springboot_test/dto/response/PostBookResponseDto.java

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostBookResponseDto {
    private Long id;
    private String author;
    private String title;
    private String category;

    // 생성자
    public PostBookResponseDto(Long id, String author, String title, String category) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.category = category;
    }

    // Getter 메서드들
    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }
}
