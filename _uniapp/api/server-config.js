import { request } from '@/utils/request'

export function getServerConfigValue(data) {
  return request({
    url: 'config/wx',
    method: 'get',
    data: data
  })
}