import Vue from 'vue';
import VueRouter from 'vue-router';
import App from '../App.vue';
import { Message } from 'element-ui';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: '登录',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/register',
    name: '注册',
    component: () => import(/* webpackChunkName: "about" */ '../views/Register.vue'),
  },
  {
    path: '/mainpage',
    name: '主页',
    component: () => import(/* webpackChunkName: "about" */ '../views/MainPage.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/userinfo',
    name: '用户个人信息',
    component: () => import(/* webpackChunkName: "about" */ '../views/UserInfo.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/chat',
    name: '聊天群组',
    component: () => import(/* webpackChunkName: "about" */ '../views/ChatGroup.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/recycle',
    name: '回收站',
    component: () => import(/* webpackChunkName: "about" */ '../views/RecycleStop.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/manage',
    name: '用户管理',
    component: () => import(/* webpackChunkName: "about" */ '../views/UserManage.vue'),
    meta: { requiresAuth: true }
  },


  {
    path: '/changeName',
    name: '修改用户名',
    component: () => import('../views/userInfoItems/ChangeName.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/changeMail',
    name: '修改邮箱',
    component: () => import(/* webpackChunkName: "about" */ '../views/userInfoItems/ChangeMail.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/changePassword',
    name: '修改密码',
    component: () => import(/* webpackChunkName: "about" */ '../views/userInfoItems/ChangePassword.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/changeOrg',
    name: '修改公司部门',
    component: () => import(/* webpackChunkName: "about" */ '../views/userInfoItems/ChangeOrg.vue'),
    meta: { requiresAuth: true }
  }



]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

// 在全局路由守卫中检查登录状态
router.beforeEach((to, from, next) => {
  const isLoggedIn = checkLogin(); // 检查登录状态的函数

  if (to.matched.some(record => record.meta.requiresAuth) && !isLoggedIn) {
    // 如果目标路由需要登录且用户未登录，则重定向到登录页面
    Message.error('请先登录');
    next('/');
  } else {
    // 否则，允许跳转到目标路由
    next();
  }
});

function checkLogin() {
  const user = sessionStorage.getItem('user');
  

  // 如果user存在，则认为用户已登录
  return !!user ;
}

new Vue({
  router,
  render: h => h(App)
}).$mount('#app');

export default router;
