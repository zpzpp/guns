import { request } from '@/utils/request'

export function getBusinessTitleData(data) {
  return request({
    url: 'business/get_title/' + data.limit,
    method: 'get'
  })
}

export function getBusinessByID(data) {
  return request({
    url: 'business/get_id/' + data.id,
    method: 'get'
  })
}

export function updateBusinessAdvisoryUser(data) {
  return request({
    url: 'business/update_advisory/' + data.id,
    method: 'post'
  })
}