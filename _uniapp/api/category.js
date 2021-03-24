import { request } from '@/utils/request'

export function getCategoryNavData(data) {
  return request({
    url: 'category/get_nav/' + data.top_id,
    method: 'get'
  })
}