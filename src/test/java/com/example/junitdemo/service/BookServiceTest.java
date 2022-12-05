package com.example.junitdemo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.junitdemo.domain.Book;
import com.example.junitdemo.domain.BookRepository;
import com.example.junitdemo.util.MailSender;
import com.example.junitdemo.web.dto.BookRespDto;
import com.example.junitdemo.web.dto.BookSaveDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
  @InjectMocks
  private BookService bookService;

  @Mock
  private BookRepository bookRepository;

  @Mock
  private MailSender mailSender;

  // 1. 책등록
  @Test
  public void 책등록_Test() {
    // given
    BookSaveDto dto = BookSaveDto
      .builder()
      .title("junit")
      .author("홍길동")
      .build();

    // stub
    // ???? when(bookRepository.save(dto.toEntity())).thenReturn(dto.toEntity());
    when(bookRepository.save(any())).thenReturn(dto.toEntity());
    when(mailSender.send()).thenReturn(true);

    // when
    BookRespDto bookRespDto = bookService.책등록하기(dto);

    // then
    assertEquals(bookRespDto.getTitle(), dto.getTitle());
    assertEquals(bookRespDto.getAuthor(), dto.getAuthor());
  }

  // 2. 책목록 보기
  @Test
  public void 책목록보기_Test() {
    // given

    // stub
    List<Book> books = new ArrayList<>();
    books.add(new Book(1L, "junit1", "홍길도"));
    books.add(new Book(2L, "junit2", "김길동"));
    when(bookRepository.findAll()).thenReturn(books);

    // when
    List<BookRespDto> dtos = bookService.책목록보기();

    dtos.stream().forEach(b -> System.out.println(b.getTitle()));

    // then
    assertThat(dtos.get(0).getTitle()).isEqualTo("junit1");
    assertThat(dtos.get(0).getAuthor()).isEqualTo("홍길도");
    assertThat(dtos.get(1).getTitle()).isEqualTo("junit2");
    assertThat(dtos.get(1).getAuthor()).isEqualTo("김길동");
  }

  // 3. 책한건 보기
  @Test
  public void 책한건보기_Test() {
    // given
    Long id = 1L;

    // stub
    Book book = new Book(id, "junit6", "홍길동");
    Optional<Book> bookOp = Optional.of(book);

    when(bookRepository.findById(1L)).thenReturn(bookOp);

    // when
    BookRespDto bookDto = bookService.책한건보기(id);

    // then
    assertThat(bookDto.getTitle()).isEqualTo(book.getTitle());
  }

  // 4. 책삭제

  // 5. 책수정
  @Test
  public void 책수정_Test() {
    // given
    Long id = 1L;
    BookSaveDto dto = new BookSaveDto("junit5", "홍길동");

    // stub
    Book book = new Book(1L, "junit4", "홍길동");
    Optional<Book> bookOp = Optional.of(book);
    when(bookRepository.findById(id)).thenReturn(bookOp);

    // when
    BookRespDto bookDto = bookService.책수정(id, dto);

    // then
    assertThat(bookDto.getTitle()).isEqualTo(dto.getTitle());
  }
}
