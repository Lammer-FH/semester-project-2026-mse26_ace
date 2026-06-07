<template>
  <ion-page>
    <AppHeader class="page-header" />

    <ion-content>
      <section class="booking-page">
        <ion-button class="back-button" fill="clear" :router-link="`/rooms/${roomId}`">
          ← Back to room
        </ion-button>

        <h1>Complete your booking</h1>

        <p v-if="loading">Loading booking details...</p>

        <p v-if="error" class="error">
          Booking details could not be loaded. Please try again later.
        </p>

        <BookingForm
          v-if="room && !loading && !error"
          :room-id="Number(room.id)"
          :room-title="room.title"
          :room-description="room.description"
          :room-image="getMainImage(room)"
          :room-extras="room.extras"
          :check-in="checkIn"
          :check-out="checkOut"
          :number-of-nights="numberOfNights"
        />
      </section>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from "vue"
import { useRoute } from "vue-router"
import { IonPage, IonContent, IonButton } from "@ionic/vue"

import AppHeader from "../components/AppHeader.vue"
import BookingForm from "../components/BookingForm.vue"

import {
  getRoomById,
  type Room
} from "../services/roomService"

const route = useRoute()

const roomId = route.params.id as string
const checkIn = route.query.checkIn as string
const checkOut = route.query.checkOut as string

const room = ref<Room | null>(null)
const loading = ref(false)
const error = ref(false)

const numberOfNights = computed(() => {
  if (!checkIn || !checkOut) {
    return 0
  }

  const start = new Date(checkIn + "T00:00:00")
  const end = new Date(checkOut + "T00:00:00")

  return (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24)
})

async function loadRoom() {
  loading.value = true
  error.value = false

  try {
    room.value = await getRoomById(roomId)
  } catch {
    error.value = true
  } finally {
    loading.value = false
  }
}

function getMainImage(room: Room) {
  const mainImage = room.images?.find((image) => image.isMainImage)

  return (
    mainImage?.url ||
    "https://images.unsplash.com/photo-1566665797739-1674de7a421a"
  )
}

onMounted(() => {
  loadRoom()
})
</script>

<style scoped>
.booking-page {
  padding: 20px;
  max-width: 850px;
  margin: 0 auto;
}

h1 {
  font-size: 32px;
  margin-bottom: 24px;
}

.error {
  color: darkred;
}

@media (min-width: 768px) {
  .booking-page {
    padding: 48px;
  }

  h1 {
    font-size: 42px;
  }
}

@media print {
  .page-header,
  .back-button,
  h1 {
    display: none !important;
  }

  .booking-page {
    padding: 0;
    max-width: 100%;
  }
}
</style>