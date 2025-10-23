package settingdust.calypsos_nightvision_goggles

import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.KeyMapping
import org.lwjgl.glfw.GLFW

object CalypsosNightVisionGogglesKeyBindings {
    val ACCESSORY_MODE = KeyMapping(
        "key.${CalypsosNightVisionGoggles.ID}.accessory_mode",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_D,
        "key.categories.${CalypsosNightVisionGoggles.ID}"
    )

    fun registerKeyBindings(register: (KeyMapping) -> Unit) {
        register(ACCESSORY_MODE)
    }
}