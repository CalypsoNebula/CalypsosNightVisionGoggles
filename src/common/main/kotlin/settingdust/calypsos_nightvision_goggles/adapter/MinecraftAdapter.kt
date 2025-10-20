package settingdust.calypsos_nightvision_goggles.adapter

import net.minecraft.resources.ResourceLocation
import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil

interface MinecraftAdapter {
    companion object : MinecraftAdapter by ServiceLoaderUtil.findService()

    fun id(namespace: String, path: String): ResourceLocation
}