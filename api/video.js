import request from '@/utils/request'
export default {
  getPlayAuth(vid) {
    return request({
      url: `/eduvod/getPlayAuto/${vid}`,
      method: 'get'
    })
  }

}