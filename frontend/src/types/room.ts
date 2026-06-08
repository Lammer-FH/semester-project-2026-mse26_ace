export type RoomImage = {
  id: number
  url: string
  isMainImage: boolean
  sortOrder: number
}

export type Extra = {
  id: number
  name: string
  iconName?: string
  icon_name?: string
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

export type AvailabilityResult = {
  roomId: number
  checkIn: string
  checkOut: string
  available: boolean
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
  createdAt?: string
  priceAtBooking?: number
}