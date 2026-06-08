<template>
  <ion-page>
    <AppHeader />

    <ion-content>
      <section class="imprint-page">
        <div class="hero-card">
          <p class="eyebrow">Legal Information</p>

          <h1>Imprint</h1>

          <p class="hero-text">
            Here you can find the legal and contact information for Hotel Technikum
            and the ACE Escapes project team.
          </p>
        </div>

        <div class="imprint-grid">
          <article class="info-card">
            <div class="card-icon">🏨</div>

            <h2>Hotel Information</h2>

            <p>
              <strong>Hotel Technikum Boutique Hotel</strong><br />
              Technikumstraße 10<br />
              1200 Vienna<br />
              Austria
            </p>
          </article>

          <article class="info-card accent-card">
            <div class="card-icon">📞</div>

            <h2>Contact</h2>

            <p>
              <strong>Email:</strong><br />
              contact@hotel-technikum.example
            </p>

            <p>
              <strong>Phone:</strong><br />
              +43 1 234 5678
            </p>
          </article>

          <article class="info-card">
            <div class="card-icon">👥</div>

            <h2>Responsible for this Website</h2>

            <p>
              <strong>AceEscapes Team</strong><br />
              Advanced Web Frameworks 2026
            </p>
          </article>

          <article class="info-card accent-card">
            <div class="card-icon">💻</div>

            <h2>Project Information</h2>

            <p>
              This website is part of a university project for creating a modern
              hotel booking interface using Vue, Ionic and Spring Boot.
            </p>
          </article>
        </div>

        <section class="notice-card">
          <p class="eyebrow">Note</p>

          <h2>Educational Project</h2>

          <p>
            This application was created for educational purposes as part of the
            Advanced Web Frameworks 2026 course. The hotel and contact data are
            fictional and used only for project demonstration.
          </p>
        </section>
      </section>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import AppHeader from "../components/AppHeader.vue"

import {
  IonPage,
  IonContent
} from "@ionic/vue"
</script>

<style scoped>
.imprint-page {
  padding: 20px;
  max-width: 1100px;
  margin: 0 auto;
}

.hero-card {
  background:
    linear-gradient(135deg, rgba(35, 83, 71, 0.94), rgba(32, 120, 104, 0.86)),
    url("https://images.unsplash.com/photo-1551882547-ff40c63fe5fa");
  background-size: cover;
  background-position: center;
  border-radius: 26px;
  padding: 32px;
  margin-bottom: 26px;
  color: #ffffff;
  box-shadow: 0 10px 28px rgba(0, 0, 0, 0.18);
}

.hero-card .eyebrow,
.hero-card h1,
.hero-card p {
  color: #ffffff;
}

.eyebrow {
  color: #207868;
  font-size: 13px;
  font-weight: 800;
  letter-spacing: 0.12em;
  text-transform: uppercase;
  margin-bottom: 10px;
}

h1 {
  font-size: 36px;
  margin: 0 0 14px;
  color: #222222;
}

h2 {
  font-size: 24px;
  margin: 0 0 14px;
  color: #222222;
}

p {
  font-size: 16px;
  line-height: 1.65;
  color: #555555;
  margin: 0 0 14px;
}

.hero-text {
  font-size: 18px;
  max-width: 720px;
  margin-bottom: 0;
}

.imprint-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 18px;
  margin-bottom: 24px;
}

.info-card,
.notice-card {
  background: var(--ion-card-background, #ffffff);
  border-radius: 20px;
  padding: 24px;
  box-shadow: 0 4px 18px rgba(0, 0, 0, 0.07);
}

.info-card {
  border-top: 5px solid #207868;
}

.accent-card {
  border-top-color: #f4b942;
}

.card-icon {
  width: 46px;
  height: 46px;
  border-radius: 14px;
  background-color: #e8f5f1;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 22px;
  margin-bottom: 14px;
}

.accent-card .card-icon {
  background-color: #fff4d6;
}

.notice-card {
  background: #fffaf0;
  border: 1px solid #f3dfad;
}

.notice-card p:last-child,
.info-card p:last-child {
  margin-bottom: 0;
}

@media (min-width: 768px) {
  .imprint-page {
    padding: 48px;
  }

  .hero-card {
    padding: 46px;
  }

  h1 {
    font-size: 48px;
  }

  h2 {
    font-size: 28px;
  }

  .imprint-grid {
    grid-template-columns: 1fr 1fr;
  }
}

@media (max-width: 480px) {
  .hero-card {
    padding: 26px;
  }

  h1 {
    font-size: 34px;
  }
}
</style>