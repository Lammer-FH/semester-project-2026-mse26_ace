<template>
  <ion-page>
    <AppHeader />

    <ion-content>
      <section class="hero">
        <img
          src="https://images.unsplash.com/photo-1566073771259-6a8506099945"
          alt="Hotel room"
          class="hero-image"
        />

        <div class="hero-text">
          <h1>Welcome to Hotel Boutique</h1>

          <p>
            Enjoy a relaxing stay at our boutique hotel. Discover comfortable
            rooms, friendly service and everything you need for your next trip.
          </p>

          <ion-button :router-link="{ name: 'rooms' }">
            Browse rooms
          </ion-button>
        </div>
      </section>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import AppHeader from "../components/AppHeader.vue";
import {
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonButton,
  IonButtons,
} from "@ionic/vue";
</script>

<style scoped>
.hero {
  padding: 24px;
  text-align: center;
}

.hero-image {
  width: 100%;
  max-height: 320px;
  object-fit: cover;
  border-radius: 16px;
  margin-bottom: 24px;
}

.hero-text {
  max-width: 700px;
  margin: 0 auto;
}

h1 {
  font-size: 32px;
  margin-bottom: 12px;
}

p {
  font-size: 18px;
  line-height: 1.5;
  color: #555;
  margin-bottom: 24px;
}

@media (min-width: 768px) {
  .hero {
    max-width: 1100px;
    margin: 0 auto;
    padding: 48px;
  }

  h1 {
    font-size: 44px;
  }
}
</style>
