import { request } from '@/utils/request'

export function updateUserInfo(data) {
  return request({
    url: 'user/update',
    method: 'post',
    data: data
  })
}