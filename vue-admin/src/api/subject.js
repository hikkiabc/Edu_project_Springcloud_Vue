import request from '@/utils/request'

export default {
  getSubjectList() {
    return request({
      url: '/teacher/subject/getlist',
      method: 'get'
    })
  },
  addCourse(data) {
    return request({
      url: '/teacher/course/addcourse',
      method: 'post',
      data
    })
  },
  getAllTeacher() {
    return request({
      url: '/teacher/page',
      method: 'post',
      // data: {}
    })
  },
  getAllChapters(courseId) {
    return request({
      url: '/teacher/course/chapter/' + courseId,
      method: 'get'
    })
  },
  getCourseById(id) {
    return request({
      url: '/teacher/course/' + id,
      method: 'get'
    })
  },
  addChapter(data) {
    return request({
      url: '/teacher/course/chapter',
      method: 'post',
      data
    })
  },
  deleteChapterById(id) {
    return request({
      url: '/teacher/course/chapter/' + id,
      method: 'post'
    })
  },
  addSubChapter(data) {
    return request({
      url: '/teacher/course/subchapter',
      method: 'post',
      data
    })
  },
  deleteSubChapterById(id) {
    return request({
      url: '/teacher/course/subchapter/' + id,
      method: 'delete'
    })
  },
  getCoursePublishById(id) {
    return request({
      url: '/teacher/course/coursepublish/' + id,
      method: 'get'
    })
  },
  getAllCourse(data) {
    return request({
      url: '/teacher/course/',
      method: 'post',
      data
    })
  },
  publishCourse(id) {
    return request({
      url: '/teacher/course/coursepublish/' + id,
      method: 'post'
    })
  },
  deleteCourseById(id) {
    return request({
      url: '/teacher/course/' + id,
      method: 'delete'
    })
  }
}