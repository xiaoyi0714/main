package rjgc.mall.service.exception.user;

import rjgc.mall.service.exception.ServiceException;

public class GoodsBuyFailException extends ServiceException {
    public GoodsBuyFailException() {
        super();
    }

    public GoodsBuyFailException(String message) {
        super(message);
    }

    public GoodsBuyFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoodsBuyFailException(Throwable cause) {
        super(cause);
    }

    protected GoodsBuyFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
