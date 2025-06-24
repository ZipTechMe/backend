package com.jybeomss1.realestateauction.common.exceptions;

public class InvalidatedTokenException extends BaseException {
    public InvalidatedTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
