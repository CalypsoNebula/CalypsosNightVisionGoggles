package settingdust.calypsos_nightvision_goggles.item.nightvision_goggles

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.inventory.Slot
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesModeHandler.Companion.mode
import settingdust.calypsos_nightvision_goggles.util.ContainerType
import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil

interface NightvisionGogglesNetworking {
    companion object : NightvisionGogglesNetworking by ServiceLoaderUtil.findService<NightvisionGogglesNetworking>() {
        fun handleSwitchMode(
            slotIndex: Int,
            containerType: ContainerType<*>,
            sender: ServerPlayer,
            data: ContainerType.Data
        ) {
            val stack = (containerType as ContainerType<ContainerType.Data>).getItem(slotIndex, sender, data)
            if (stack.item is NightvisionGogglesItem) {
                stack.mode =
                    NightvisionGogglesModeHandler.Mode.entries[
                        stack.mode?.ordinal?.let { (it + 1) % NightvisionGogglesModeHandler.Mode.entries.size } ?: 0
                    ]
            }
        }
    }

    fun c2sSwitchMode(slot: Slot)

}