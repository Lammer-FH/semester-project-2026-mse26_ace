import { createRouter, createWebHistory } from '@ionic/vue-router'
import { RouteRecordRaw } from 'vue-router'

import HomePage from '../views/HomePage.vue'
import AboutPage from '../views/AboutPage.vue'
import ImprintPage from '../views/ImprintPage.vue'
import RoomsPage from '../views/RoomsPage.vue'
import RoomDetailPage from '../views/RoomDetailPage.vue'
import BookingPage from "../views/BookingPage.vue"


const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    name: 'Home',
    component: HomePage
  },
  {
    path: '/about',
    name: 'About',
    component: AboutPage
  },
    {
    path: '/imprint',
    name: 'Imprint',
    component: ImprintPage
  },
  {
  path: '/rooms',
  name: 'Rooms',
  component: RoomsPage
},
{
  path: '/rooms/:id',
  name: 'RoomDetail',
  component: RoomDetailPage
},
{
  path: "/booking/:id",
  name: "Booking",
  component: BookingPage
},
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router