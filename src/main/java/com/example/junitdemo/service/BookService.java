package com.example.junitdemo.service;

import com.example.junitdemo.domain.Book;
import com.example.junitdemo.domain.BookRepository;
import com.example.junitdemo.util.MailSender;
import com.example.junitdemo.web.dto.BookRespDto;
import com.example.junitdemo.web.dto.BookSaveDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.management.RuntimeErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookService {

  private final BookRepository bookRepository;

  private final MailSender mailSender;

  // 1. 책등록
  @Transactional(rollbackFor = RuntimeException.class)
  public BookRespDto 책등록하기(BookSaveDto dto) {
    Book bookPS = bookRepository.save(dto.toEntity());
    if (bookPS != null) {
      if (!mailSender.send()) {
        throw new RuntimeException("메일이 전송되지 않았습니다.");
      }
    }
    return new BookRespDto().toDto(bookPS);
  }

  // 2. 책목록 보기
  public List<BookRespDto> 책목록보기() {
    return bookRepository
      .findAll()
      .stream()
      .map(book -> {
        return new BookRespDto().toDto(book);
      })
      .collect(Collectors.toList());
  }

  // 3. 책한건보기
  public BookRespDto 책한건보기(Long id) {
    Optional<Book> op = bookRepository.findById(id);
    if (op.isPresent()) {
      return new BookRespDto().toDto(op.get());
    } else {
      throw new RuntimeException("책을 찾을 수 없습니다.");
    }
  }

  // 4. 책삭제
  @Transactional(rollbackFor = RuntimeException.class)
  public void 책삭제(Long id) {
    // ... 여기에 여러가지 트랜잭션이 있을경우
    // 전체 롤백처리 @Transactional
    bookRepository.deleteById(id);
  }

  // 5. 책수정
  @Transactional(rollbackFor = RuntimeException.class)
  public void 책삭제(Long id, BookSaveDto dto) {
    Optional<Book> bookPS = bookRepository.findById(id);
    if (bookPS.isPresent()) {
      Book book = bookPS.get();
      book.setTitle(dto.getTitle());
      book.setAuthor(dto.getAuthor());
    } else {
      throw new RuntimeException("해당 책을 찾을 수 없습니다.");
    }
  } // 메소드 종료시 더티체킹(flush)으로 update 됩니다.
}
