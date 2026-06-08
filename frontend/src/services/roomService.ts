import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api'

export type RoomImage = {
  id: number
  url: string
  isMainImage: boolean
}

export type Extra = {
  id: number
  name: string
  iconName: string
}

export type Room = {
  id: number
  title: string
  description: string
  pricePerNight: number
  capacity: number
  sizeSqm: number
  images: RoomImage[]
  extras: Extra[]
}

export async function getRooms(): Promise<Room[]> {
  const response = await axios.get<Room[]>(`${API_BASE_URL}/rooms`)
  return response.data
}

export async function getRoomById(id: string): Promise<Room> {
  const response = await axios.get<Room>(`${API_BASE_URL}/rooms/${id}`)
  return response.data
}

export type AvailabilityResult = {
  available: boolean
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

export type CreateBookingRequest = {
  roomId: number
  checkIn: string
  checkOut: string
  firstName: string
  lastName: string
  email: string
  breakfast: boolean
}

export type BookingResponse = {
  id: number
  roomId: number
  checkIn: string
  checkout: string
  firstName: string
  lastName: string
  email: string
  breakfast: boolean
  priceAtBooking?: number
  createdAt?: string
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