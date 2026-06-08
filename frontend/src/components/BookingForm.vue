<template>
  <div class="booking-form">
    <div v-if="bookingSuccess" class="confirmation-page">
      <div class="success-box">
        <h2>Booking confirmed</h2>

        <p>✅ Your reservation has been created successfully.</p>

        <p v-if="bookingId"><strong>Booking number:</strong> {{ bookingId }}</p>
      </div>

      <div class="confirmation-section">
        <h3>Your room</h3>

        <img
          :src="roomImage"
          :alt="roomTitle"
          class="confirmation-room-image"
        />

        <h4>{{ roomTitle }}</h4>

        <p>
          {{ roomDescription }}
        </p>

        <div class="confirmation-extras">
          <span v-for="extra in roomExtras" :key="extra.id">
            {{ getExtraIcon(extra.iconName) }} {{ extra.name }}
          </span>
        </div>
      </div>

      <div class="confirmation-section">
        <h3>Booking period</h3>

   

        <p><strong>Check-in:</strong> {{ formatDate(checkIn) }}</p>

        <p><strong>Check-out:</strong> {{ formatDate(checkOut) }}</p>

        <p><strong>Nights:</strong> {{ numberOfNights }}</p>
      </div>

<div class="confirmation-section">
  <h3>Booking details</h3>

  <p v-if="createdAt">
    <strong>Created at:</strong> {{ formatDateTime(createdAt) }}
  </p>

  <p v-if="priceAtBooking !== null">
    <strong>Price at booking:</strong> €{{ priceAtBooking }}
  </p>
</div>

      <div class="confirmation-section">
        <h3>Personal details</h3>

        <p><strong>Name:</strong> {{ firstName }} {{ lastName }}</p>

        <p><strong>Email:</strong> {{ email }}</p>

        <p><strong>Breakfast:</strong> {{ breakfast ? "Yes" : "No" }}</p>
      </div>

      <div class="confirmation-section">
        <h3>Directions and contact</h3>

        <p>
          <strong>Address:</strong> ACE Escapes Hotel, Main Street 12, 1010
          Vienna, Austria
        </p>

        <p><strong>Phone:</strong> +43 1 234 567</p>

        <p><strong>Email:</strong> contact@ace-escapes.example</p>

        <a
          href="https://www.google.com/maps/search/?api=1&query=ACE%20Escapes%20Hotel%20Main%20Street%2012%201010%20Vienna%20Austria"
          target="_blank"
          rel="noopener noreferrer"
        >
          Open directions in Google Maps
        </a>
      </div>

      <ion-button
        expand="block"
        class="print-button"
        @click="printConfirmation"
      >
        Print confirmation
      </ion-button>
    </div>

    <div v-else-if="!showReview">
      <h2>Complete your booking</h2>

      <p class="form-intro">
        Please enter your personal details to continue with the reservation.
      </p>

      <div class="form-field">
        <label for="firstName">First name</label>
        <input
          id="firstName"
          v-model="firstName"
          type="text"
          placeholder="Enter your first name"
        />
        <p v-if="errors.firstName" class="field-error">
          {{ errors.firstName }}
        </p>
      </div>

      <div class="form-field">
        <label for="lastName">Last name</label>
        <input
          id="lastName"
          v-model="lastName"
          type="text"
          placeholder="Enter your last name"
        />
        <p v-if="errors.lastName" class="field-error">
          {{ errors.lastName }}
        </p>
      </div>

      <div class="form-field">
        <label for="email">Email</label>
        <input
          id="email"
          v-model="email"
          type="email"
          placeholder="Enter your email"
        />
        <p v-if="errors.email" class="field-error">
          {{ errors.email }}
        </p>
      </div>

      <div class="form-field">
        <label for="emailConfirm">Confirm email</label>
        <input
          id="emailConfirm"
          v-model="emailConfirm"
          type="email"
          placeholder="Repeat your email"
        />
        <p v-if="errors.emailConfirm" class="field-error">
          {{ errors.emailConfirm }}
        </p>
      </div>

      <div class="breakfast-option">
        <input id="breakfast" v-model="breakfast" type="checkbox" />

        <label for="breakfast"> Add breakfast to my stay </label>
      </div>

      <ion-button expand="block" @click="continueToReview">
        Continue to review
      </ion-button>
    </div>

    <div v-else class="review-step">
      <h2>Review your booking</h2>

      <p class="form-intro">
        Please check your booking details before confirming.
      </p>

      <div class="review-box">
        <h3>Stay details</h3>

        <p><strong>Room:</strong> {{ roomTitle }}</p>

        <p>
          <strong>Period:</strong> {{ formatDate(checkIn) }} →
          {{ formatDate(checkOut) }}
        </p>

        <p><strong>Nights:</strong> {{ numberOfNights }}</p>
      </div>

      <div class="review-box">
        <h3>Guest details</h3>

        <p><strong>Name:</strong> {{ firstName }} {{ lastName }}</p>

        <p><strong>Email:</strong> {{ email }}</p>

        <p><strong>Breakfast:</strong> {{ breakfast ? "Yes" : "No" }}</p>
      </div>

      <div v-if="bookingError" class="booking-error-box">
        <strong>Booking failed</strong>
        <p>
          Your booking could not be completed. Please check your details and try
          again.
        </p>
      </div>

      <div class="review-actions">
        <ion-button
          expand="block"
          fill="outline"
          :disabled="bookingLoading"
          @click="showReview = false"
        >
          Back and edit
        </ion-button>

        <ion-button
          expand="block"
          :disabled="bookingLoading"
          @click="confirmBooking"
        >
          {{ bookingLoading ? "Submitting..." : "Confirm booking" }}
        </ion-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import { IonButton } from "@ionic/vue";
