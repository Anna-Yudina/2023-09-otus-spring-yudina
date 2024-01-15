import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/BookView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'books',
    component: HomeView
  },
  {
    path: '/authors',
    name: 'authors',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AuthorsView.vue')
  },
  {
    path: '/genres',
    name: 'genres',
    component: () => import(/* webpackChunkName: "about" */ '../views/GenresView.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
