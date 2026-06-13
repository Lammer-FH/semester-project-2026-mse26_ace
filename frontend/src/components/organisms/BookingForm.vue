<template>
  <div class="booking-form">
    <div v-if="bookingSuccess" class="confirmation-page">
      <div class="success-hero">
        <div class="success-icon">✓</div>

        <div>
          <p class="eyebrow">Booking confirmed</p>

          <h2>Your stay is booked</h2>

          <p>
            Thank you for your booking. Your confirmation details are listed
            below.
          </p>
        </div>
      </div>

      <div class="confirmation-grid">
        <section class="confirmation-card room-confirmation-card">
          <img
            :src="roomImage"
            :alt="roomTitle"
            class="confirmation-room-image"
          />

          <div class="confirmation-card-content">
            <p class="section-label">Your room</p>

            <h3>{{ roomTitle }}</h3>

            <p>
              {{ roomDescription }}
            </p>

            <div class="confirmation-extras">
              <ExtraBadge
                v-for="extra in roomExtras"
                :key="extra.id"
                :extra="extra"
              />
            </div>
          </div>
        </section>

        <section class="confirmation-card">
          <p class="section-label">Booking period</p>

          <h3>
            {{ numberOfNights }} night{{ numberOfNights === 1 ? "" : "s" }}
          </h3>

          <div class="detail-row">
            <span>Check-in</span>
            <strong>{{ formatDate(checkIn) }}</strong>
          </div>

          <div class="detail-row">
            <span>Check-out</span>
            <strong>{{ formatDate(checkOut) }}</strong>
          </div>

          <div v-if="bookingId" class="detail-row">
            <span>Booking number</span>
            <strong>#{{ bookingId }}</strong>
          </div>

          <div v-if="createdAt" class="detail-row">
            <span>Created at</span>
            <strong>{{ formatDateTime(createdAt) }}</strong>
          </div>

          <div v-if="priceAtBooking !== null" class="detail-row">
            <span>Total price</span>
            <strong>€{{ priceAtBooking }}</strong>
          </div>
        </section>

        <section class="confirmation-card">
          <p class="section-label">Guest details</p>

          <div class="detail-row">
            <span>Name</span>
            <strong>{{ firstName }} {{ lastName }}</strong>
          </div>

          <div class="detail-row">
            <span>Email</span>
            <strong>{{ email }}</strong>
          </div>

          <div class="detail-row">
            <span>Breakfast</span>
            <strong>{{ breakfast ? "Yes" : "No" }}</strong>
          </div>
        </section>

        <section class="confirmation-card">
          <p class="section-label">Arrival information</p>
          <h3>Hotel Technikum Boutique Hotel</h3>

          <p>
            Technikumstraße 10<br />
            1200 Vienna<br />
            Austria
          </p>

          <a
            class="maps-link"
            href="https://www.google.com/maps/search/?api=1&query=Hotel%20Technikum%20Boutique%20Hotel%20Technikumstraße%2010%201200%20Vienna%20Austria"
            target="_blank"
            rel="noopener noreferrer"
          >
            Open directions in Google Maps
          </a>
        </section>

        <section class="confirmation-card contact-card">
          <p class="section-label">Contact</p>

          <p>
            <strong>Phone:</strong><br />
            +43 1 234 5678
          </p>

          <p>
            <strong>Email:</strong><br />
            contact@hotel-technikum.example
          </p>
        </section>
      </div>

      <ion-button
        expand="block"
        class="print-button"
        color="primary"
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

        <label for="breakfast">Add breakfast to my stay</label>
      </div>

      <ion-button expand="block" color="primary" @click="continueToReview">
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
          color="primary"
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

import { createBooking } from "../../services/roomService";
import { useBookingStore } from "../../stores/bookingStore";
import ExtraBadge from "../atoms/ExtraBadge.vue";

import type { Extra } from "../../types/room";

const props = defineProps<{
  roomId: number;
  roomTitle: string;
  roomDescription: string;
  roomImage: string;
  roomExtras: Extra[];
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

    bookingId.value = result.id;
    createdAt.value = result.createdAt || "";
    priceAtBooking.value = result.priceAtBooking ?? null;

    bookingStore.setBookingResult(
      result.id,
      result.createdAt,
      result.priceAtBooking,
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
    return "";
  }

  const dateObject = new Date(value);

  return dateObject.toLocaleString("en-US", {
    month: "short",
    day: "numeric",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });
}

