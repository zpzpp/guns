import { request } from '@/utils/request'

export function getSearchResult(data) {
  return request({
    url: 'search/get_result',
    method: 'get',
    data: data
  })
}