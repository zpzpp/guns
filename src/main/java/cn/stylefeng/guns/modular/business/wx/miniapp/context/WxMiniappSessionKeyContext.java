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
package cn.stylefeng.guns.modular.business.wx.miniapp.context;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 小程序sessionKey管理
 *
 * @author liuhanqing
 * @date 2021/1/24 22:08
 */
public class WxMiniappSessionKeyContext {

    private static final ConcurrentHashMap<String, WxMaJscode2SessionResult> sessionKeyMap = new ConcurrentHashMap<>();

    /**
     * 添加用户ID相关的Session
     *
     * @param openId  用户id
     * @param session 用户session
     * @author liuhanqing
     * @date 2021/1/24 22:08
     */
    public static void add(String openId, WxMaJscode2SessionResult session) {
        sessionKeyMap.put(openId, session);
    }

    /**
     * 根据用户ID获取Session
     *
     * @param openId 用户id
     * @return WxMaJscode2SessionResult  用户session
     * @author liuhanqing
     * @date 2021/1/24 22:10
     */
    public static WxMaJscode2SessionResult getSession(String openId) {
        return sessionKeyMap.get(openId);
    }

    /**
     * 移除失效的Session
     *
     * @param openId  用户id
     * @author liuhanqing
     * @date 2021/1/24 22:11
     */
    public static void removeSession(String openId) {
        sessionKeyMap.remove(openId);
    }

}
