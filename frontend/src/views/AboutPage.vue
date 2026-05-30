<template>
  <ion-page>
    <AppHeader />

    <ion-content>
      <section class="about">
        <h1>About Our Hotel</h1>

        <p>
          ACE Escapes is a small boutique hotel created for guests who want a
          comfortable and relaxing stay. Our goal is to offer a calm atmosphere,
          friendly service and modern rooms for short trips, holidays and city visits.
        </p>

        <p>
          The hotel combines simple design with everything guests need during their
          stay. Whether you travel alone, as a couple or with your family, ACE Escapes
          helps you find a room that fits your needs.
        </p>

        <p>
          With our booking app, guests can easily explore rooms, check availability
          and book their stay online.
        </p>
      </section>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import AppHeader from '../components/AppHeader.vue'

import {
  IonPage,
  IonContent
} from '@ionic/vue'
</script>

<style scoped>
.about {
  padding: 24px;
  max-width: 800px;
  margin: 0 auto;
}

h1 {
  font-size: 32px;
  margin-bottom: 16px;
}

p {
  font-size: 18px;
  line-height: 1.6;
  color: #555;
  margin-bottom: 16px;
}

@media (min-width: 768px) {
  .about {
    padding: 48px;
  }

  h1 {
    font-size: 42px;
  }
}
</style>