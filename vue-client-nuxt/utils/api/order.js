import request from '../request'

export default {
  saveOrder(data) {
    return request({
      url: '/order/',
      method: 'post',
      data
    })
  },
  getOrderByOrderNo(id) {
    return request({
      url: '/order/' + id,
      method: 'get'
    })
  }
}