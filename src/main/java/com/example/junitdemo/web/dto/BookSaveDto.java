package com.example.junitdemo.web.dto;

import com.example.junitdemo.domain.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookSaveDto {

  private String title;
  private String author;

  @Builder
  public BookSaveDto(String title, String author) {
    this.title = title;
    this.author = author;
  }

  public Book toEntity() {
    return Book.builder().title(title).author(author).build();
  }
}
