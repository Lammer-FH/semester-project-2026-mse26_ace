P
<template>
  <ion-page>
      <AppHeader />

    <ion-content>
      <section class="imprint">
        <h1>Imprint</h1>

        <h2>Hotel Information</h2>
        <p>
          Hotel Technikum Boutique Hotel<br />
          Technikumstraße 10<br />
          1200 Vienna<br />
          Austria
        </p>

        <h2>Contact</h2>
        <p>
          Email: contact@hotel-technikum.example<br />
          Phone: +43 1 234 5678
        </p>

        <h2>Responsible for this website</h2>
        <p>
          AceEscapes Team<br />
          Advanced Web Frameworks 2026
        </p>
      </section>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import AppHeader from '../components/AppHeader.vue'
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
.imprint {
  padding: 24px;
  max-width: 800px;
  margin: 0 auto;
}

h1 {
  font-size: 32px;
  margin-bottom: 24px;
}

h2 {
  font-size: 22px;
  margin-top: 24px;
  margin-bottom: 8px;
}

p {
  font-size: 18px;
  line-height: 1.6;
  color: #555;
}

@media (min-width: 768px) {
  .imprint {
    padding: 48px;
  }

  h1 {
    font-size: 42px;
  }
}
</style>
