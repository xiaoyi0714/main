package rjgc.mall.service.exception.goods;

import rjgc.mall.service.exception.ServiceException;

public class GoodsOnlyOneException extends ServiceException {
    public GoodsOnlyOneException() {
        super();
    }

    public GoodsOnlyOneException(String message) {
        super(message);
    }

    public GoodsOnlyOneException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoodsOnlyOneException(Throwable cause) {
        super(cause);
    }

    protected GoodsOnlyOneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
