<template>
  <ion-page>
    <AppHeader />

    <ion-content>
      <section class="rooms-page">
        <div class="rooms-hero">
          <p class="eyebrow">Hotel Technikum</p>

          <h1>Our Rooms</h1>

          <p class="intro">
            Discover our available rooms and choose the one that fits your stay
            best.
          </p>
        </div>

        <p v-if="loading" class="status-text">
          Loading rooms...
        </p>

        <p v-if="error" class="error">
          Rooms could not be loaded. Please try again later.
        </p>

        <div v-if="!loading && !error" class="room-grid">
          <RoomCard
            v-for="room in visibleRooms"
            :key="room.id"
            :room="room"
          />
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
import { computed, onMounted, ref } from "vue"

import {
  IonPage,
  IonContent,
  IonButton
} from "@ionic/vue"

import AppHeader from "../components/AppHeader.vue"
import RoomCard from "../components/molecules/RoomCard.vue"
import { useRoomStore } from "../stores/roomStore"

const roomStore = useRoomStore()

const currentPage = ref(1)
const roomsPerPage = 5

const totalPages = computed(() => {
  return Math.ceil(roomStore.rooms.length / roomsPerPage)
})

const visibleRooms = computed(() => {
  const start = (currentPage.value - 1) * roomsPerPage
  const end = start + roomsPerPage

  return roomStore.rooms.slice(start, end)
})

const loading = computed(() => roomStore.loading)
const error = computed(() => roomStore.error)

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

onMounted(() => {
  roomStore.loadRooms()
})
</script>

<style scoped>
.rooms-page {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.rooms-hero {
  background:
    linear-gradient(135deg, rgba(35, 83, 71, 0.94), rgba(32, 120, 104, 0.86)),
    url("https://images.unsplash.com/photo-1566073771259-6a8506099945");
  background-size: cover;
  background-position: center;
  border-radius: 26px;
  padding: 32px;
  margin-bottom: 28px;
  color: #ffffff;
  box-shadow: 0 10px 28px rgba(0, 0, 0, 0.18);
}

.eyebrow {
  color: #f4d35e;
  font-size: 13px;
  font-weight: 800;
  letter-spacing: 0.12em;
  text-transform: uppercase;
  margin: 0 0 10px;
}

h1 {
  font-size: 36px;
  margin: 0 0 12px;
  color: #ffffff;
}

.intro {
  font-size: 18px;
  line-height: 1.6;
  color: #ffffff;
  max-width: 720px;
  margin: 0;
}

.status-text {
  font-size: 16px;
  color: #555555;
}

.error {
  color: darkred;
  background-color: #ffebee;
  padding: 14px 16px;
  border-radius: 12px;
}

.room-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 24px;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 16px;
  margin-top: 34px;
}

.pagination span {
  font-weight: 600;
  color: #444444;
}

@media (min-width: 768px) {
  .rooms-page {
    padding: 48px;
  }

  .rooms-hero {
    padding: 46px;
  }

  h1 {
    font-size: 48px;
  }

  .room-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (min-width: 1100px) {
  .room-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 480px) {
  .rooms-page {
    padding: 16px;
  }

  .rooms-hero {
    padding: 26px;
  }

  h1 {
    font-size: 34px;
  }

  .pagination {
    flex-direction: column;
    gap: 10px;
  }
}
</style>