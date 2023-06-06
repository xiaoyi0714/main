package rjgc.mall.service.exception.goods;

import rjgc.mall.service.exception.ServiceException;

import javax.xml.ws.Service;

public class PutawayFailException extends ServiceException {
    public PutawayFailException() {
        super();
    }

    public PutawayFailException(String message) {
        super(message);
    }

    public PutawayFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public PutawayFailException(Throwable cause) {
        super(cause);
    }

    protected PutawayFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
