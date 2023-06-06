package rjgc.mall.service.impl;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rjgc.mall.entity.Goods;
import rjgc.mall.entity.OrderUpdate;
import rjgc.mall.entity.TradeAction;
import rjgc.mall.entity.User;
import rjgc.mall.entity.vo.Order;
import rjgc.mall.entity.vo.UserAndAction;
import rjgc.mall.mapper.GoodsMapper;
import rjgc.mall.mapper.OrderUpdateMapper;
import rjgc.mall.mapper.TradeActionMapper;
import rjgc.mall.mapper.UserMapper;
import rjgc.mall.service.OrderService;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderUpdateMapper orderUpdateMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TradeActionMapper tradeActionMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 卖家确定该交易行为
     * 1、更新交易行为
     * 2、更新用户信息
     * 3、删除order_update中修改信息
     * 4、冻结商品
     * @param id 用户订单的编号
     */
    @Override
    public void confirmTrue(Integer id) {
        OrderUpdate orderUpdate = orderUpdateMapper.selectById(id);
        TradeAction tradeAction = new TradeAction();
        tradeAction.setId(orderUpdate.getId());
        tradeAction.setTradeTime(orderUpdate.getTrade_time());
        tradeAction.setTradeTime(orderUpdate.getTrade_place());
        tradeActionMapper.updateByPrimaryKeySelective(tradeAction);
        User user = new User();
        user.setId(tradeActionMapper.getUserId(orderUpdate.getId()));
        user.setName(orderUpdate.getName());
        user.setTele(orderUpdate.getTele());
        userMapper.updateByPrimaryKeySelective(user);
        orderUpdateMapper.deleteByid(id);
        goodsMapper.feezeGoods(tradeActionMapper.getGoodsId(id));
    }

    /**
     * 商家拒绝用户的修改
     * @param id 用户订单的编号
     */
    @Override
    public void confirmFalse(Integer id) {
        orderUpdateMapper.deleteByid(id);
    }

    /**
     * 根据订单编号获取交易者信息
     * @param id
     * @return
     */
    @Override
    public UserAndAction getTraders(Integer id) {
        Integer userId = tradeActionMapper.getGoodsId2(id);
        TradeAction tradeAction = tradeActionMapper.getActopm(id);
        User user = userMapper.selectByPrimaryKey(userId);
        UserAndAction userAndAction = new UserAndAction();
        userAndAction.setName(user.getName());
        userAndAction.setTele(user.getTele());
        userAndAction.setTradeTime(tradeAction.getTradeTime());
        userAndAction.setTradePlace(tradeAction.getTradePlace());
        return userAndAction;
    }

    @Override
    public List<UserAndAction> getTraderOrderInfo(Integer id) {
        Integer orderId = tradeActionMapper.getActionId(id);
        TradeAction tradeAction = tradeActionMapper.selectByPrimaryKey(orderId);
        List<UserAndAction> userAndActions = new ArrayList<>();
        UserAndAction userAndAction1 = new UserAndAction();
        userAndAction1.setTradeTime(tradeAction.getTradeTime());
        userAndAction1.setTradePlace(tradeAction.getTradePlace());
        User user = userMapper.selectByPrimaryKey(tradeAction.getUserId());
        userAndAction1.setName(user.getName());
        userAndAction1.setTele(user.getTele());
        userAndAction1.setOrderId(tradeAction.getId());
        userAndActions.add(userAndAction1);
        OrderUpdate orderUpdate = orderUpdateMapper.selectById(orderId);
        UserAndAction userAndAction2 = new UserAndAction();
        userAndAction2.setTradePlace(orderUpdate.getTrade_place());
        userAndAction2.setTradeTime(orderUpdate.getTrade_time());
        userAndAction2.setName(orderUpdate.getName());
        userAndAction2.setTele(orderUpdate.getTele());
        userAndAction2.setOrderId(orderUpdate.getId());
        userAndActions.add(userAndAction2);
        return userAndActions;
    }

    @Override
    public Boolean checkUpdateExist(Integer id) {
        Integer orderId = tradeActionMapper.getActionId(id);
        if(tradeActionMapper.selectByPrimaryKey(orderId) == null){
            return false;
        }
        return true;
    }

    /**
     * 根据订单编号获取用户的订单信息
     * @param id
     * @return
     */
    @Override
    public Order getOrder(Integer id) {
        TradeAction tradeAction = tradeActionMapper.selectByPrimaryKey(id);
        User user = userMapper.selectByPrimaryKey(tradeAction.getUserId());
        Goods goods = goodsMapper.selectByPrimaryKey(tradeAction.getGoodsId());
        Order order = null;
        if(user != null && tradeAction != null && goods != null){
            order = new Order();
            order.setTradeAction(tradeAction);
            order.setUser(user);
            order.setGoods(goods);
        }
        return order;
    }
}