function printConfirmation() {
  window.print();
}
</script>

<style scoped>
.booking-form {
  background: var(--ion-card-background, #ffffff);
  border-radius: 18px;
  padding: 22px;
  margin-top: 24px;
  box-shadow: 0 4px 18px rgba(0, 0, 0, 0.08);
}

.form-intro {
  color: #555555;
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
  background-color: #f6fbf9;
  color: #222222;
  padding: 14px;
  border-radius: 12px;
  margin-bottom: 16px;
  border-left: 5px solid var(--ion-color-primary, #207868);
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
  display: flex;
  flex-direction: column;
  gap: 22px;
}

.success-hero {
  display: flex;
  gap: 18px;
  align-items: center;
  background: linear-gradient(
    135deg,
    rgba(35, 83, 71, 0.95),
    rgba(32, 120, 104, 0.9)
  );
  color: #ffffff;
  border-radius: 22px;
  padding: 24px;
  box-shadow: 0 8px 22px rgba(0, 0, 0, 0.14);
}

.success-icon {
  min-width: 54px;
  height: 54px;
  border-radius: 50%;
  background-color: #ffffff;
  color: var(--ion-color-primary, #207868);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 30px;
  font-weight: 800;
}

.success-hero .eyebrow,
.success-hero h2,
.success-hero p {
  color: #ffffff;
}

.success-hero h2 {
  font-size: 30px;
  margin: 0 0 8px;
}

.success-hero p {
  margin: 0;
}

.eyebrow,
.section-label {
  color: var(--ion-color-primary, #207868);
  font-size: 12px;
  font-weight: 800;
  letter-spacing: 0.12em;
  text-transform: uppercase;
  margin: 0 0 10px;
}

.confirmation-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 18px;
}

.confirmation-card {
  background: #ffffff;
  border-radius: 18px;
  padding: 20px;
  box-shadow: 0 4px 18px rgba(0, 0, 0, 0.07);
  border-top: 5px solid var(--ion-color-primary, #207868);
}

.confirmation-card h3 {
  font-size: 22px;
  margin: 0 0 12px;
  color: #222222;
}

.confirmation-card p {
  color: #555555;
  line-height: 1.6;
  margin: 0 0 12px;
}

.room-confirmation-card {
  padding: 0;
  overflow: hidden;
}

.confirmation-room-image {
  width: 100%;
  height: 220px;
  object-fit: cover;
  display: block;
}

.confirmation-card-content {
  padding: 20px;
}

.confirmation-extras {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 14px;
}

.detail-row {
  display: flex;
  justify-content: space-between;
  gap: 14px;
  border-bottom: 1px solid #eeeeee;
  padding: 10px 0;
}

.detail-row:last-child {
  border-bottom: none;
}

.detail-row span {
  color: #666666;
}

.detail-row strong {
  color: #222222;
  text-align: right;
}

.maps-link {
  display: inline-block;
  margin-top: 8px;
  color: var(--ion-color-primary, #207868);
  font-weight: 700;
  text-decoration: none;
}

.maps-link:hover {
  text-decoration: underline;
}

.contact-card {
  background: #fffaf0;
  border-top-color: var(--ion-color-secondary, #f4b942);
}

.print-button {
  margin-top: 4px;
}

@media (min-width: 768px) {
  .confirmation-grid {
    grid-template-columns: 1fr 1fr;
  }

  .room-confirmation-card {
    grid-column: span 2;
  }
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

  .success-hero {
    padding: 14px;
    box-shadow: none;
  }

  .success-icon {
    min-width: 38px;
    height: 38px;
    font-size: 22px;
  }

  .success-hero h2 {
    font-size: 22px;
  }

  .confirmation-grid {
    grid-template-columns: 1fr 1fr;
    gap: 10px;
  }

  .confirmation-card {
    padding: 12px;
    box-shadow: none;
    break-inside: avoid;
    page-break-inside: avoid;
  }

  .room-confirmation-card {
    grid-column: span 2;
  }

  .confirmation-card-content {
    padding: 12px;
  }

  .confirmation-room-image {
    height: 120px;
  }

  .confirmation-card h3 {
    font-size: 16px;
    margin-bottom: 6px;
  }

  .confirmation-card p,
  .detail-row {
    font-size: 12px;
  }

  body {
    background: white;
  }
}
</style>
