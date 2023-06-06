package rjgc.mall.service.exception.goods;

import rjgc.mall.service.exception.ServiceException;

public class GoodsRemoveFailException extends ServiceException {
    public GoodsRemoveFailException() {
        super();
    }

    public GoodsRemoveFailException(String message) {
        super(message);
    }

    public GoodsRemoveFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoodsRemoveFailException(Throwable cause) {
        super(cause);
    }

    protected GoodsRemoveFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
