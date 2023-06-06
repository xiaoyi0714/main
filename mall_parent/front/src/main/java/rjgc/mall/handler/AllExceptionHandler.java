package rjgc.mall.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rjgc.mall.common.MessageConstant;
import rjgc.mall.common.Result;
import rjgc.mall.service.exception.ServiceException;
import rjgc.mall.service.exception.goods.GoodsRemoveFailException;
import rjgc.mall.service.exception.goods.PutawayFailException;
import rjgc.mall.service.exception.user.GoodsBuyFailException;

//对controller注解的方法进行AOP拦截
@RestControllerAdvice
public class AllExceptionHandler {
    @ExceptionHandler({ServiceException.class,Exception.class})
    public Result handleException(Exception e) {
        e.printStackTrace();
        if(e instanceof GoodsRemoveFailException) {
            return new Result(false, MessageConstant.GOODS_REMOVE_FAIL);
        } else if (e instanceof PutawayFailException) {
            return new Result(false,MessageConstant.GOODS_PUTAWAY_FAIL);
        } else if (e instanceof GoodsBuyFailException) {
            return new Result(false,MessageConstant.GOODS_BUY_FAIL);
        } else {
            return new Result(false,MessageConstant.SYSTEM_ERROR);
        }
    }
}
