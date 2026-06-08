import axios from "axios"

import type {
  Room,
  AvailabilityResult,
  CreateBookingRequest,
  BookingResponse
} from "../types/room"

const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api"

export async function getRooms(): Promise<Room[]> {
  const response = await axios.get<Room[]>(`${API_BASE_URL}/rooms`)

  return response.data
}

export async function getRoomById(id: string): Promise<Room> {
  const response = await axios.get<Room>(`${API_BASE_URL}/rooms/${id}`)

  return response.data
}

export async function checkRoomAvailability(
  roomId: string,
  checkIn: string,
  checkOut: string
): Promise<AvailabilityResult> {
  const response = await axios.get<AvailabilityResult>(
    `${API_BASE_URL}/rooms/${roomId}/availability`,
    {
      params: {
        checkIn,
        checkOut
      }
    }
  )

  return response.data
}

export async function createBooking(
  booking: CreateBookingRequest
): Promise<BookingResponse> {
  const response = await axios.post<BookingResponse>(
    `${API_BASE_URL}/bookings`,
    booking
  )

  return response.data
}