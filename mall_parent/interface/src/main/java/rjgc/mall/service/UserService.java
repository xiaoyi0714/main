package rjgc.mall.service;

import org.apache.ibatis.annotations.Mapper;
import rjgc.mall.entity.User;
import rjgc.mall.entity.param.OrderInfoParam;

import java.util.Map;

public interface UserService {
    public void buy(User user,String tradeTime, String tradePlace,Integer goodsId);
    public void freezeGoods(Integer goodsId);
    public void updateOrderInfo(OrderInfoParam orderInfoParam);
    public Map<String,Integer> buy2(User user, String tradeTime, String tradePlace, Integer goodsId);
}
