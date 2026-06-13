<template>
  <span class="extra-badge">
    <ion-icon
      class="extra-icon"
      :icon="getExtraIcon(extra)"
    />

    {{ extra.name }}
  </span>
</template>

<script setup lang="ts">
import { IonIcon } from "@ionic/vue"
import { useRoomUtils } from "../../composables/useRoomUtils"
import type { Extra } from "../../types/room"

defineProps<{
  extra: Extra
}>()

const { getExtraIcon } = useRoomUtils()
</script>

<style scoped>
.extra-badge {
  display: inline-flex;
  align-items: center;
  gap: 7px;
  background-color: #e8f5f1;
  color: #234f47;
  padding: 7px 11px;
  border-radius: 14px;
  font-size: 14px;
  font-weight: 600;
}

.extra-icon {
  font-size: 17px;
  color: #207868;
}
</style>