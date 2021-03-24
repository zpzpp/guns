/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.guns.modular.business.wx.miniapp.service.impl;

import cn.hutool.core.convert.Convert;
import cn.stylefeng.guns.modular.business.wx.miniapp.pojo.WxLoginUser;
import cn.stylefeng.guns.modular.business.wx.miniapp.service.WxSessionManagerService;
import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.cookie.SessionCookieCreator;
import cn.stylefeng.roses.kernel.auth.api.expander.AuthConfigExpander;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 基于redis的会话管理
 *
 * @author fengshuonan
 * @date 2019-09-28-14:43
 */
public class WxSessionManagerServiceImpl implements WxSessionManagerService {

    /**
     * 登录用户缓存
     * <p>
     * key是 LOGGED_TOKEN_PREFIX + 用户的token
     */
    private final CacheOperatorApi<WxLoginUser> loginUserCache;


    /**
     * session的超时时间
     */
    private final Long sessionExpiredSeconds;

    public WxSessionManagerServiceImpl(CacheOperatorApi<WxLoginUser> loginUserCache,
                                       Long sessionExpiredSeconds) {
        this.loginUserCache = loginUserCache;
        this.sessionExpiredSeconds = sessionExpiredSeconds;
    }

    @Override
    public void createSession(String token, WxLoginUser loginUser) {

        // 装配用户信息的缓存
        loginUserCache.put(token, loginUser, sessionExpiredSeconds);
    }

    @Override
    public void updateSession(String token, WxLoginUser loginUser) {
        WxLoginUser session = this.getSession(token);

        // 该用户session为空不能更新
        if (session == null) {
            return;
        }

        loginUserCache.put(token, loginUser, sessionExpiredSeconds);
    }

    @Override
    public WxLoginUser getSession(String token) {
        return loginUserCache.get(token);
    }

    @Override
    public void removeSession(String token) {

        // 删除本token用户信息的缓存
        loginUserCache.remove(token);
    }

    @Override
    public boolean haveSession(String token) {
        return loginUserCache.contains(token);
    }

    @Override
    public void refreshSession(String token) {
        WxLoginUser loginUser = loginUserCache.get(token);
        if (loginUser != null) {
            loginUserCache.expire(token, sessionExpiredSeconds);
        }
    }

    @Override
    public List<WxLoginUser> onlineUserList() {
        Map<String, WxLoginUser> allKeyValues = loginUserCache.getAllKeyValues();

        ArrayList<WxLoginUser> loginUsers = new ArrayList<>();
        for (Map.Entry<String, WxLoginUser> userEntry : allKeyValues.entrySet()) {
            String token = userEntry.getKey();
            WxLoginUser loginUser = userEntry.getValue();
            loginUser.setToken(token);
            loginUsers.add(loginUser);
        }

        return loginUsers;
    }

}
