import request from '../request'

export default {
  register(data) {
    return request({
      url: '/user/register',
      method: 'post',
      data
    })
  },
  login(data) {
    return request({
      url: '/user/login',
      method: 'post',
      data
    })
  },
  getCode(phone) {
    return request({
      url: '/sms/getcode/' + phone,
      method: 'get'
    })
  },
  getUserByToken() {
    return request({
      url: '/user/login',
      method: 'get'
    })
  },
}