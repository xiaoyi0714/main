package rjgc.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rjgc.mall.common.MessageConstant;
import rjgc.mall.common.Result;
import rjgc.mall.entity.vo.Order;
import rjgc.mall.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping("/getOrder")
    public Result getOrder(Integer id){
        Order order = orderService.getOrder(id);
        return new Result(true, MessageConstant.SELECT_ORDER,order);
    }
}
