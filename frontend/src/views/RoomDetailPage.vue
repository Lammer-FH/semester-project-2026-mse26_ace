<template>
  <ion-page>
    <AppHeader />

    <ion-content>
      <section class="detail-page">
        <ion-button fill="clear" router-link="/rooms">
          ← Back to rooms
        </ion-button>

        <h1>Room Details</h1>

        <p v-if="loading">Loading room...</p>

        <p v-if="error" class="error">
          Room could not be loaded. Please try again later.
        </p>

        <div v-if="room && !loading && !error" class="detail-layout">
          <div class="room-info">
            <img
              :src="getMainImage(room)"
              :alt="room.title"
              class="room-image"
            />

            <h2>{{ room.title }}</h2>

            <p>
              {{ room.description }}
            </p>

            <p class="room-meta">
              <strong>Price:</strong> €{{ room.pricePerNight }} per night
            </p>

            <p class="room-meta">
              <strong>Capacity:</strong>
              {{ room.capacity }} guest{{ room.capacity === 1 ? "" : "s" }}
            </p>

            <p class="room-meta">
              <strong>Size:</strong> {{ room.sizeSqm }} m²
            </p>

            <div class="extras">
              <span v-for="extra in room.extras" :key="extra.id">
                {{ getExtraIcon(extra.iconName) }} {{ extra.name }}
              </span>
            </div>
          </div>

          <div class="booking-box">
            <h2>Select your stay</h2>

            <ion-button
              expand="block"
              fill="outline"
              @click="showCheckInPicker = true"
            >
              Check-in: {{ formatDate(checkInDate) || "Select date" }}
            </ion-button>

            <ion-button
              expand="block"
              fill="outline"
              @click="showCheckOutPicker = true"
            >
              Check-out: {{ formatDate(checkOutDate) || "Select date" }}
            </ion-button>

            <ion-modal
              :is-open="showCheckInPicker"
              @didDismiss="showCheckInPicker = false"
            >
              <ion-content class="date-modal-content">
                <ion-datetime
                  presentation="date"
                  v-model="checkInDate"
                  @ionChange="showCheckInPicker = false"
                ></ion-datetime>
              </ion-content>
            </ion-modal>

            <ion-modal
              :is-open="showCheckOutPicker"
              @didDismiss="showCheckOutPicker = false"
            >
              <ion-content class="date-modal-content">
                <ion-datetime
                  presentation="date"
                  v-model="checkOutDate"
                  @ionChange="showCheckOutPicker = false"
                ></ion-datetime>
              </ion-content>
            </ion-modal>

            <p v-if="dateError" class="error">
              The check-out date cannot be before the check-in date.
            </p>

            <div
              v-if="checkInDate && checkOutDate && !dateError"
              class="selected-period"
            >
              <strong>Selected period:</strong>
              <br />
              {{ numberOfNights }} night{{ numberOfNights === 1 ? "" : "s" }} ·
              {{ formatDate(checkInDate) }} → {{ formatDate(checkOutDate) }}
            </div>

            <ion-button
              expand="block"
              :disabled="availabilityLoading"
              @click="handleCheckAvailability"
            >
              Check availability
            </ion-button>

            <p v-if="dateMissingError" class="error">
              Please select a check-in and check-out date before checking
              availability.
            </p>

            <p v-if="availabilityLoading" class="hint">
              Checking availability...
            </p>

            <div
              v-if="availabilityChecked && isAvailable === true"
              class="availability-result available"
            >
              ✅ This room is available for your selected period.
            </div>

            <div
              v-if="availabilityChecked && isAvailable === false"
              class="availability-result unavailable"
            >
              ❌ This room is not available for your selected period.
            </div>

            <p v-if="availabilityError" class="error">
              Availability could not be checked. Please try again later.
            </p>

           
          </div>
        </div>
      </section>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import AppHeader from "../components/AppHeader.vue";

import {
  IonPage,
  IonContent,
  IonButton,
  IonModal,
  IonDatetime,
} from "@ionic/vue";

