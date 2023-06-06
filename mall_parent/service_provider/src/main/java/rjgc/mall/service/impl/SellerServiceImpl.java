package rjgc.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rjgc.mall.entity.Goods;
import rjgc.mall.entity.Seller;
import rjgc.mall.entity.TradeAction;
import rjgc.mall.entity.User;
import rjgc.mall.mapper.GoodsMapper;
import rjgc.mall.mapper.SellerMapper;
import rjgc.mall.mapper.TradeActionMapper;
import rjgc.mall.service.GoodsService;
import rjgc.mall.service.SellerService;
import rjgc.mall.service.exception.goods.GoodsOnlyOneException;
import rjgc.mall.service.exception.seller.PasswordNotMatchException;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerMapper sellerMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private TradeActionMapper tradeActionMapper;

    @Override
    public void publishGoods(String goodsName,
                             String goodsImgUrl,
                             String goodsDescription,
                             Float goodsPrice,
                             String goodsType,
                             Integer status,
                             Integer supply_id) {
        List<Goods> goods = goodsMapper.selectGoods(supply_id);
        if(goods.size() == 1 && !goods.isEmpty()){
            throw new GoodsOnlyOneException("只能上架一个商品");
        }
        sellerMapper.publishGoods(goodsName,
                goodsImgUrl,
                goodsDescription,
                goodsPrice,
                goodsType,
                status,
                supply_id, new Date()) ;
    }

    @Override
    public Seller Sellerlogin(String account) {
        Seller seller=sellerMapper.Sellerlogin(account);
        return seller;
    }

    @Override
    public void Passwordupdate(String account, String password,String oldPassword) {
        if(sellerMapper.checkPassword(account, oldPassword) == 0){
            System.out.println(sellerMapper.checkPassword(account, oldPassword));
            throw new PasswordNotMatchException("密码不正确");
        }
        sellerMapper.Passwordupdate(account, password);
    }


    @Override
    public User checkUser(Integer user_id) {
        User user=sellerMapper.checkUser(user_id);
        return user;
    }
    @Override
    public void Register(String account,String password,String name){
       sellerMapper.Register(account,password,name);
    }
    @Override
    public List<TradeAction> intendingUser(Integer goods_id)
    {
        return tradeActionMapper.intendingUser(goods_id);
    }

    @Override
    public User getintendingUser(Integer id) {
        return  sellerMapper.getintendingUser(id);
    }

    @Override
    public void chooseUser(Integer id,Integer goods_id) {
        sellerMapper.chooseUser(id);
        goodsMapper.chooseUser(goods_id);
    }

    @Override
    public boolean registerComfirm() {
        int x=sellerMapper.registerComfirm();
        System.out.println(x);
        if(x==0){
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public List<String> Buyerinfo(Integer id) {
        List<String> infoList= sellerMapper.Buyerinfo(id);
        return infoList;
    }

    @Override
    public Integer checkgoods() {
        return  goodsMapper.checkgood();
    }

    @Override
    public Integer getTradecount(Integer goodsId) {
        return  tradeActionMapper.getTradecount(goodsId);
    }


}
