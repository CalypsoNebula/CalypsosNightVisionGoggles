package settingdust.calypsos_nightvision_goggles.fabric.v1_21

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import settingdust.calypsos_nightvision_goggles.adapter.Entrypoint
import settingdust.calypsos_nightvision_goggles.v1_21.CalypsosNightVisionGogglesCassettes
import settingdust.calypsos_nightvision_goggles.v1_21.CalypsosNightVisionGogglesDataComponents

class Entrypoint : Entrypoint {
    override fun init() {
        CalypsosNightVisionGogglesDataComponents.registerDataComponents { id, type ->
            Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, id, type)
        }
        // 注册自定义磁带物品
        CalypsosNightVisionGogglesCassettes.registerCassettes { id, item ->
            Registry.register(BuiltInRegistries.ITEM, id, item)
        }
    }
}