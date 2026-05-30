<template>
  <ion-page>
    <AppHeader />

    <ion-content>
      <section class="rooms-page">
        <h1>Our Rooms</h1>

        <p class="intro">
          Discover our available rooms and choose the one that fits your stay best.
        </p>

        <p v-if="loading">Loading rooms...</p>

        <p v-if="error" class="error">
          Rooms could not be loaded. Please try again later.
        </p>

        <div v-if="!loading && !error" class="room-grid">
          <ion-card v-for="room in visibleRooms" :key="room.id" class="room-card">
            <img :src="getMainImage(room)" :alt="room.title" />

            <ion-card-header>
              <ion-card-title>{{ room.title }}</ion-card-title>
            </ion-card-header>

            <ion-card-content>
              <p>{{ room.description }}</p>

              <p class="room-meta">
                {{ room.capacity }} guest{{ room.capacity === 1 ? '' : 's' }} ·
                {{ room.sizeSqm }} m² ·
                €{{ room.pricePerNight }} / night
              </p>

              <div class="extras">
                <span v-for="extra in room.extras" :key="extra.id">
                  {{ getExtraIcon(extra.iconName) }} {{ extra.name }}
                </span>
              </div>

              <ion-button expand="block" :router-link="`/rooms/${room.id}`">
                View details
              </ion-button>
            </ion-card-content>
          </ion-card>
        </div>

        <div v-if="!loading && !error && totalPages > 1" class="pagination">
          <ion-button :disabled="currentPage === 1" @click="previousPage">
            Previous
          </ion-button>

          <span>Page {{ currentPage }} of {{ totalPages }}</span>

          <ion-button :disabled="currentPage === totalPages" @click="nextPage">
            Next
          </ion-button>
        </div>
      </section>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import AppHeader from '../components/AppHeader.vue'

import {
  IonPage,
  IonContent,
  IonButton,
  IonCard,
  IonCardHeader,
  IonCardTitle,
  IonCardContent
} from '@ionic/vue'

import { getRooms, type Room } from '../services/roomService'

const loading = ref(false)
const error = ref(false)
const currentPage = ref(1)
const roomsPerPage = 5

const rooms = ref<Room[]>([])

const totalPages = computed(() => {
  return Math.ceil(rooms.value.length / roomsPerPage)
})

const visibleRooms = computed(() => {
  const start = (currentPage.value - 1) * roomsPerPage
  const end = start + roomsPerPage

  return rooms.value.slice(start, end)
})

async function loadRooms() {
  loading.value = true
  error.value = false

  try {
    rooms.value = await getRooms()
  } catch {
    error.value = true
  } finally {
    loading.value = false
  }
}

function nextPage() {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

function previousPage() {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

function getMainImage(room: Room) {
  const mainImage = room.images?.find((image) => image.isMainImage)

  return mainImage?.url || 'https://images.unsplash.com/photo-1566665797739-1674de7a421a'
}

function getExtraIcon(iconName: string) {
  if (iconName === 'wifi') return '📶'
  if (iconName === 'coffee') return '☕'
  if (iconName === 'car') return '🅿️'
  if (iconName === 'tv') return '📺'
  if (iconName === 'wind') return '❄️'
  if (iconName === 'spa') return '🧖'

  return '✨'
}

onMounted(() => {
  loadRooms()
})
</script>

<style scoped>
.rooms-page {
  padding: 24px;
  max-width: 1100px;
  margin: 0 auto;
}

h1 {
  font-size: 32px;
  margin-bottom: 8px;
}

.intro {
  font-size: 18px;
  color: #555;
  margin-bottom: 24px;
}

.error {
  color: darkred;
}

.room-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 20px;
}

.room-card {
  margin: 0;
}

.room-card img {
  width: 100%;
  height: 220px;
  object-fit: cover;
}

.room-meta {
  color: #666;
  font-size: 14px;
  margin-top: 12px;
}

.extras {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin: 12px 0 16px;
}

.extras span {
  background-color: #eeeeee;
  color: #222222;
  padding: 6px 10px;
  border-radius: 12px;
  font-size: 14px;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 16px;
  margin-top: 32px;
}

@media (min-width: 768px) {
  .rooms-page {
    padding: 48px;
  }

  h1 {
    font-size: 42px;
  }

  .room-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (min-width: 1024px) {
  .room-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}
</style>