import request from '../request'

export default {
  getFrontCourseByCondition(data) {
    return request({
      url: '/teacher/course/frontCourse',
      method: 'post',
      data
    })
  },
  getAllSubject() {
    return request({
      url: '/teacher/course/subject',
      method: 'get'
    })
  },
  getCourseDetailById(id) {
    return request({
      url: '/teacher/course/frontcourse/' + id,
      method: 'get'
    })
  },
  getComment(id) {
    return request({
      url: '/teacher/course/comment/' + id,
      method: 'get'
    })
  },
  saveComment(data) {
    return request({
      url: '/teacher/course/comment',
      method: 'post',
      data
    })
  },
}