import {
  getRoomById,
  checkRoomAvailability,
  type Room,
} from "../services/roomService";

const route = useRoute();
const roomId = route.params.id as string;

const room = ref<Room | null>(null);
const loading = ref(false);
const error = ref(false);

const checkInDate = ref("");
const checkOutDate = ref("");

const showCheckInPicker = ref(false);
const showCheckOutPicker = ref(false);

const availabilityLoading = ref(false);
const availabilityError = ref(false);
const availabilityChecked = ref(false);
const isAvailable = ref<boolean | null>(null);
const dateMissingError = ref(false);

const dateError = computed(() => {
  if (!checkInDate.value || !checkOutDate.value) {
    return false;
  }

  return checkOutDate.value < checkInDate.value;
});

const numberOfNights = computed(() => {
  if (!checkInDate.value || !checkOutDate.value || dateError.value) {
    return 0;
  }

  const start = new Date(checkInDate.value);
  const end = new Date(checkOutDate.value);

  const difference = end.getTime() - start.getTime();

  return difference / (1000 * 60 * 60 * 24);
});

const canContinue = computed(() => {
  return (
    checkInDate.value &&
    checkOutDate.value &&
    !dateError.value &&
    numberOfNights.value > 0
  );
});

async function loadRoom() {
  loading.value = true;
  error.value = false;

  try {
    room.value = await getRoomById(roomId);
  } catch {
    error.value = true;
  } finally {
    loading.value = false;
  }
}

function getMainImage(room: Room) {
  const mainImage = room.images?.find((image) => image.isMainImage);

  return (
    mainImage?.url ||
    "https://images.unsplash.com/photo-1566665797739-1674de7a421a"
  );
}

function getExtraIcon(iconName: string) {
  if (iconName === "wifi") return "📶";
  if (iconName === "coffee") return "☕";
  if (iconName === "car") return "🅿️";
  if (iconName === "tv") return "📺";
  if (iconName === "wind") return "❄️";
  if (iconName === "spa") return "🧖";

  return "✨";
}

function formatDate(date: string) {
  if (!date) {
    return "";
  }

  const dateObject = new Date(date);

  return dateObject.toLocaleDateString("en-US", {
    month: "short",
    day: "numeric",
    year: "numeric",
  });
}

onMounted(() => {
  loadRoom();
});

async function handleCheckAvailability() {
  dateMissingError.value = false;
  availabilityError.value = false;
  availabilityChecked.value = false;
  isAvailable.value = null;

  if (!checkInDate.value || !checkOutDate.value) {
    dateMissingError.value = true;
    return;
  }

  if (dateError.value) {
    return;
  }

  availabilityLoading.value = true;

  try {
    const result = await checkRoomAvailability(
      roomId,
      checkInDate.value,
      checkOutDate.value,
    );

    isAvailable.value = result.available;
    availabilityChecked.value = true;
  } catch {
    availabilityError.value = true;
  } finally {
    availabilityLoading.value = false;
  }
}
</script>

<style scoped>
.detail-page {
  padding: 20px;
  max-width: 1100px;
  margin: 0 auto;
}

h1 {
  font-size: 32px;
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

.room-meta {
  color: #555;
  margin-bottom: 8px;
}

.extras {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 16px;
}

.extras span {
  background-color: #eeeeee;
  color: #222222;
  padding: 6px 10px;
  border-radius: 12px;
  font-size: 14px;
}

.booking-box ion-button {
  margin-bottom: 12px;
}

.date-modal-content {
  display: flex;
  justify-content: center;
  align-items: center;
}

ion-datetime {
  margin: 24px auto;
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

.hint {
  color: #666;
  font-size: 14px;
  margin-top: 12px;
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
}

.availability-result {
  padding: 12px;
  border-radius: 12px;
  margin-top: 16px;
  line-height: 1.5;
  font-weight: 500;
}

.availability-result.available {
  background-color: #e8f5e9;
  color: #1b5e20;
}

.availability-result.unavailable {
  background-color: #ffebee;
  color: #b71c1c;
}
</style>

