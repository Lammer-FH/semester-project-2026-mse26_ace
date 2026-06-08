<template>
  <div class="booking-box">
    <h2>Select your stay</h2>

    <p class="calendar-instruction">
      {{ calendarInstruction }}
    </p>

    <ion-datetime
      presentation="date"
      :value="calendarValue"
      @ionChange="selectCalendarDate($event.detail.value)"
    />

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

      <button class="reset-button" @click="resetDates">Reset</button>
    </div>

    <ion-button
      expand="block"
      :disabled="availabilityLoading"
      @click="handleCheckAvailability"
    >
      Check availability
    </ion-button>

    <p v-if="dateMissingError" class="error">
      Please select a check-in and check-out date before checking availability.
    </p>

    <p v-if="availabilityLoading" class="hint">Checking availability...</p>

    <div
      v-if="availabilityChecked && isAvailable === true"
      class="availability-result available"
    >
      ✅ This room is available for your selected period.
    </div>

    <ion-button
      v-if="availabilityChecked && isAvailable === true"
      expand="block"
      :router-link="bookingLink"
    >
      Continue to booking
    </ion-button>

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
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import { IonButton, IonDatetime } from "@ionic/vue";

import { checkRoomAvailability } from "../../services/roomService";
import { useBookingStore } from "../../stores/bookingStore";

const props = defineProps<{
  roomId: number;
}>();

const bookingStore = useBookingStore();

const checkInDate = ref("");
const checkOutDate = ref("");

const availabilityLoading = ref(false);
const availabilityError = ref(false);
const availabilityChecked = ref(false);
const isAvailable = ref<boolean | null>(null);
const dateMissingError = ref(false);

const calendarValue = computed(() => {
  return checkOutDate.value || checkInDate.value || "";
});

const calendarInstruction = computed(() => {
  if (!checkInDate.value) {
    return "Select your check-in date.";
  }

  if (!checkOutDate.value) {
    return "Now select your check-out date.";
  }

  return "Your stay period is selected. Click another date to start again.";
});

const dateError = computed(() => {
  if (!checkInDate.value || !checkOutDate.value) {
    return false;
  }

  return toApiDate(checkOutDate.value) < toApiDate(checkInDate.value);
});

const numberOfNights = computed(() => {
  if (!checkInDate.value || !checkOutDate.value || dateError.value) {
    return 0;
  }

  const start = new Date(toApiDate(checkInDate.value) + "T00:00:00");
  const end = new Date(toApiDate(checkOutDate.value) + "T00:00:00");

  const difference = end.getTime() - start.getTime();

  return difference / (1000 * 60 * 60 * 24);
});

const bookingLink = computed(() => {
  const checkIn = toApiDate(checkInDate.value);
  const checkOut = toApiDate(checkOutDate.value);

  bookingStore.setBookingPeriod(props.roomId, checkIn, checkOut);
  return {
    name: "booking",
    params: {
      id: props.roomId,
    },
    query: {
      checkIn,
      checkOut,
    },
  };
});

function selectCalendarDate(value: string | string[] | null | undefined) {
  if (typeof value !== "string") {
    return;
  }

  if (!checkInDate.value || checkOutDate.value) {
    checkInDate.value = value;
    checkOutDate.value = "";
  } else {
    checkOutDate.value = value;
  }

  availabilityError.value = false;
  availabilityChecked.value = false;
  dateMissingError.value = false;
  isAvailable.value = null;
}

function resetDates() {
  checkInDate.value = "";
  checkOutDate.value = "";

  availabilityError.value = false;
  availabilityChecked.value = false;
  dateMissingError.value = false;
  isAvailable.value = null;
}

function formatDate(date: string) {
  if (!date) {
    return "";
  }

  const dateOnly = toApiDate(date);
  const dateObject = new Date(dateOnly + "T00:00:00");

  return dateObject.toLocaleDateString("en-US", {
    month: "short",
    day: "numeric",
    year: "numeric",
  });
}

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
      String(props.roomId),
      toApiDate(checkInDate.value),
      toApiDate(checkOutDate.value),
    );

    isAvailable.value = result.available;
    availabilityChecked.value = true;
  } catch {
    availabilityError.value = true;
  } finally {
    availabilityLoading.value = false;
  }
}

function toApiDate(date: string) {
  if (!date) {
    return "";
  }

  return date.split("T")[0];
}
</script>

<style scoped>
.booking-box {
  background: var(--ion-card-background, #ffffff);
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
}

.booking-box ion-button {
  margin-top: 16px;
}

.calendar-instruction {
  color: #555555;
  font-size: 15px;
  margin-bottom: 12px;
}

.booking-box ion-datetime {
  width: 100%;
  max-width: 360px;
  margin: 0 auto 16px;
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

.reset-button {
  display: block;
  margin-top: 12px;
  border: none;
  background: transparent;
  color: var(--ion-color-primary, #207868);
  text-decoration: underline;
  font-size: 15px;
  cursor: pointer;
  padding: 0;
}

.hint {
  color: #666666;
  font-size: 14px;
  margin-top: 12px;
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
