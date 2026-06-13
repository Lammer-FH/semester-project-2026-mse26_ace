import type { Room } from "../types/room"

import {
  wifiOutline,
  cafeOutline,
  carOutline,
  tvOutline,
  snowOutline,
  leafOutline,
  sparklesOutline
} from "ionicons/icons"

type ExtraLike = {
  name: string
  iconName?: string
  icon_name?: string
}

export function useRoomUtils() {
  function getMainImage(room: Room) {
    const mainImage = room.images?.find((image) => {
      return image.isMainImage || image.isPrimary
    })

    const imagePath = mainImage?.url || mainImage?.filePath

    if (!imagePath) {
      return "https://images.unsplash.com/photo-1566665797739-1674de7a421a"
    }

    if (imagePath.startsWith("http")) {
      return imagePath
    }

    return `http://localhost:8080${imagePath}`
  }

  function getExtraIcon(extra: ExtraLike) {
    const iconName = extra.iconName || extra.icon_name || ""
    const extraName = extra.name.toLowerCase()

    switch (iconName) {
      case "wifi":
        return wifiOutline
      case "coffee":
        return cafeOutline
      case "car":
        return carOutline
      case "tv":
        return tvOutline
      case "wind":
        return snowOutline
      case "spa":
        return leafOutline
    }

    switch (extraName) {
      case "wi-fi":
        return wifiOutline
      case "breakfast":
        return cafeOutline
      case "parking":
        return carOutline
      case "smart tv":
        return tvOutline
      case "air con":
        return snowOutline
      case "spa access":
        return leafOutline
      default:
        return sparklesOutline
    }
  }

  return {
    getMainImage,
    getExtraIcon
  }
}