import { useBookingStore } from "../stores/bookingStore";
import { createBooking } from "../services/roomService";

const props = defineProps<{
  roomId: number;
  roomTitle: string;
  roomDescription: string;
  roomImage: string;
  roomExtras: {
    id: number;
    name: string;
    iconName: string;
  }[];
  checkIn: string;
  checkOut: string;
  numberOfNights: number;
}>();

const bookingStore = useBookingStore();

const firstName = ref("");
const lastName = ref("");
const email = ref("");
const emailConfirm = ref("");
const breakfast = ref(false);

const showReview = ref(false);

const bookingLoading = ref(false);
const bookingError = ref(false);
const bookingSuccess = ref(false);
const bookingId = ref<number | null>(null);
const createdAt = ref("");
const priceAtBooking = ref<number | null>(null);
//const bookingStatus = ref("");

const errors = reactive({
  firstName: "",
  lastName: "",
  email: "",
  emailConfirm: "",
});

function continueToReview() {
  clearErrors();

  if (!validateForm()) {
    return;
  }

  bookingStore.setPersonalData(
    firstName.value.trim(),
    lastName.value.trim(),
    email.value.trim(),
    breakfast.value,
  );

  showReview.value = true;
}

async function confirmBooking() {
  bookingError.value = false;
  bookingLoading.value = true;

  try {
    const result = await createBooking({
      roomId: props.roomId,
      checkIn: props.checkIn,
      checkOut: props.checkOut,
      firstName: firstName.value.trim(),
      lastName: lastName.value.trim(),
      email: email.value.trim(),
      breakfast: breakfast.value,
    });
console.log("Booking response:", result)
    bookingId.value = result.id;
    createdAt.value = result.createdAt || "";
    priceAtBooking.value = result.priceAtBooking ?? null;
    //bookingStatus.value = result.status || "";

    bookingStore.setBookingResult(
      result.id,
      result.createdAt,
      result.priceAtBooking,
     // result.status,
    );
    bookingSuccess.value = true;
  } catch {
    bookingError.value = true;
  } finally {
    bookingLoading.value = false;
  }
}

function validateForm() {
  let isValid = true;

  if (!firstName.value.trim()) {
    errors.firstName = "First name is required.";
    isValid = false;
  }

  if (!lastName.value.trim()) {
    errors.lastName = "Last name is required.";
    isValid = false;
  }

  if (!email.value.trim()) {
    errors.email = "Email is required.";
    isValid = false;
  } else if (!isValidEmail(email.value)) {
    errors.email = "Please enter a valid email address.";
    isValid = false;
  }

  if (!emailConfirm.value.trim()) {
    errors.emailConfirm = "Please confirm your email.";
    isValid = false;
  } else if (email.value !== emailConfirm.value) {
    errors.emailConfirm = "The email addresses do not match.";
    isValid = false;
  }

  return isValid;
}

