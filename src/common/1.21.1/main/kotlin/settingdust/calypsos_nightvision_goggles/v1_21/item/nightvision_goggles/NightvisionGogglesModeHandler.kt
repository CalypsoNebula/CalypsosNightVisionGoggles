package settingdust.calypsos_nightvision_goggles.v1_21.item.nightvision_goggles

import net.minecraft.world.item.ItemStack
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesModeHandler
import settingdust.calypsos_nightvision_goggles.v1_21.CalypsosNightVisionGogglesDataComponents

class NightvisionGogglesModeHandler : NightvisionGogglesModeHandler {
    override var ItemStack.mode: NightvisionGogglesModeHandler.Mode?
        get() {
            val mode = get(CalypsosNightVisionGogglesDataComponents.MODE) ?: return null
            return mode
        }
        set(value) {
            if (value != null) {
                set(CalypsosNightVisionGogglesDataComponents.MODE, value)
            }
        }
}

