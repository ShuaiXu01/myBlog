import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Blogs from '../views/Blogs.vue'
import BlogEdit from '../views/BlogEdit.vue'
import BlogDetail from '../views/BlogDetail.vue'
import Register from "../views/Register";

Vue.use(VueRouter)

/**
 * 路由中心
 */

const routes = [
  {
    path: '/',
    name: 'index',
    redirect:{name: 'Blogs'}
  },
  {
    path: '/blogs',
    name: 'Blogs',
    component: Blogs
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/user/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/blog/add',  //要放在 /blog/:blogId/edit 之前
    name: 'BlogAdd',
    component: BlogEdit,
    meta: {
      requireAuth: true  //登录之后才能访问的受限资源
    }
  },
  {
    path: '/blog/:blogId',
    name: 'BlogDetail',
    component: BlogDetail
  },
  {
    path: '/blog/delete',
    name: 'BlogDelete',
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/blog/:blogId/edit',
    name: 'BlogEdit',
    component: BlogEdit
  }
  
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router
