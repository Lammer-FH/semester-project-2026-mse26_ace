<template>
  <ion-page>
      <AppHeader />

    <ion-content>
      <section class="detail-page">
        <ion-button fill="clear" router-link="/rooms">
          ← Back to rooms
        </ion-button>

        <h1>Room Details</h1>

        <p class="hint">
          Room ID: {{ roomId }}
        </p>

        <div class="detail-layout">
          <div class="room-info">
            <img
              src="https://images.unsplash.com/photo-1566665797739-1674de7a421a"
              alt="Hotel room"
              class="room-image"
            />

            <h2>Selected Room</h2>

            <p>
              Here the selected room information will be displayed later.
              The data will come from the backend API.
            </p>

            <div class="extras">
              <span>📶 WiFi</span>
              <span>📺 TV</span>
              <span>☕ Breakfast</span>
            </div>
          </div>

          <div class="booking-box">
            <h2>Select your stay</h2>

            <div class="date-field">
              <label for="checkIn">Check-in date</label>
              <input id="checkIn" type="date" v-model="checkInDate" />
            </div>

            <div class="date-field">
              <label for="checkOut">Check-out date</label>
              <input id="checkOut" type="date" v-model="checkOutDate" />
            </div>

            <p v-if="dateError" class="error">
              The check-out date cannot be before the check-in date.
            </p>

            <div v-if="checkInDate && checkOutDate && !dateError" class="selected-period">
              <strong>Selected period:</strong>
              <br />
              {{ numberOfNights }} night{{ numberOfNights === 1 ? '' : 's' }} ·
              {{ formatDate(checkInDate) }} → {{ formatDate(checkOutDate) }}
            </div>

            <ion-button expand="block" :disabled="!canContinue">
              Check availability
            </ion-button>
          </div>
        </div>
      </section>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { useRoute } from 'vue-router'
import AppHeader from '../components/AppHeader.vue'

import {
  IonPage,
  IonContent,
  IonButton,
  IonButtons
} from '@ionic/vue'

const route = useRoute()

const roomId = route.params.id

const checkInDate = ref('')
const checkOutDate = ref('')

const dateError = computed(() => {
  if (!checkInDate.value || !checkOutDate.value) {
    return false
  }

  return checkOutDate.value < checkInDate.value
})

const numberOfNights = computed(() => {
  if (!checkInDate.value || !checkOutDate.value || dateError.value) {
    return 0
  }

  const start = new Date(checkInDate.value)
  const end = new Date(checkOutDate.value)

  const difference = end.getTime() - start.getTime()

  return difference / (1000 * 60 * 60 * 24)
})

const canContinue = computed(() => {
  return checkInDate.value && checkOutDate.value && !dateError.value && numberOfNights.value > 0
})

function formatDate(date: string) {
  if (!date) {
    return ''
  }

  const dateObject = new Date(date)

  return dateObject.toLocaleDateString('en-US', {
    month: 'short',
    day: 'numeric',
    year: 'numeric'
  })
}
</script>

<style scoped>
.nav-toolbar {
  --min-height: 44px;
}

.nav-buttons {
  width: 100%;
  display: flex;
  justify-content: center;
  gap: 4px;
}

.detail-page {
  padding: 20px;
  max-width: 1100px;
  margin: 0 auto;
}

h1 {
  font-size: 32px;
  margin-bottom: 8px;
}

.hint {
  color: #666;
  margin-bottom: 24px;
}

.detail-layout {
  display: grid;
  grid-template-columns: 1fr;
  gap: 24px;
}

.room-info,
.booking-box {
  background: var(--ion-card-background, #ffffff);
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
}

.room-image {
  width: 100%;
  height: 260px;
  object-fit: cover;
  border-radius: 12px;
  margin-bottom: 16px;
}

.extras {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 12px;
}

.extras span {
  background-color: #eeeeee;
  color: #222222;
  padding: 6px 10px;
  border-radius: 12px;
  font-size: 14px;
}

.date-field {
  margin-bottom: 16px;
}

.date-field label {
  display: block;
  font-size: 14px;
  margin-bottom: 6px;
  color: #555;
}

.date-field input {
  width: 100%;
  padding: 12px;
  border: 1px solid #cccccc;
  border-radius: 10px;
  font-size: 16px;
  background-color: white;
  color: #222;
}

.error {
  color: darkred;
  margin: 12px 0;
}

.selected-period {
  background-color: #f3f3f3;
  color: #222222;
  padding: 12px;
  border-radius: 12px;
  margin: 16px 0;
  line-height: 1.5;
}

@media (min-width: 768px) {
  .detail-page {
    padding: 48px;
  }

  h1 {
    font-size: 42px;
  }

  .detail-layout {
    grid-template-columns: 2fr 1fr;
    align-items: start;
  }
}

@media (max-width: 480px) {
  .detail-page {
    padding: 16px;
  }

  h1 {
    font-size: 30px;
  }

  .room-image {
    height: 220px;
  }

  .nav-buttons ion-button {
    font-size: 13px;
  }
}
</style>