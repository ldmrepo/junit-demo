package com.example.junitdemo.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
public class BookRepositoryTest {

  @Autowired
  private BookRepository bookRepository;

  // 데이터 준비
  @BeforeEach
  public void 데이터준비() {
    Book book = Book.builder().title("junit5").author("홍길동").build();
    bookRepository.save(book);
  }

  // 1. 책등록
  @Test
  public void 책등록_Test() {
    // given
    Book book = Book.builder().title("junit").author("이동명").build();

    // when
    Book bookPS = bookRepository.save(book);

    // then
    assertEquals(bookPS.getTitle(), book.getTitle());
    assertEquals(bookPS.getAuthor(), book.getAuthor());
  }

  // 2. 책 목록보기
  @Test
  public void 책목록_Test() {
    // given
    String title = "junit5";
    String author = "홍길동";

    // when
    List<Book> books = bookRepository.findAll();

    // then
    assertEquals(books.get(0).title, title);
    assertEquals(books.get(0).author, author);
  }

  // 3. 책 한건보기
  @Sql("classpath:db/tableinit.sql")
  @Test
  public void 책한건보기_Test() {
    // given
    String title = "junit5";
    String author = "홍길동";

    // when
    Optional<Book> bookPS = bookRepository.findById(1L);

    // then
    assertTrue(bookPS.isPresent());
    assertEquals(bookPS.get().title, title);
    assertEquals(bookPS.get().author, author);
  }

  // 4. 책 삭제
  @Sql("classpath:db/tableinit.sql")
  @Test
  public void 책삭제_Test() {
    // given
    Long id = 1L;

    // when
    bookRepository.deleteById(id);

    // then
    Optional<Book> bookPS = bookRepository.findById(id);
    assertFalse(bookPS.isPresent());
  }

  // 5. 책 수정
  @Sql("classpath:db/tableinit.sql")
  @Test
  public void 책수정_Test() {
    // given
    Book book = Book.builder().id(1L).title("junit6").author("홍길순").build();

    // when
    Book bookPS = bookRepository.save(book);

    // then
    assertEquals(bookPS.getId(), book.getId());
    assertEquals(bookPS.getTitle(), book.getTitle());
    assertEquals(bookPS.getAuthor(), book.getAuthor());
  }
}
