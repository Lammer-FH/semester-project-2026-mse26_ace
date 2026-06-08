<template>
  <ion-page>
    <AppHeader />

    <ion-content>
      <section class="detail-page">
        <ion-button
          class="back-button"
          fill="clear"
          :router-link="{ name: 'rooms' }"
        >
          ← Back to rooms
        </ion-button>

        <div class="detail-hero">
          <p class="eyebrow">Room Details</p>

          <h1>Plan your perfect stay</h1>

          <p>
            Choose your travel period, check availability and continue with your
            booking in just a few steps.
          </p>
        </div>

        <p v-if="loading" class="status-text">Loading room...</p>

        <p v-if="error" class="error">
          Room could not be loaded. Please try again later.
        </p>

        <div v-if="room && !loading && !error" class="detail-layout">
          <article class="room-detail-card">
            <div class="image-wrapper">
              <img
                :src="getMainImage(room)"
                :alt="room.title"
                class="room-image"
              />
            </div>

            <div class="room-content">
              <p class="eyebrow">Selected room</p>

              <h2>{{ room.title }}</h2>

              <p class="room-description">
                {{ room.description }}
              </p>

              <div class="room-facts">
                <div class="fact-box">
                  <span>Price</span>
                  <strong>€{{ room.pricePerNight }}</strong>
                  <small>per night</small>
                </div>

                <div class="fact-box">
                  <span>Capacity</span>
                  <strong>{{ room.capacity }}</strong>
                  <small> guest{{ room.capacity === 1 ? "" : "s" }} </small>
                </div>

                <div class="fact-box">
                  <span>Size</span>
                  <strong>{{ room.sizeSqm }}</strong>
                  <small>m²</small>
                </div>
              </div>

              <div class="extras-section">
                <h3>Included extras</h3>

                <div class="extras">
                  <ExtraBadge
                    v-for="extra in room.extras"
                    :key="extra.id"
                    :extra="extra"
                  />
                </div>
              </div>
            </div>
          </article>

          <aside class="booking-column">
            <BookingWidget :room-id="Number(room.id)" />
          </aside>
        </div>
      </section>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { computed, onMounted } from "vue";
import { useRoute } from "vue-router";

import { IonPage, IonContent, IonButton } from "@ionic/vue";

import AppHeader from "../components/AppHeader.vue";
import ExtraBadge from "../components/atoms/ExtraBadge.vue";
import BookingWidget from "../components/molecules/BookingWidget.vue";

import { useRoomUtils } from "../composables/useRoomUtils";
import { useRoomStore } from "../stores/roomStore";

const route = useRoute();
const roomId = route.params.id as string;

const roomStore = useRoomStore();

const room = computed(() => roomStore.selectedRoom);
const loading = computed(() => roomStore.loading);
const error = computed(() => roomStore.error);

const { getMainImage } = useRoomUtils();

onMounted(() => {
  roomStore.loadRoomById(roomId);
});
</script>

<style scoped>
.detail-page {
  min-height: 100%;
  padding: 18px 22px 32px;
  background: linear-gradient(180deg, #f4f7f5 0%, #ffffff 45%);
}

.detail-page > * {
  max-width: 1180px;
  margin-left: auto;
  margin-right: auto;
}

.back-button {
  --color: var(--ion-color-primary, #207868);
  margin-bottom: 12px;
  font-weight: 700;
  letter-spacing: 0.04em;
}

.detail-hero {
  background:
    linear-gradient(135deg, rgba(35, 83, 71, 0.94), rgba(32, 120, 104, 0.88)),
    url("https://images.unsplash.com/photo-1566073771259-6a8506099945");
  background-size: cover;
  background-position: center;
  color: #ffffff;
  border-radius: 24px;
  padding: 22px 28px;
  margin-bottom: 22px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.14);
}

.eyebrow {
  color: #f4d35e;
  font-size: 12px;
  font-weight: 800;
  letter-spacing: 0.14em;
  text-transform: uppercase;
  margin: 0 0 10px;
}
.detail-hero h1 {
  font-size: 34px;
  line-height: 1.1;
  margin: 0 0 8px;
  color: #ffffff;
}

.detail-hero p {
  max-width: 720px;
  font-size: 16px;
  line-height: 1.5;
  margin: 0;
  color: #ffffff;
}

.status-text {
  color: #555555;
  font-size: 16px;
}

.error {
  color: darkred;
  background-color: #ffebee;
  padding: 14px 16px;
  border-radius: 12px;
}

.detail-layout {
  display: grid;
  grid-template-columns: 1fr;
  gap: 24px;
  align-items: start;
}

.room-detail-card {
  background: #ffffff;
  border-radius: 24px;
  overflow: hidden;
  box-shadow: 0 8px 26px rgba(0, 0, 0, 0.08);
}

.image-wrapper {
  background-color: #eeeeee;
}

.room-image {
  width: 100%;
  height: 330px;
  object-fit: cover;
  display: block;
}

.room-content {
  padding: 26px;
}

.room-content .eyebrow {
  color: var(--ion-color-primary, #207868);
}

.room-content h2 {
  font-size: 34px;
  margin: 0 0 14px;
  color: #222222;
}

.room-description {
  font-size: 17px;
  line-height: 1.7;
  color: #555555;
  margin-bottom: 22px;
}

.room-facts {
  display: grid;
  grid-template-columns: 1fr;
  gap: 12px;
  margin-bottom: 26px;
}

.fact-box {
  background-color: #f6fbf9;
  border-left: 5px solid var(--ion-color-primary, #207868);
  border-radius: 14px;
  padding: 16px;
}

.fact-box span {
  display: block;
  color: #666666;
  font-size: 13px;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.08em;
  margin-bottom: 6px;
}

.fact-box strong {
  display: block;
  color: #222222;
  font-size: 26px;
  line-height: 1.1;
}

.fact-box small {
  color: #666666;
}

.extras-section h3 {
  font-size: 20px;
  margin: 0 0 12px;
  color: #222222;
}

.extras {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.booking-column {
  position: relative;
}

@media (min-width: 768px) {
  .detail-page {
    padding: 28px 42px 42px;
  }

  .detail-layout {
    grid-template-columns: minmax(0, 2fr) 390px;
  }

  .booking-column {
    position: sticky;
    top: 24px;
  }

  .room-facts {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 480px) {
  .detail-page {
    padding: 14px;
  }

  .detail-hero {
    padding: 20px;
    border-radius: 20px;
  }

  .detail-hero h1 {
    font-size: 28px;
  }

  .detail-hero p {
    font-size: 16px;
  }

  .room-image {
    height: 230px;
  }

  .room-content {
    padding: 20px;
  }

  .room-content h2 {
    font-size: 28px;
  }
}
</style>
