/**
 * Created by tuniao on 2020/1/2
 */
import { updateUserInfo } from '@/api/user'

/**
 * 检查用户是否已经授权登陆
 */
export function checkUserScope() {
    const app = getApp();
    return new Promise((reslove, reject) => {
        //判断用户是否已经更新过信息
		debugger
        if (app.globalData.isUpdateUserInfo == true) {
            reslove(true);
        } else {
            //判断用户是否已经授权获取用户信息
            uni.getSetting({
                success: (res) => {
                    if (res.authSetting['scope.userInfo']) {
						debugger
                        reslove(true);
                    } else {
                        reject(false);
                    }
                }
            });
        }
    })
}

/**
 * 获取用户信息
 */
export function getUserInfo() {
    const app = getApp();
    return new Promise((resolve, reject) => {
        uni.getUserInfo({
          provider: 'weixin',
            success: (res) => {
				debugger
                if (!app.globalData.isUpdateUserInfo) {
                    _updateUserInfo(app, res.userInfo);
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
function _updateUserInfo(app, userInfo) {
    // console.log(userInfo);
    updateUserInfo({
        nick_name: userInfo.nickName,
        avatar_url: userInfo.avatarUrl,
        gender: userInfo.gender
    }).then((res) => {
        if (res.code === 200) {
            app.globalData.isUpdateUserInfo = true;
        }
    })
}