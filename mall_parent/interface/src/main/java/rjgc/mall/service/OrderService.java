package rjgc.mall.service;

import rjgc.mall.entity.vo.Order;
import rjgc.mall.entity.vo.UserAndAction;

import java.util.List;

public interface OrderService {
    void confirmTrue(Integer id);
    void confirmFalse(Integer id);
    UserAndAction getTraders(Integer id);
    List<UserAndAction> getTraderOrderInfo(Integer id);
    Boolean checkUpdateExist(Integer id);
    Order getOrder(Integer id);
}
