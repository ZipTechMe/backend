package com.jybeomss1.realestateauction.common.exceptions;

public class RevokedTokenException extends BaseException {
    public RevokedTokenException() {
        super(ErrorCode.REVOKED_TOKEN);
    }
}
