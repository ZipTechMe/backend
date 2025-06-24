package com.jybeomss1.realestateauction.common.exceptions;

public class ExistUserException extends BaseException {
    public ExistUserException() {
        super(ErrorCode.EXIST_USER);
    }
}
