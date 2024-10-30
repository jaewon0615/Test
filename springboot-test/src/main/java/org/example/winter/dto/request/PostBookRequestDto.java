package org.example.winter.dto.request;

// src/main/java/com/example/demo/dto/PostBookResponseDto.java

public class PostBookRequestDto {
    private Long id;
    private String author;
    private String title;
    private String category;

    public PostBookRequestDto(Long id, String author, String title, String category) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.category = category;
    }

    // Getter 추가
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
