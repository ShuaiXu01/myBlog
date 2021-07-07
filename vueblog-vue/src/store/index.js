import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
/**
 * 使用localStorage存储token，sessionStorage存储用户信息(用户信息不需要长久保存)
 */
export default new Vuex.Store({
  state: {
    token: '',
    userInfo: JSON.parse(sessionStorage.getItem("userInfo")),
    hasLogin: Boolean
  },
  mutations: {
    //set
    SET_TOKEN: (state, token) => {  //设置store中的token信息
      state.token = token
      localStorage.setItem("token", token)
    },
    SET_USERINFO: (state, userInfo) => {  //初始化用户信息
      state.userInfo = userInfo
      sessionStorage.setItem("userInfo", JSON.stringify(userInfo))
    },
    REMOVE_INFO: (state) => {  //清除store中的用户信息和token信息
      state.token = ''
      state.userInfo = {}
      state.hasLogin = false
      localStorage.setItem("token", '')
      sessionStorage.setItem("userInfo", JSON.stringify(''))
    },
    SET_HASLOGIN: (state, hasLogin) => {
      state.hasLogin = hasLogin
      sessionStorage.setItem("hasLogin", hasLogin)
    }
  },
  getters: {
    //get
    getUser: state => {    //获取store中的用户信息
      return state.userInfo
    },
    getState: state => {
      return state.hasLogin
    }
  },
  actions: {
  },
  modules: {
  }
})
