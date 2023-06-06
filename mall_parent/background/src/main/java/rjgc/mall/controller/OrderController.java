package rjgc.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rjgc.mall.common.MessageConstant;
import rjgc.mall.common.Result;
import rjgc.mall.entity.vo.UserAndAction;
import rjgc.mall.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/Order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @RequestMapping("/confirmOrderChange")
    public Result confirmOrderChange(Boolean flag,Integer orderId){
        if(flag){
            orderService.confirmTrue(orderId);
        }else {
            orderService.confirmFalse(orderId);
        }
        return new Result(true, MessageConstant.CONFIRM_SUCCESS);
    }

    /**
     * 获得交易人的信息
     * @param id 商品编号
     * @return
     */
    @RequestMapping("/getTraders")
    public Result getTraders(Integer id){
        UserAndAction userAndAction = orderService.getTraders(id);
        return new Result(true,"成功了",userAndAction);
    }

    /**
     * 获得修改前后的信息
     * @param id 商品编号
     * @return
     */
    @RequestMapping("/getTraderOrderInfo")
    public Result getTraderOrderInfo(Integer id){
        List<UserAndAction> userAndActions = orderService.getTraderOrderInfo(id);
        return new Result(true,"成功了",userAndActions);
    }


}
