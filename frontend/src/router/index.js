import Vue from 'vue'
import Router from 'vue-router'

// import AppIndex from '@/components/home/AppIndex'
import Login from '@/components/Login'
import Home from '../components/Home'
import LibraryIndex from '../components/library/LibraryIndex'
import Cart from '../components/library/Cart'
import Myorders from '../components/library/Myorders'
import admin from '../components/library/admin'
import anly from '../components/library/admin-anly'
import dataDig from '../components/library/admin-data'
import adminOrders from '../components/library/admin-orders'
import adminUsers from '../components/library/admin-users'
Vue.use(Router)

export default new Router({
  mode: 'history',
  base: '/demo/bookmarket/',
  routes: [
    {
      path: '/',
      name: 'index',
      redirect: '/book',
      component: LibraryIndex,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      redirect: '/index',
      children: [
        {
          path: '/index',
          name: 'AppLibrary',
          component: LibraryIndex,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/cart',
          name: 'Cart',
          component: Cart,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/book',
          name: 'book',
          component: LibraryIndex,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/Myorders',
          name: 'Myorders',
          component: Myorders,
          meta: {
            requireAuth: true
          }
        }
      ]
    },
    {
      path: '/admin',
      name: 'admin',
      component: admin,
      meta: {
        requireAuth: true
      },
      children: [
        {
          path: '/',
          name: 'anly',
          redirect: '/anly',
          component: anly,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/anly',
          name: 'anly',
          component: anly,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/data',
          name: 'data',
          component: dataDig,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/adminOrders',
          name: 'adminOrders',
          component: adminOrders,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/adminUsers',
          name: 'adminUsers',
          component: adminUsers,
          meta: {
            requireAuth: true
          }
        }
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/line',
      name: '折线图',
      component: () => import('../components/library/linechart')
    }
  ]
})
