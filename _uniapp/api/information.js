import { request } from '@/utils/request'

export function getInformationRecommData(data) {
  return request({
    url: 'content/get_recomm/information/' + data.limit,
    method: 'get'
  })
}

export function getInformationNewestData(data) {
  return request({
    url: 'content/get_newest/information/' + data.limit,
    method: 'get'
  })
}

export function getInformationCategoryPaginationData(data) {
  return request({
    url: 'content/get_category_list',
    method: 'get',
    data: data
  })
}

export function getInformationByID(data) {
  return request({
    url: 'content/get_data/information/' + data.id,
    method: 'get'
  })
}

export function updateInformationLikeUser(data) {
  return request({
    url: 'content/update_operation/like/' + data.id,
    method: 'post',
    data: data
  })
}

export function updateInformationViewUser(data) {
  return request({
    url: 'content/update_operation/view/' + data.id,
    method: 'post',
    data: data
  })
}

export function updateInformationShareUser(data) {
  return request({
    url: 'content/update_operation/share/' + data.id,
    method: 'post',
    data: data
  })
}