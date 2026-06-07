import { defineStore } from "pinia"

export const useBookingStore = defineStore("booking", {
  state: () => ({
    roomId: 0,
    checkIn: "",
    checkOut: "",
    firstName: "",
    lastName: "",
    email: "",
    breakfast: false,
    bookingId: null as number | null,
    createdAt: "",
    priceAtBooking: null as number | null,
    status: ""
  }),

  actions: {
    setBookingPeriod(roomId: number, checkIn: string, checkOut: string) {
      this.roomId = roomId
      this.checkIn = checkIn
      this.checkOut = checkOut
    },

    setPersonalData(
      firstName: string,
      lastName: string,
      email: string,
      breakfast: boolean
    ) {
      this.firstName = firstName
      this.lastName = lastName
      this.email = email
      this.breakfast = breakfast
    },

    setBookingResult(
      bookingId: number,
      createdAt?: string,
      priceAtBooking?: number,
      status?: string
    ) {
      this.bookingId = bookingId
      this.createdAt = createdAt || ""
      this.priceAtBooking = priceAtBooking ?? null
      this.status = status || ""
    },

    clearBooking() {
      this.roomId = 0
      this.checkIn = ""
      this.checkOut = ""
      this.firstName = ""
      this.lastName = ""
      this.email = ""
      this.breakfast = false
      this.bookingId = null
      this.createdAt = ""
      this.priceAtBooking = null
      this.status = ""
    }
  }
})