package rjgc.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rjgc.mall.common.MessageConstant;
import rjgc.mall.common.Result;
import rjgc.mall.entity.TradeAction;
import rjgc.mall.entity.User;
import rjgc.mall.entity.param.BuyParam;
import rjgc.mall.entity.param.OrderInfoParam;
import rjgc.mall.entity.vo.UserAndAction;
import rjgc.mall.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户购买时填写用户信息
     * 最后返回
     * @param buyParam
     * user       用户姓名和电话号码
     * tradeTime  交易时间 前端传进来的形式 : yyyy-MM-dd hh-mm-ss
     * tradePlace 交易地点
     * goodsId    商品编号
     * @return
     */
    @RequestMapping("/buy")
    public Result buy(@RequestBody BuyParam buyParam) {
        Map<String,Integer> map = userService.buy2(buyParam.getUser(), buyParam.getTradeTime(), buyParam.getTradePlace(),buyParam.getGoodsId());
        return new Result(true, MessageConstant.GOODS_BUY_SUCCESS,map.get("orderId"));
    }

}
