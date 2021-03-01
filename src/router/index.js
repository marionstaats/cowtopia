import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'cows',
    component: () => import("../views/CowList")
  },
  {
    path: "/cows/:id",
    name: "cow-details",
    component: () => import("../views/Cow")
  },
  {
    path: "/add",
    name: "add",
    component: () => import("../views/AddCow")
  },
  {
    path: '*',
    component: () => import("../views/CowList")
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
