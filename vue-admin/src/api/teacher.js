import request from '@/utils/request'
export default {
  findByConditions(data) {
    return request({
      // url: '/vue-admin-template/user/login',
      url: '/teacher/page',
      method: 'post',
      data
    })
  },
  deleteById(id) {
    return request({
      url: `/teacher/${id}`,
      method: 'delete',

    })
  },
  add(data) {
    return request({
      url: `/teacher/`,
      method: 'post',
      data
    })
  },
  findById(id) {
    return request({
      url: `/teacher/${id}`,
      method: 'get',

    })
  },
}

