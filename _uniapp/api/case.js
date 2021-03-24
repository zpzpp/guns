import { request } from '@/utils/request'

export function getCaseRecommData(data) {
  return request({
    url: 'content/get_recomm/case/' + data.limit,
    method: 'get'
  })
}

export function getCaseNewestData(data) {
  return request({
    url: 'content/get_newest/case/' + data.limit,
    method: 'get'
  })
}

export function getCaseCategoryPaginationData(data) {
  return request({
    url: 'content/get_category_list',
    method: 'get',
    data: data
  })
}

export function getCaseByID(data) {
  return request({
    url: 'content/get_data/case/' + data.id,
    method: 'get'
  })
}

export function updateCaseLikeUser(data) {
  return request({
    url: 'content/update_operation/like/' + data.id,
    method: 'post',
    data: data
  })
}

export function updateCaseViewUser(data) {
  return request({
    url: 'content/update_operation/view/' + data.id,
    method: 'post',
    data: data
  })
}

export function updateCaseShareUser(data) {
  return request({
    url: 'content/update_operation/share/' + data.id,
    method: 'post',
    data: data
  })
}