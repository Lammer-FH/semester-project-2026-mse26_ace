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
import { computed, onMounted } from "vue"
import { useRoute } from "vue-router"

import {
  IonPage,
  IonContent,
  IonButton
} from "@ionic/vue"

import AppHeader from "../components/AppHeader.vue"
import BookingForm from "../components/organisms/BookingForm.vue"
import { useRoomUtils } from "../composables/useRoomUtils"
import { useRoomStore } from "../stores/roomStore"

const route = useRoute()

const roomId = route.params.id as string
const checkIn = route.query.checkIn as string
const checkOut = route.query.checkOut as string

const roomStore = useRoomStore()
const { getMainImage } = useRoomUtils()

const room = computed(() => roomStore.selectedRoom)
const loading = computed(() => roomStore.loading)
const error = computed(() => roomStore.error)

const numberOfNights = computed(() => {
  if (!checkIn || !checkOut) {
    return 0
  }

  const start = new Date(checkIn + "T00:00:00")
  const end = new Date(checkOut + "T00:00:00")

  return (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24)
})

onMounted(() => {
  roomStore.loadRoomById(roomId)
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