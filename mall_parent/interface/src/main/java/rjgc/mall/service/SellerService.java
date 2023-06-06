package rjgc.mall.service;

import rjgc.mall.entity.Seller;
import rjgc.mall.entity.TradeAction;
import rjgc.mall.entity.User;

import java.util.List;

public interface SellerService {
    public void publishGoods(String goodsName,
                             String goodsImgUrl,
                             String goodsDescription,
                             Float goodsPrice,
                             String goodsType,
                             Integer status,
                             Integer supply_id);

    public Seller Sellerlogin(String account);

    public void Passwordupdate(String account, String password,String oldPassword);


    public User checkUser(Integer user_id);

    public void Register(String account , String password,String name);

    public List<TradeAction> intendingUser(Integer goods_id);

    public User getintendingUser(Integer id);

    public void chooseUser(Integer id,Integer goods_id);

    public boolean registerComfirm();

    public List<String> Buyerinfo(Integer id);

    public Integer checkgoods();

    public Integer getTradecount(Integer goodsId);
}
