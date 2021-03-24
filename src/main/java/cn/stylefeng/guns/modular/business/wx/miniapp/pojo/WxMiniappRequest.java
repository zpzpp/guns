package cn.stylefeng.guns.modular.business.wx.miniapp.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 通用树选择器界面的请求参数
 *
 * @author fengshuonan
 * @date 2021/1/6 20:20
 */
@Data
public class WxMiniappRequest {

    /**
     * 传递给上级页面的哪个字段，例如 parent.MenuInfoDlg.data.pcodeName
     */
    private String code;

    /**
     * 传递给上级页面的哪个id值，例如 parent.MenuInfoDlg.data.pid
     */
    private String token;

    /**
     * 渲染出数据的url，例如/menu/selectMenuTreeList
     */
    private String sessionKey;
    private String signature;
    private String rawData;
    private String encryptedData;
    private String iv;

}
