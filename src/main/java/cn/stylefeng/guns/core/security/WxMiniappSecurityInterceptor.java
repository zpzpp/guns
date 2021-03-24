package cn.stylefeng.guns.core.security;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.modular.business.wx.miniapp.context.WxLoginUserTreadLocalContext;
import cn.stylefeng.guns.modular.business.wx.miniapp.exception.enums.WxAuthExceptionEnum;
import cn.stylefeng.guns.modular.business.wx.miniapp.pojo.WxLoginUser;
import cn.stylefeng.guns.modular.business.wx.miniapp.service.WxSessionManagerService;
import cn.stylefeng.roses.kernel.auth.api.AuthServiceApi;
import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.exception.AuthException;
import cn.stylefeng.roses.kernel.rule.util.AntPathMatcherUtil;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.ResourceUrlParam;
import cn.stylefeng.roses.kernel.system.api.ResourceServiceApi;
import cn.stylefeng.roses.kernel.validator.api.context.RequestParamContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础的Filter，一般用在权限过滤
 *
 * @author fengshuonan
 * @date 2020/12/15 22:50
 */
@Component
@Slf4j
public class WxMiniappSecurityInterceptor implements HandlerInterceptor {

    @Resource
    private ResourceServiceApi resourceServiceApi;

    @Resource
    private AuthServiceApi authServiceApi;

    @Resource
    private SessionManagerApi sessionManagerApi;
    @Resource
    private WxSessionManagerService wxSessionManagerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 1. 获取当前请求的路径
        String requestURI = request.getRequestURI();
        List<String> wxUrl = new ArrayList<>();
        wxUrl.add("/wx/**");

        // 2. 不是小程序连接放行
        Boolean noneSecurityFlag = AntPathMatcherUtil.getAntMatchFLag(requestURI, request.getContextPath(), wxUrl);
        if (!noneSecurityFlag) {
            return true;
        }

        // 3. 获取当前用户的token，可能为null
        String token = null;
        try {
            token = LoginContext.me().getToken();
        } catch (Exception e) {
            // 不做处理，因为本接口可能是不需要鉴权
        }

        // 4. 如果token不为空，则先判断是否登录过期了，过期了就直接打回，不过期不做处理
        if (StrUtil.isNotBlank(token)) {
            request.setAttribute("openid", token);
            WxLoginUser wxLoginUser = wxSessionManagerService.getSession(token);
            if (wxLoginUser == null) {
                throw new AuthException(WxAuthExceptionEnum.WX_TOKEN_GET_ERROR);
            }
            WxLoginUserTreadLocalContext.setObject(wxLoginUser);
            // 5. 刷新用户的session的过期时间
            wxSessionManagerService.refreshSession(token);
        }

        // 6. 获取ResourceDefinition，可能为null
        ResourceUrlParam resourceUrlParam = new ResourceUrlParam();
        resourceUrlParam.setUrl(requestURI);
        ResourceDefinition resourceDefinition = resourceServiceApi.getResourceByUrl(resourceUrlParam);

        // 7. 资源找不到，则当前url不被权限控制，直接放行
        if (resourceDefinition == null) {
            return true;
        }

        // 8.执行真正过滤器业务，如果拦截器执行不成功会抛出异常
        this.filterAction(request, response, resourceDefinition, token);

        return true;
    }

    /**
     * 过滤器的具体业务执行逻辑
     *
     * @author fengshuonan
     * @date 2020/12/15 22:52
     */
    public void filterAction(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ResourceDefinition resourceDefinition, String token) {

    }

    ;

}
