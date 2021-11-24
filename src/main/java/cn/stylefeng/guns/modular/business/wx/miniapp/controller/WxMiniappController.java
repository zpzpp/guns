package cn.stylefeng.guns.modular.business.wx.miniapp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import cn.stylefeng.guns.modular.business.wx.miniapp.exception.WxAuthException;
import cn.stylefeng.guns.modular.business.wx.miniapp.exception.enums.WxAuthExceptionEnum;
import cn.stylefeng.guns.modular.business.wx.miniapp.pojo.WxLoginUser;
import cn.stylefeng.guns.modular.business.wx.miniapp.pojo.WxMiniappRequest;
import cn.stylefeng.guns.modular.business.wx.miniapp.service.WxSessionManagerService;
import cn.stylefeng.guns.modular.demo.service.DemoService;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.pojo.response.ErrorResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 示例控制器
 *
 * @author fengshuonan
 * @date 2021/1/24 10:57
 */
@RestController
@ApiResource(name = "微信小程序", path = "/wx/miniapp")
public class WxMiniappController {

    @Resource
    private DemoService demoService;
    @Resource
    private WxMaService wxMaService;

    @Resource
    private WxSessionManagerService wxSessionManagerService;

    /**
     * 示例方法
     *
     * @author fengshuonan
     * @date 2021/1/24 10:59
     */
    @PostResource(name = "小程序登录", path = "/token/get")
    public ResponseData tokenGet(@RequestBody WxMiniappRequest wxMiniappRequest) {
        WxMaJscode2SessionResult session;
        HttpServletRequest request = HttpServletUtil.getRequest();
        try {
            session = wxMaService.getUserService().getSessionInfo(wxMiniappRequest.getCode());
            WxLoginUser loginUser = new WxLoginUser();
            loginUser.setToken(session.getOpenid());
            loginUser.setOpenId(session.getOpenid());
            loginUser.setSessionKey(session.getSessionKey());
            loginUser.setUnionId(session.getUnionid());
            loginUser.setLoginTime(new Date());
            wxSessionManagerService.createSession(session.getOpenid(), loginUser);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return new ErrorResponseData("500", e.getMessage());
        }
        return new SuccessResponseData(session);
    }

    /**
     * 示例方法
     *
     * @author fengshuonan
     * @date 2021/1/24 10:59
     */
    @GetResource(name = "小程序登录", path = "/token/verify")
    public ResponseData tokenVerify(String token) {
        WxLoginUser loginUser;
        boolean result = true;
        try {
            loginUser = wxSessionManagerService.getSession(token);
            if (loginUser == null) {
                result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponseData(WxAuthExceptionEnum.WX_AUTH_EXPIRED_ERROR.getErrorCode(), WxAuthExceptionEnum.WX_AUTH_EXPIRED_ERROR.getUserTip());
        }

        return new SuccessResponseData(result);
    }

    /**
     * 示例方法
     *
     * @author fengshuonan
     * @date 2021/1/24 10:59
     */
    @PostResource(name = "小程序登录", path = "/user/update")
    public ResponseData wxMiniappUserInfo(@RequestBody WxMiniappRequest wxMiniappRequest) {
        String rawData = wxMiniappRequest.getRawData();
        if (StrUtil.isNotEmpty(rawData)) {
            rawData = HtmlUtil.unescape(rawData);
        }
        String token = LoginContext.me().getToken();
        WxLoginUser loginUser = wxSessionManagerService.getSession(token);
        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(loginUser.getSessionKey(), rawData, wxMiniappRequest.getSignature())) {
            return new ErrorResponseData(WxAuthExceptionEnum.WX_GET_USERINFO_ERROR.getErrorCode(), WxAuthExceptionEnum.WX_GET_USERINFO_ERROR.getUserTip());
        }
        try {
            // 解密用户信息
            WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(loginUser.getSessionKey(), wxMiniappRequest.getEncryptedData(), wxMiniappRequest.getIv());

            BeanUtil.copyProperties(userInfo, loginUser);
            loginUser.setLoginTime(new Date());
            wxSessionManagerService.updateSession(token, loginUser);
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponseData(WxAuthExceptionEnum.WX_GET_USERINFO_ERROR.getErrorCode(), WxAuthExceptionEnum.WX_GET_USERINFO_ERROR.getUserTip());
        }
        return new SuccessResponseData(loginUser);
    }

}
