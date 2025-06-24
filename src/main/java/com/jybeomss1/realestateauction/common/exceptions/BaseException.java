package com.jybeomss1.realestateauction.common.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
}
