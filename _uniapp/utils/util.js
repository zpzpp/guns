const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const timeGreeting = date => {
  const hour = date.getHours()

  if (hour > 0 && hour < 6) {
    return '现在是凌晨' + hour + '点,注意休息呗';
  } else if (hour >= 6 && hour < 11) {
    return 'Hi,早上好吖';
  } else if (hour >= 11 && hour < 13) {
    return 'Hi,中午好吖';
  } else if (hour >= 13 && hour < 18) {
    return 'Hi,下午好吖';
  } else {
    return 'Hi,晚上好吖';
  }
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

/**
 * 获得元素上的绑定的值
 * params：
 * event 元素的标识
 * key dataset的键
 */
const getDataSet = (event, key) => {
  return event.currentTarget.dataset[key];
}

/*
 * 提示窗口
 * params:
 * title - {string}标题
 * content - {string}内容
 * callback - 点击确定后需要执行的操作
 */
const showTips = (title, content, callback) => {
  wx.showModal({
    title: title,
    content: content,
    showCancel: false,
    success: function(res) {
      callback && callback(res);
    }
  });
}

/*
 * 操作确认窗口
 * params:
 * title - {string}标题
 * content - {string}内容
 * callback - 点击确定后需要执行的操作
 */
const confirmOperation = (title, content, callback) => {
  wx.showModal({
    title: title,
    content: content,
    showCancel: true,
    success: function(res) {
      callback && callback(res);
    }
  });
}

/**
 * 获取页面组件对应的信息
 * params:
 * params.name 节点的标识
 * params.success 成功后回调函数
 */
const getComponentInfo = (params) => {
  uni.createSelectorQuery().in(params.component).select(params.name).boundingClientRect((rect) => {
    params.success && params.success(rect);
  }).exec();
}

module.exports = {
  formatTime,
  timeGreeting,
  getDataSet,
  showTips,
  confirmOperation,
  getComponentInfo
}
