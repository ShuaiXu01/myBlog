import axios from 'axios';
import ElementUI from 'element-ui';
import router from "./router";
import store from "./router";

axios.defaults.baseURL = "http://39.98.113.38:8081"

/**
 * 全局axios拦截器
 */

//前置拦截
axios.interceptors.request.use(config => {
    return config
})

//后置拦截
axios.interceptors.response.use( response => {
    let res = response.data;
    console.log("=======")
    console.log(res)
    console.log("=======")
    
    if (res.code === 200) {
        return response
    } else {
        return Promise.reject(response.data.msg)
    }
},
   error => {
    console.log(error)
    if (error.response.data) {
        error.message = error.response.data.msg
    }   
    if (error.response.status === 401) {
        store.commit("REMOVE_INFO")
        router.push("/login")
        error.message = '请重新登录'
    }
    if (error.res.status === 403) {
        error.message = '权限不足，无法访问'
    }
    
    ElementUI.Message.error(error.message, {duration: 2 * 1000})
    return Promise.reject(error)   
}
)