function isValidEmail(value: string) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
}

function clearErrors() {
  errors.firstName = "";
  errors.lastName = "";
  errors.email = "";
  errors.emailConfirm = "";
}

function formatDate(date: string) {
  if (!date) {
    return "";
  }

  const dateObject = new Date(date + "T00:00:00");

  return dateObject.toLocaleDateString("en-US", {
    month: "short",
    day: "numeric",
    year: "numeric",
  });
}

function formatDateTime(value: string) {
  if (!value) {
    return ""
  }

  const dateObject = new Date(value)

  return dateObject.toLocaleString("en-US", {
    month: "short",
    day: "numeric",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit"
  })
}

function printConfirmation() {
  window.print();
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


</script>

<style scoped>
.booking-form {
  background: var(--ion-card-background, #ffffff);
  border-radius: 16px;
  padding: 20px;
  margin-top: 24px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
}

.form-intro {
  color: #555;
  margin-bottom: 20px;
}

.form-field {
  margin-bottom: 16px;
}

.form-field label {
  display: block;
  font-weight: 600;
  margin-bottom: 6px;
}

.form-field input {
  width: 100%;
  padding: 12px;
  border: 1px solid #cccccc;
  border-radius: 10px;
  font-size: 16px;
}

.field-error {
  color: darkred;
  font-size: 14px;
  margin-top: 6px;
}

.breakfast-option {
  display: flex;
  align-items: center;
  gap: 10px;
  margin: 18px 0;
}

.breakfast-option input {
  width: 18px;
  height: 18px;
}

.review-box {
  background-color: #f3f3f3;
  color: #222222;
  padding: 14px;
  border-radius: 12px;
  margin-bottom: 16px;
}

.review-box h3 {
  margin-top: 0;
  margin-bottom: 10px;
}

.review-box p {
  margin: 8px 0;
}

.review-actions {
  display: grid;
  gap: 10px;
}

.success-box {
  background-color: #e8f5e9;
  color: #1b5e20;
  padding: 18px;
  border-radius: 14px;
}

.booking-error-box {
  background-color: #ffebee;
  color: #b71c1c;
  padding: 14px;
  border-radius: 12px;
  margin-bottom: 16px;
}

.booking-error-box p {
  margin: 6px 0 0;
}

.confirmation-page {
  display: grid;
  gap: 18px;
}

.confirmation-section {
  background-color: #f7f7f7;
  color: #222222;
  padding: 16px;
  border-radius: 14px;
}

.confirmation-section h3 {
  margin-top: 0;
}

.confirmation-section p {
  margin: 8px 0;
}

.confirmation-room-image {
  width: 100%;
  max-height: 260px;
  object-fit: cover;
  border-radius: 12px;
  margin-bottom: 12px;
}

.confirmation-extras {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 12px;
}

.confirmation-extras span {
  background-color: #eeeeee;
  color: #222222;
  padding: 6px 10px;
  border-radius: 12px;
  font-size: 14px;
}

.print-button {
  margin-top: 4px;
}

@media print {
  ion-button,
  .print-button,
  .review-actions {
    display: none !important;
  }

  .booking-form {
    box-shadow: none;
    margin: 0;
    padding: 0;
    font-size: 12px;
  }

  .confirmation-page {
    gap: 10px;
  }

  .success-box {
    padding: 10px;
  }

  .confirmation-section {
    padding: 10px;
    border-radius: 8px;
    break-inside: avoid;
    page-break-inside: avoid;
  }

  .confirmation-section h3 {
    margin: 0 0 6px;
    font-size: 16px;
  }

  .confirmation-section h4 {
    margin: 6px 0;
    font-size: 14px;
  }

  .confirmation-section p {
    margin: 4px 0;
  }

  .confirmation-room-image {
    max-height: 120px;
    margin-bottom: 8px;
  }

  .confirmation-extras {
    gap: 6px;
    margin-top: 6px;
  }

  .confirmation-extras span {
    padding: 3px 6px;
    font-size: 11px;
  }

  body {
    background: white;
  }
}
</style>
