import { request } from '@/utils/request'

export function checkUserExist(code) {
  return request({
    url: 'user/exist',
    method: 'get',
    data: {
      code
    }
  })
}

export function updateUserInfo(data) {
  return request({
    url: 'user/update',
    method: 'post',
    data: data
  })
}