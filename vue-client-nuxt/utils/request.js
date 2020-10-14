import axios from 'axios'
import cookie from 'js-cookie'

const request = axios.create({
  baseURL: 'http://localhost:9001',
  timeout: 2000
})

request.interceptors.request.use(
  config => {
    // do something before request is sent

    if (cookie.get('token')) {

      config.headers['token'] = cookie.get('token')
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

export default request