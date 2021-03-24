import {
  MP_API_URL,
  TOKEN_NAME
} from 'config.js';

/**
 * http 请求类
 * params：
 * param 请求参数
 */
export function request(params) {
  let url = MP_API_URL + params.url;
  params.type = params.type || 'get';
  /*不需要再次组装地址*/
  if (params.setUpUrl == false) {
      url = params.url;
  }
  return new Promise((resolve, reject) => {
	  debugger
      uni.request({
          url: url,
          data: params.data,
          method: params.method,
          header: {
              'content-type': 'application/json',
              'Authorization': uni.getStorageSync(TOKEN_NAME)
          },
          success: async (res) => {
			  debugger
              // 判断以2（2xx)开头的状态码为正确
              // 异常不要返回到回调中，就在request中处理，记录日志并showToast一个统一的错误即可
              var code = res.statusCode.toString();
              var startChar = code.charAt(0);
              if (startChar == '2') {
                  resolve(res.data);
              } else {
                  //token已经过期了，需要重新获取
                  //验证errorCode是用于区别没有授权的网站使用
                  if (code == '401' && res.data.errorCode == 30000) {
                      await getTokenFromServer();
                      return request(params);
                  }
                  _processError(res);
                  reject(res.data);
              }
          },
          fail: (err) => {
			  debugger
              //wx.hideNavigationBarLoading();
              _processError(err);
              reject(err);
          }
      });
  })
}

/**
 * 从服务器中获取Token
 */
function getTokenFromServer() {
  return new Promise((resolve, reject) => {
      uni.login({
        provider: 'weixin',
          success: (res) => {
              uni.request({
                  url: MP_API_URL + 'token/get',
                  method: 'POST',
                  data: {
                      code: res.code
                  },
                  success: (res) => {
                      uni.setStorageSync(TOKEN_NAME, res.data.token);   //将Token存放在本地
                      resolve(res.data.token);
                  }
              })
          }
      })
  })
}

/**
 * 处理错误
 */
function _processError(err) {
  // 判断错误的信息是什么类型
  let msg = [];
  if (err && err.data && typeof err.data.msg == 'object') {
    for (let item in err.data.msg) {
      msg.push(err.data.msg[item]);
    }
  } else if (err && err.data && typeof err.data.msg == 'string') {
    msg.push(err.data.msg);
  } else {
    msg.push('未知错误');
  }
  console.log(msg[0]);

  if (err && err.data && (err.data.errorCode == 30000 ||
    err.data.errorCode == 40012 ||
    err.data.errorCode == 40103 ||
    err.data.errorCode == 40104 ||
    err.data.errorCode == 20004 ||
    err.data.errorCode == 20200)) {
    return;
  }

  uni.showToast({
    title: msg[0],
    icon: 'none'
  });
}