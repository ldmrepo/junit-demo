package com.example.junitdemo.util;

import org.springframework.stereotype.Component;

// 가짜!!
@Component // IoC 컨테이너 등록
public class MainSenderStub implements MailSender {

  @Override
  public boolean send() {
    return true;
  }
}
