package settingdust.calypsos_nightvision_goggles.v1_20.adapter

import net.minecraft.resources.ResourceLocation
import settingdust.calypsos_nightvision_goggles.adapter.MinecraftAdapter

class MinecraftAdapter : MinecraftAdapter {
    override fun id(namespace: String, path: String) = ResourceLocation(namespace, path)
}