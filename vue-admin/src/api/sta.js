import request from '@/utils/request'

export default {
  getRegisterCountByDate(date) {
    return request({
      url: '/statistics/registercountbydate',
      method: 'get',
      params: { date }
    })
  },
  getStatisticsForChart(data) {
    return request({
      url: '/statistics/',
      method: 'post',
      data
    })
  }
} 