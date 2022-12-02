package com.example.junitdemo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.junitdemo.domain.BookRepository;
import com.example.junitdemo.util.MailSender;
import com.example.junitdemo.web.dto.BookRespDto;
import com.example.junitdemo.web.dto.BookSaveDto;
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

  // 3. 책한건 보기

  // 4. 책삭제

  // 5. 책수정
}
