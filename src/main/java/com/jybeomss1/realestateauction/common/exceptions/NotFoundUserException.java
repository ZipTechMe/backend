package com.jybeomss1.realestateauction.common.exceptions;

public class NotFoundUserException extends BaseException {
  public NotFoundUserException() {
    super(ErrorCode.NOT_FOUND_USER);
  }
}
