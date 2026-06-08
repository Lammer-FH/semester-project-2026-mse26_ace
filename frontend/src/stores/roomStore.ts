import { defineStore } from "pinia"
import { getRooms, getRoomById } from "../services/roomService"
import type { Room } from "../types/room"

export const useRoomStore = defineStore("rooms", {
  state: () => ({
    rooms: [] as Room[],
    selectedRoom: null as Room | null,
    loading: false,
    error: false
  }),

  actions: {
    async loadRooms() {
      if (this.rooms.length > 0) {
        return
      }

      this.loading = true
      this.error = false

      try {
        this.rooms = await getRooms()
      } catch {
        this.error = true
      } finally {
        this.loading = false
      }
    },

    async loadRoomById(roomId: string) {
      this.loading = true
      this.error = false

      const cachedRoom = this.rooms.find((room) => String(room.id) === roomId)

      if (cachedRoom) {
        this.selectedRoom = cachedRoom
        this.loading = false
        return
      }

      try {
        this.selectedRoom = await getRoomById(roomId)
      } catch {
        this.error = true
      } finally {
        this.loading = false
      }
    }
  }
})