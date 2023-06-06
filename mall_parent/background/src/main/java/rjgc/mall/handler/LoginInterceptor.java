package rjgc.mall.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import rjgc.mall.common.MessageConstant;
import rjgc.mall.common.MySessionContext;
import rjgc.mall.common.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //再执行controller方法(handler)之前执行
        /**
         * 1.判断 请求的接口路径 是否为HandlerMethod(controller 方法）
         * 2.判断 Token是否为空 为空 则未登录
         * 3.如果Token不会空 登录验证 验证Token是否违法
         * 4.如果认证成功，放行即可
         */
        if(!(handler instanceof HandlerMethod)){
            //handler 可能是RequestResourceHandler springboot 程序 访问静态资源 默认去classpath下的static目录查询
            return true;
        }
        String token = request.getHeader("token");
        MySessionContext mySessionContext = MySessionContext.getInstance();
        System.out.println(mySessionContext.getSession(token));
        if(mySessionContext.getSession(token) == null){
            Result result = new Result(false, MessageConstant.NOT_LOGIN);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        return true;
    }
}
