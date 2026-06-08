<template>
  <article class="room-card">
    <img :src="getMainImage(room)" :alt="room.title" class="room-image" />

    <div class="room-content">
      <h2>{{ room.title }}</h2>

      <p class="room-description">
        {{ room.description }}
      </p>

      <p class="room-meta">
        {{ room.capacity }} guest{{ room.capacity === 1 ? "" : "s" }} ·
        {{ room.sizeSqm }} m² · €{{ room.pricePerNight }} / night
      </p>

      <div class="extras">
        <ExtraBadge
          v-for="extra in room.extras"
          :key="extra.id"
          :extra="extra"
        />
      </div>

      <ion-button
        expand="block"
        :router-link="{ name: 'room-details', params: { id: room.id } }"
      >
        View details
      </ion-button>
    </div>
  </article>
</template>

<script setup lang="ts">
import { IonButton } from "@ionic/vue";

import ExtraBadge from "../atoms/ExtraBadge.vue";
import { useRoomUtils } from "../../composables/useRoomUtils";
import type { Room } from "../../types/room";

defineProps<{
  room: Room;
}>();

const { getMainImage } = useRoomUtils();
</script>

<style scoped>
.room-card {
  background: var(--ion-card-background, #ffffff);
  border-radius: 18px;
  overflow: hidden;
  box-shadow: 0 4px 18px rgba(0, 0, 0, 0.08);
}

.room-image {
  width: 100%;
  height: 240px;
  object-fit: cover;
  display: block;
}

.room-content {
  padding: 20px;
}

h2 {
  font-size: 24px;
  margin: 0 0 12px;
  color: #222222;
}

.room-description {
  font-size: 16px;
  line-height: 1.55;
  color: #666666;
  margin-bottom: 16px;
}

.room-meta {
  font-size: 16px;
  font-weight: 600;
  color: #555555;
  margin-bottom: 16px;
}

.extras {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 18px;
}

ion-button {
  margin-top: 8px;
}

@media (max-width: 480px) {
  .room-image {
    height: 220px;
  }

  .room-content {
    padding: 18px;
  }

  h2 {
    font-size: 22px;
  }
}
</style>
