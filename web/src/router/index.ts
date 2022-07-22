import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/home.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    //懒加载
    component: () => import(/* webpackChunkName: "about" */ '../views/about.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
