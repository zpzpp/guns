/**
 * Created by tuniao on 2020/1/2
 */
import { updateUserInfo } from '@/api/user'
import {
  USER_INFO_NAME
} from 'config.js';

/**
 * 检查用户是否已经授权登陆
 */
export function checkUserScope() {
    const app = getApp();
    return new Promise((reslove, reject) => {
		debugger
        //判断用户是否已经更新过信息
        if (app.globalData.isUpdateUserInfo == true) {
            reslove(true);
        } else {
			reject(false);
            //判断用户是否已经授权获取用户信息
     //        uni.getSetting({
     //            success: (res) => {
					// debugger
     //                if (res.authSetting['scope.userInfo']) {
     //                    reslove(true);
     //                } else {
     //                    reject(false);
     //                }
     //            }
     //        });
        }
    })
}

/**
 * 检查用户是否已经授权登陆
 */
export function checkUserInfo() {
    const app = getApp();
	
	let userInfo = uni.getStorageSync(USER_INFO_NAME); //取出在本地的用户信息
    return new Promise((reslove, reject) => {
		debugger
        //判断用户是否已经更新过信息
        if (userInfo) {
            reslove(true);
        } else {
			reslove(false);
        }
    })
}

/**
 * 获取用户信息
 */
export function getUserInfo() {
    const app = getApp();
    return new Promise((resolve, reject) => {
		debugger
        uni.getUserProfile({
          // provider: 'weixin',
		    desc: '获得您的公开信息', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
            success: (res) => {
                if (!app.globalData.isUpdateUserInfo) {
                    _updateUserInfo(app, res);
                }
                resolve(res.userInfo);
            },
            fail: (res) => {
                reject();
            }
        });
    })
}

/**
 * 将用户信息上传到服务器上
 * params:
 * userInfo 需要上传到服务器的用户信息
 */
function _updateUserInfo(app, data) {
    console.log(data);
	let userInfo = data.userInfo;
	let rawData = data.rawData;
	/* if(rawData){
		rawData = rawData.replace(/\"/g,"'")
	} */
    updateUserInfo({
        nickName: userInfo.nickName,
        avatarUrl: userInfo.avatarUrl,
        gender: userInfo.gender,
        rawData: rawData,
        signature: data.signature,
        encryptedData: data.encryptedData,
        iv: data.iv
    }).then((res) => {
		debugger
        if (res.code === "00000") {
            app.globalData.isUpdateUserInfo = true;
			uni.setStorageSync(USER_INFO_NAME, res.data);
        }
    })
}