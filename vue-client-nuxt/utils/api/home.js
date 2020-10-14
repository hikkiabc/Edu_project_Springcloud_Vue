import request from '../request'

export default {
  getAllBanner() {
    return request({
      url: '/front/banner',
      method: 'get'
    })
  },
  getTeacherAndCourse() {
    return request({
      url: '/teacher/hot',
      method: 'get'
    })
  }
}