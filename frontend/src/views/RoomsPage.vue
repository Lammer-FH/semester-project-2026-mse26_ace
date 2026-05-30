<template>
  <ion-page>
       <AppHeader />

    <ion-content>
      <section class="rooms-page">
        <h1>Our Rooms</h1>

        <p class="intro">
          Discover our available rooms and choose the one that fits your stay best.
        </p>

        <!-- wird angezeigt falls loading true ist -->
        <p v-if="loading">Loading rooms...</p>

        <!-- wird angezeigt falls error true ist -->
        <p v-if="error" class="error">
          Rooms could not be loaded. Please try again later.
        </p>

        <!-- Zimmerliste wird nur angezeigt, wenn nicht geladen wird und kein Fehler da ist -->
        <div v-if="!loading && !error" class="room-grid">
          <!-- v-for erstellt für jedes sichtbare Zimmer eine Karte -->
          <ion-card v-for="room in visibleRooms" :key="room.id" class="room-card">
            <!-- Zimmerbild -->
            <img :src="room.image" :alt="room.title" />

            <!-- Kopfbereich der Karte -->
            <ion-card-header>
              <!-- Zimmertitel -->
              <ion-card-title>{{ room.title }}</ion-card-title>
            </ion-card-header>

            <!-- Inhalt der Karte -->
           <ion-card-content>
  <p>{{ room.description }}</p>

  <div class="extras">
    <span v-for="extra in room.extras" :key="extra">
      {{ extra }}
    </span>
  </div>

  <ion-button expand="block" :router-link="`/rooms/${room.id}`">
    View details
  </ion-button>
</ion-card-content>
          </ion-card>
        </div>

        <!-- Pagination-Bereich unten -->
        <div v-if="!loading && !error" class="pagination">
          <!-- Previous Button ist deaktiviert, wenn man auf Seite 1 ist -->
          <ion-button :disabled="currentPage === 1" @click="previousPage">
            Previous
          </ion-button>

          <!-- Anzeige der aktuellen Seite -->
          <span>Page {{ currentPage }} of {{ totalPages }}</span>

          <!-- Next Button ist deaktiviert, wenn man auf der letzten Seite ist -->
          <ion-button :disabled="currentPage === totalPages" @click="nextPage">
            Next
          </ion-button>
        </div>
      </section>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import AppHeader from '../components/AppHeader.vue'
/*
  computed = berechnet Werte automatisch neu,
  wenn sich abhängige Daten ändern.

  ref = macht Variablen reaktiv.
  Wenn sich der Wert ändert, aktualisiert Vue automatisch die Anzeige.
*/
import { computed, ref } from 'vue'

/*
  Ionic-Komponenten müssen importiert werden,
  damit sie im Template verwendet werden können.
*/
import {
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonButton,
  IonButtons,
  IonCard,
  IonCardHeader,
  IonCardTitle,
  IonCardContent
} from '@ionic/vue'

/*
  loading zeigt an, ob Daten gerade geladen werden.
  Momentan ist es false, weil wir noch keine echte API verwenden.
*/
const loading = ref(false)

/*
  error zeigt an, ob beim Laden ein Fehler passiert ist.
  Momentan ebenfalls false.
*/
const error = ref(false)

/*
  currentPage merkt sich, auf welcher Seite der Pagination wir gerade sind.
  Start ist Seite 1.
*/
const currentPage = ref(1)

/*
  Pro Seite sollen maximal 5 Zimmer angezeigt werden.
  Das kommt aus der User Story.
*/
const roomsPerPage = 5

