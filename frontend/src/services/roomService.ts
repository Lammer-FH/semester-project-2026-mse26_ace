import { apiClient } from "./apiClient"

import type {
  Room,
  AvailabilityResult,
  CreateBookingRequest,
  BookingResponse
} from "../types/room"


export async function getRooms(): Promise<Room[]> {
  const response = await apiClient.get<Room[]>("/rooms")

  return response.data
}

export async function getRoomById(id: string): Promise<Room> {
  const response = await apiClient.get<Room>(`/rooms/${id}`)

  return response.data
}

export async function checkRoomAvailability(
  roomId: string,
  checkIn: string,
  checkOut: string
): Promise<AvailabilityResult> {
  const response = await apiClient.get<AvailabilityResult>("/rooms/availability", {
  params: {
    roomId,
    checkIn,
    checkOut
  }
})

  return response.data
}

export async function createBooking(
  booking: CreateBookingRequest
): Promise<BookingResponse> {
  const response = await apiClient.post<BookingResponse>(
  "/bookings",
  booking
)


  return response.data
}