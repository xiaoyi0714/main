package rjgc.mall.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rjgc.mall.common.MessageConstant;
import rjgc.mall.common.Result;
import rjgc.mall.service.exception.ServiceException;
import rjgc.mall.service.exception.goods.GoodsOnlyOneException;
import rjgc.mall.service.exception.seller.PasswordNotMatchException;

@RestControllerAdvice
public class AllExceptionHandler {
    @ExceptionHandler({ServiceException.class,Exception.class})
    public Result handleException(Exception e) {
        e.printStackTrace();
        if(e instanceof GoodsOnlyOneException) {
            return new Result(false, MessageConstant.PUBLISH_GOODS_ONLYONE);
        } else if (e instanceof PasswordNotMatchException) {
            return new Result(false, MessageConstant.PASSWORDNOTMATCH);
        } else {
            return new Result(false,MessageConstant.SYSTEM_ERROR);
        }
    }
}
