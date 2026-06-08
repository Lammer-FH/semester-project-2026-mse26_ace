import { createRouter, createWebHistory } from '@ionic/vue-router'
import { RouteRecordRaw } from 'vue-router'

import HomePage from '../views/HomePage.vue'
import AboutPage from '../views/AboutPage.vue'
import ImprintPage from '../views/ImprintPage.vue'
import RoomsPage from '../views/RoomsPage.vue'
import RoomDetailPage from '../views/RoomDetailPage.vue'
import BookingPage from "../views/BookingPage.vue"


const routes = [
  {
    path: "/",
    name: "home",
    component: () => import("../views/HomePage.vue")
  },
  {
    path: "/rooms",
    name: "rooms",
    component: () => import("../views/RoomsPage.vue")
  },
  {
    path: "/rooms/:id",
    name: "room-details",
    component: () => import("../views/RoomDetailPage.vue")
  },
  {
    path: "/booking/:id",
    name: "booking",
    component: () => import("../views/BookingPage.vue")
  },
  {
    path: "/about",
    name: "about",
    component: () => import("../views/AboutPage.vue")
  },
  {
    path: "/imprint",
    name: "imprint",
    component: () => import("../views/ImprintPage.vue")
  }
]
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router