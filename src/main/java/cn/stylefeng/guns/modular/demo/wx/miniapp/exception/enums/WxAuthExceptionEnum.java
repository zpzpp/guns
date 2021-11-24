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
package cn.stylefeng.guns.modular.business.wx.miniapp.exception.enums;

import cn.stylefeng.roses.kernel.auth.api.constants.AuthConstants;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 认证相关异常
 *
 * @author fengshuonan
 * @date 2020/10/16 10:53
 */
@Getter
public enum WxAuthExceptionEnum implements AbstractExceptionEnum {

    /**
     * 会话过期或超时，token过期，都属于这种异常，提示用户从新登录
     */
    WX_AUTH_EXPIRED_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "87", "wx当前登录会话过期，请重新登录"),
    /**
     * 会话过期或超时，token过期，都属于这种异常，提示用户从新登录
     */
    WX_GET_USERINFO_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "89", "wx获取用户信息异常，请重新登录"),
    /**
     * 获取token失败
     */
    WX_TOKEN_GET_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "88", "wx获取token失败，请检查header和param中是否传递了用户token");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    WxAuthExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