/*
  Mock-Daten:
  Diese Zimmer sind aktuell nur Testdaten im Frontend.
  Später werden sie durch API-Daten vom Backend ersetzt.
*/
const rooms = ref([
  {
    id: 1,
    title: 'Single Room',
    description: 'A cozy room for one guest with a comfortable bed and modern design.',
    image: 'https://images.unsplash.com/photo-1566665797739-1674de7a421a',
    extras: ['WiFi', 'TV', 'Desk']
  },
  {
    id: 2,
    title: 'Double Room',
    description: 'A bright room for two guests, perfect for couples or friends.',
    image: 'https://images.unsplash.com/photo-1560448204-e02f11c3d0e2',
    extras: ['WiFi', 'Breakfast', 'TV']
  },
  {
    id: 3,
    title: 'Family Room',
    description: 'A spacious room with enough space for the whole family.',
    image: 'https://images.unsplash.com/photo-1590490360182-c33d57733427',
    extras: ['WiFi', 'Sofa', 'Breakfast']
  },
  {
    id: 4,
    title: 'Business Room',
    description: 'A quiet room with a desk and everything needed for business trips.',
    image: 'https://images.unsplash.com/photo-1611892440504-42a792e24d32',
    extras: ['WiFi', 'Desk', 'Coffee']
  },
  {
    id: 5,
    title: 'Deluxe Room',
    description: 'A modern room with extra comfort and a relaxing atmosphere.',
    image: 'https://images.unsplash.com/photo-1618773928121-c32242e63f39',
    extras: ['WiFi', 'Balcony', 'TV']
  },
  {
    id: 6,
    title: 'Suite',
    description: 'A larger room with elegant design and more space for longer stays.',
    image: 'https://images.unsplash.com/photo-1591088398332-8a7791972843',
    extras: ['WiFi', 'Balcony', 'Breakfast']
  },
  {
    id: 7,
    title: 'Comfort Room',
    description: 'A comfortable room for guests who want a simple and relaxing stay.',
    image: 'https://images.unsplash.com/photo-1598928506311-c55ded91a20c',
    extras: ['WiFi', 'TV', 'Parking']
  }
])

/*
  totalPages berechnet, wie viele Seiten es insgesamt gibt.

  Beispiel:
  7 Zimmer / 5 Zimmer pro Seite = 1,4
  Math.ceil macht daraus 2 Seiten.
*/
const totalPages = computed(() => {
  return Math.ceil(rooms.value.length / roomsPerPage)
})

/*
  visibleRooms berechnet, welche Zimmer auf der aktuellen Seite angezeigt werden.

  Seite 1:
  start = 0
  end = 5
  also Zimmer 1 bis 5

  Seite 2:
  start = 5
  end = 10
  also Zimmer 6 bis 7
*/
const visibleRooms = computed(() => {
  const start = (currentPage.value - 1) * roomsPerPage
  const end = start + roomsPerPage

  return rooms.value.slice(start, end)
})

/*
  Wechselt zur nächsten Seite,
  aber nur wenn man noch nicht auf der letzten Seite ist.
*/
function nextPage() {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

/*
  Wechselt zur vorherigen Seite,
  aber nur wenn man nicht schon auf Seite 1 ist.
*/
function previousPage() {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}
</script>

<style scoped>
/*
  Grundlayout der Seite:
  - Abstand zum Rand
  - maximale Breite auf großen Bildschirmen
  - zentriert auf Desktop
*/
.rooms-page {
  padding: 24px;
  max-width: 1100px;
  margin: 0 auto;
}

/* Überschrift */
h1 {
  font-size: 32px;
  margin-bottom: 8px;
}

/* Einleitungstext */
.intro {
  font-size: 18px;
  color: #555;
  margin-bottom: 24px;
}

/* Fehlermeldung */
.error {
  color: darkred;
}

/*
  DAS IST EIN WICHTIGER RESPONSIVE-TEIL:

  Standardmäßig gibt es nur 1 Spalte.
  Das ist perfekt für mobile Geräte.
*/
.room-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 20px;
}

/* Karten haben keinen zusätzlichen Außenabstand */
.room-card {
  margin: 0;
}

/*
  Bilder werden auf gleiche Höhe gebracht.
  object-fit: cover sorgt dafür, dass Bilder schön zugeschnitten werden,
  statt verzerrt auszusehen.
*/
.room-card img {
  width: 100%;
  height: 220px;
  object-fit: cover;
}

/* Extras werden nebeneinander angezeigt und umbrechen bei wenig Platz */
.extras {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 12px;
}

/* Aussehen der Extra-Labels */
.extras span {
  background-color: #eeeeee;
  padding: 6px 10px;
  border-radius: 12px;
  font-size: 14px;
}

/* Pagination unten */
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 16px;
  margin-top: 32px;
}

/*
  RESPONSIVE-TEIL FÜR TABLETS UND KLEINERE LAPTOPS:

  Ab 768px Bildschirmbreite:
  - mehr Innenabstand
  - größere Überschrift
  - 2 Zimmerkarten nebeneinander
*/
@media (min-width: 768px) {
  .rooms-page {
    padding: 48px;
  }

  h1 {
    font-size: 42px;
  }

  .room-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

/*
  Ab 1024px Bildschirmbreite:
  - 3 Zimmerkarten nebeneinander
*/
@media (min-width: 1024px) {
  .room-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}
</style>