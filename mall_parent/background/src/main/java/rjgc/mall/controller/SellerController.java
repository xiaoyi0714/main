package rjgc.mall.controller;

import org.apache.ibatis.annotations.DeleteProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rjgc.mall.common.MessageConstant;
import rjgc.mall.common.MySessionContext;
import rjgc.mall.common.Result;
import rjgc.mall.entity.Buyerinfo;
import rjgc.mall.entity.Seller;
import rjgc.mall.entity.TradeAction;
import rjgc.mall.entity.User;
import rjgc.mall.entity.vo.RegisterParam;
import rjgc.mall.mapper.TradeActionMapper;
import rjgc.mall.service.SellerService;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    HttpServletRequest request;

    @Autowired
    private TradeActionMapper tradeActionMapper;
    @RequestMapping("/publishGoods")
    /*
    ******发布商品*******
     *
     */
    public Result publishGoods (@RequestHeader("token") String token,
                           String goodsName,
                           @RequestParam("img") MultipartFile img,
                           String goodsDescription,
                           Float goodsPrice,
                           String goodsType) throws IOException {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String encode = base64Encoder.encode(img.getBytes());
        Integer status=0;
        MySessionContext myContext = MySessionContext.getInstance();
        HttpSession session = myContext.getSession(token);
        Seller seller=(Seller) session.getAttribute("seller");
        Integer supply_id = (Integer) seller.getId();
        sellerService.publishGoods(goodsName,encode,goodsDescription,goodsPrice,goodsType,status,supply_id);
        return new Result(true,MessageConstant.GOODS_PUBLISH_SUCCESS);
    }
    /*
    ***商家登录*****
    * */
    @RequestMapping("/Sellerlogin")
    public Result Sellerlogin(
                              String account,
                              String password){
        Seller seller = sellerService.Sellerlogin(account);
        if(seller==null){
            return new Result(false,MessageConstant.SELLER_ACCOUNT_NON_EXISTENT);
        }
        else if(!seller.getPassword().equals(password)){
            return new Result(false,MessageConstant.SELLER_PASSWORD_WRONG);
        }
        else{
            HttpSession session = request.getSession();
            session.setAttribute("seller",seller);
            MySessionContext myContext = MySessionContext.getInstance();
            myContext.AddSession(session);
            return new Result(true,MessageConstant.SELLER_LOGIN_SUCCESS,session.getId());
        }
    }
/***********修改密码*******************/
    @RequestMapping("/Passwordupdate")
    public Result Passwordupdate(@RequestHeader("token") String token,String password) {
        MySessionContext myContext = MySessionContext.getInstance();
        HttpSession session = myContext.getSession(token);
        Seller seller=(Seller) session.getAttribute("seller");
        System.out.println(seller.getAccount());
        System.out.println("old密码："+seller.getPassword());
        sellerService.Passwordupdate(seller.getAccount(), password,seller.getPassword());
        return new Result(true,MessageConstant.PASSWORD_UPDATE_SUCCESS);
    }
/*********选择购买人*********/
    @RequestMapping("/checkUser")
    public Result checkUser(Integer id) {
        Integer user_id= tradeActionMapper.trading(id);
        User user=sellerService.checkUser(user_id);
        System.out.println("选择成功");
        return new Result(true,MessageConstant.CHECK_USER_SUCCes,user);
    }

    @RequestMapping("/getLoginUser")
    public Result getLoginUser(@RequestHeader("token") String token){
        MySessionContext myContext = MySessionContext.getInstance();
        HttpSession session = myContext.getSession(token);
        Seller seller=(Seller) session.getAttribute("seller");
        return new Result(true,MessageConstant.GET_USER_SUCCESS,seller);
    }
    @RequestMapping("/logout")
    public Result logout(@RequestHeader("token") String token){
        MySessionContext myContext = MySessionContext.getInstance();
        HttpSession session = myContext.getSession(token);
        myContext.DelSession(session);
        return new Result(true,MessageConstant.LOGOUT_SUCCESS);
    }
    @RequestMapping("/register")
    public Result Register(@RequestBody RegisterParam registerParam){
        System.out.println(registerParam.getAccount());
        sellerService.Register(registerParam.getAccount(),registerParam.getPassword(),registerParam.getName());
        return new Result(true,MessageConstant.REGUSTER_SUCCESS);
    }
    /*******查看意向购买人**********/
    @RequestMapping("/intendingUSer")
    public Result interdingUSer(Integer goods_id){
        List<Buyerinfo> infoList= new ArrayList<>();
        List<User> UserList = new ArrayList<>();
        List<TradeAction> traLIst= sellerService.intendingUser(goods_id);
        for(int i=0;i<traLIst.size();i++){
            UserList.add( sellerService.getintendingUser(traLIst.get(i).getUserId()));
            Buyerinfo bi = new Buyerinfo(UserList.get(i).getName(),traLIst.get(i).getGoodsId(),traLIst.get(i).getId(),UserList.get(i).getTele(),traLIst.get(i).getTradeTime(),traLIst.get(i).getTradePlace());
            infoList.add(bi);
        }
        return new Result(true,MessageConstant.INTENDINGUSER_SUCCESS,infoList);
    }
    /*******选择购买意向人*******/
    @RequestMapping("/chooseUser")
    public Result chooseUSer(Integer id,Integer goods_id){
        sellerService.chooseUser(id,goods_id);
        System.out.println("选择成功");
        return new Result(true,MessageConstant.CHOOSEUSER_SUCCESS);
    }
    /*******注册验证功能*******/
    @RequestMapping("/registerComfirm")
    public Result registerComfirm(){
        boolean flag=sellerService.registerComfirm();
        return new Result(true,MessageConstant.REGISTERCOMFIRM_SUCCESS,flag);
    }
    /********检查是否已经被购买*********/
    @RequestMapping("/checkgoods")
    public Result checkgoods(){
        Integer data=sellerService.checkgoods();
        boolean flag = true;
        if(data==0){
             flag=true;
        }
        else{
             flag=false;
        }
        return new Result(true,MessageConstant.REGISTERCOMFIRM_SUCCESS,flag);
    }
    /*********查看订单数量**********/
    @RequestMapping("/getTradecount")
    public Result getTradecount(Integer goodsId){
        Integer count=sellerService.getTradecount(goodsId);
        return new Result(true,"查看订单数量成功",count);
    }

}
