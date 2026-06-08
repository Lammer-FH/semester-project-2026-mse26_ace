<template>
  <ion-page>
    <AppHeader />

    <ion-content>
      <section class="about-page">
        <div class="hero-card">
          <div class="hero-content">
            <p class="eyebrow">Boutique Hotel Technikum</p>

            <h1>A calm stay in the heart of the city</h1>

            <p class="hero-text">
              Hotel Technikum is a modern boutique hotel for guests who want a
              comfortable, friendly and well-organized stay.
            </p>
          </div>

          <div class="hero-highlight">
            <span>Online booking</span>
            <strong>Rooms, availability and confirmation in one simple flow.</strong>
          </div>
        </div>

        <div class="content-grid">
          <article class="info-card">
            <div class="card-icon">🏨</div>

            <h2>Our Hotel</h2>

            <p>
              Hotel Technikum offers a relaxing atmosphere, friendly service and
              modern rooms for short trips, holidays and city visits.
            </p>

            <p>
              Whether you travel alone, as a couple or with your family, our hotel
              helps you find a room that fits your needs.
            </p>
          </article>

          <article class="info-card accent-card">
            <div class="card-icon">✨</div>

            <h2>What You Can Expect</h2>

            <p>
              Guests can explore available rooms, compare room details, check
              availability and complete the booking online.
            </p>

            <p>
              After a successful booking, a confirmation is shown with all important
              information, including room details, travel dates, contact information
              and directions.
            </p>
          </article>
        </div>

        <section class="booking-process">
          <p class="eyebrow">Booking process</p>

          <h2>How booking works</h2>

          <div class="steps">
            <div class="step-card">
              <div class="step-number">1</div>

              <div>
                <h3>Choose a room</h3>

                <p>
                  Browse the room overview and compare images, descriptions, prices
                  and extras.
                </p>
              </div>
            </div>

            <div class="step-card">
              <div class="step-number">2</div>

              <div>
                <h3>Select your stay</h3>

                <p>
                  Choose your check-in and check-out dates and check whether the room
                  is available for the full period.
                </p>
              </div>
            </div>

            <div class="step-card">
              <div class="step-number">3</div>

              <div>
                <h3>Complete your booking</h3>

                <p>
                  Enter your personal details, choose whether you want breakfast and
                  review your booking before submitting it.
                </p>
              </div>
            </div>

            <div class="step-card">
              <div class="step-number">4</div>

              <div>
                <h3>Receive confirmation</h3>

                <p>
                  After a successful booking, you receive a confirmation with your
                  room details, booking period, contact information and directions.
                </p>
              </div>
            </div>
          </div>
        </section>

        <section class="guest-info">
          <p class="eyebrow">Guest information</p>

          <h2>Before, During and After Your Stay</h2>

          <div class="guest-info-grid">
            <div class="guest-info-card">
              <h3>Before booking</h3>

              <p>
                Check the available rooms, select your travel period and review the
                room information before continuing.
              </p>
            </div>

            <div class="guest-info-card">
              <h3>During booking</h3>

              <p>
                Provide complete and correct personal data. Before the booking is
                submitted, you can review all information again.
              </p>
            </div>

            <div class="guest-info-card">
              <h3>After booking</h3>

              <p>
                Keep your confirmation. It contains your booking number, room details,
                travel dates and hotel contact information.
              </p>
            </div>
          </div>
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
.about-page {
  padding: 20px;
  max-width: 1100px;
  margin: 0 auto;
}

.hero-card {
  display: grid;
  gap: 24px;
  background:
    linear-gradient(135deg, rgba(35, 83, 71, 0.92), rgba(32, 120, 104, 0.86)),
    url("https://images.unsplash.com/photo-1566073771259-6a8506099945");
  background-size: cover;
  background-position: center;
  border-radius: 26px;
  padding: 32px;
  margin-bottom: 26px;
  color: #ffffff;
  box-shadow: 0 10px 28px rgba(0, 0, 0, 0.18);
}

.hero-content {
  max-width: 720px;
}

.hero-card .eyebrow,
.hero-card h1,
.hero-card p {
  color: #ffffff;
}

.hero-highlight {
  background: rgba(255, 255, 255, 0.16);
  border: 1px solid rgba(255, 255, 255, 0.24);
  border-radius: 18px;
  padding: 18px;
  backdrop-filter: blur(8px);
}

.hero-highlight span {
  display: block;
  font-size: 13px;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.1em;
  margin-bottom: 8px;
  color: #f4d35e;
}

.hero-highlight strong {
  font-size: 18px;
  line-height: 1.4;
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

h3 {
  font-size: 18px;
  margin: 0 0 8px;
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
  max-width: 760px;
  margin-bottom: 0;
}

.content-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 18px;
  margin-bottom: 24px;
}

.info-card,
.booking-process,
.guest-info {
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

.info-card p:last-child,
.step-card p:last-child,
.guest-info-card p:last-child {
  margin-bottom: 0;
}

.booking-process {
  background: linear-gradient(180deg, #ffffff, #f6fbf9);
  margin-bottom: 24px;
}

.steps {
  display: grid;
  grid-template-columns: 1fr;
  gap: 14px;
  margin-top: 18px;
}

.step-card {
  display: flex;
  gap: 14px;
  align-items: flex-start;
  background-color: #ffffff;
  border: 1px solid #e3eee9;
  border-radius: 16px;
  padding: 16px;
}

.step-number {
  min-width: 36px;
  height: 36px;
  border-radius: 50%;
  background: linear-gradient(135deg, #207868, #34a58e);
  color: #ffffff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 800;
}

.guest-info {
  background: #fffaf0;
  border: 1px solid #f3dfad;
}

.guest-info-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 18px;
}

.guest-info-card {
  background: #ffffff;
  border-radius: 16px;
  padding: 18px;
  border-left: 5px solid #f4b942;
}

@media (min-width: 768px) {
  .about-page {
    padding: 48px;
  }

  .hero-card {
    grid-template-columns: 2fr 1fr;
    align-items: end;
    padding: 46px;
  }

  h1 {
    font-size: 48px;
  }

  h2 {
    font-size: 28px;
  }

  .content-grid {
    grid-template-columns: 1fr 1fr;
  }

  .steps {
    grid-template-columns: 1fr 1fr;
  }

  .guest-info-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 480px) {
  .hero-card {
    padding: 26px;
  }

  h1 {
    font-size: 34px;
  }

  .hero-highlight strong {
    font-size: 16px;
  }
}
</style>