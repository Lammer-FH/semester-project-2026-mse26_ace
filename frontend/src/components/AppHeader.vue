<template>
  <ion-header>
    <ion-toolbar>
      <ion-title>Hotel Technikum</ion-title>
    </ion-toolbar>

    <ion-toolbar class="nav-toolbar">
      <ion-buttons class="nav-buttons">
        <ion-button router-link="/home">Home</ion-button>
        <ion-button router-link="/rooms">Rooms</ion-button>
        <ion-button router-link="/about">About</ion-button>
        <ion-button router-link="/imprint">Imprint</ion-button>
      </ion-buttons>
    </ion-toolbar>
  </ion-header>
</template>

<script setup lang="ts">
import {
  IonHeader,
  IonToolbar,
  IonTitle,
  IonButtons,
  IonButton
} from '@ionic/vue'
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

@media (max-width: 480px) {
  .nav-buttons ion-button {
    font-size: 13px;
  }
}
</style>