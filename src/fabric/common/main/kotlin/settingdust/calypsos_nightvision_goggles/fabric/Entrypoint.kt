package settingdust.calypsos_nightvision_goggles.fabric

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesItems
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeyBindings
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesMobEffects
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesSoundEvents
import settingdust.calypsos_nightvision_goggles.adapter.Entrypoint
import settingdust.calypsos_nightvision_goggles.util.event.PlayerBlockBreakCallback

object Entrypoint {
    init {
        requireNotNull(CalypsosNightVisionGoggles)
        Entrypoint.construct()
    }

    fun init() {
        CalypsosNightVisionGogglesMobEffects.registerMobEffects { id, effect ->
            Registry.register(BuiltInRegistries.MOB_EFFECT, id, effect)
        }
        CalypsosNightVisionGogglesItems.registerItems { id, item ->
            Registry.register(BuiltInRegistries.ITEM, id, item)
        }
        CalypsosNightVisionGogglesSoundEvents.registerSoundEvents { id, factory ->
            Registry.register(BuiltInRegistries.SOUND_EVENT, id, factory(id))
        }
        PlayerBlockBreakEvents.AFTER.register { level, player, pos, state, _ ->
            PlayerBlockBreakCallback.CALLBACK.invoker.onBreak(level, player, pos, state)
        }
        Entrypoint.init()
    }

    fun clientInit() {
        CalypsosNightVisionGogglesKeyBindings.registerKeyBindings {
            KeyBindingHelper.registerKeyBinding(it)
        }
        Entrypoint.clientInit()
    }
}
