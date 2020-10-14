import request from '../request'

export default {
  getTeachers(num, size) {
    return request({
      url: `/teacher/getfrontteacher/${num}/${size}`,
      method: 'post'
    })
  },
  getTeacherAndCourseByTeacherId(id) {
    return request({
      url: `/teacher/frontteacher/${id}`,
      method: 'get'
    })
  }

}