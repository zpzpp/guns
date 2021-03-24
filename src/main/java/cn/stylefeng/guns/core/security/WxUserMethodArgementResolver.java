package cn.stylefeng.guns.core.security;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class WxUserMethodArgementResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
      /*  if(methodParameter.getParameterType().isAssignableFrom(Target.class)&&methodParameter.hasParameterAnnotation(CurrentUser.class)){
            System.out.println("===========================》符合条件");
            return true;
        }*/
        return false;
    }
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        System.out.println("------------------------------------------");
        /*Target target=new Target();
        target.setTid(56);
        target.setTname("852963741");
        return target;*/
        return new Object();
    }
}