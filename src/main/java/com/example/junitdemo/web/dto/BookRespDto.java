package com.example.junitdemo.web.dto;

import com.example.junitdemo.domain.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BookRespDto {

  private Long id;
  private String title;
  private String author;

  @Builder
  public BookRespDto(Long id, String title, String author) {
    this.id = id;
    this.title = title;
    this.author = author;
  }

  public BookRespDto toDto(Book book) {
    this.id = book.getId();
    this.title = book.getTitle();
    this.author = book.getAuthor();
    return this;
  }
}
