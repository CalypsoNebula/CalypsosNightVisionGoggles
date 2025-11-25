package settingdust.calypsos_nightvision_goggles.forge

import net.minecraft.client.KeyMapping
import net.minecraft.core.registries.Registries
import net.minecraftforge.client.event.RegisterKeyMappingsEvent
import net.minecraftforge.client.settings.KeyConflictContext
import net.minecraftforge.event.level.BlockEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.registries.RegisterEvent
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesItems
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeyBindings
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesMobEffects
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesSoundEvents
import settingdust.calypsos_nightvision_goggles.adapter.Entrypoint
import settingdust.calypsos_nightvision_goggles.util.event.PlayerBlockBreakCallback
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(CalypsosNightVisionGoggles.ID)
object CalypsosNightVisionGogglesForge {
    init {
        requireNotNull(CalypsosNightVisionGoggles)
        Entrypoint.construct()
        MOD_BUS.apply {
            addListener<FMLCommonSetupEvent> {
                Entrypoint.init()
                requireNotNull(CalypsosNightVisionGogglesNetworking)
            }
            addListener<FMLClientSetupEvent> {
                addListener<RegisterKeyMappingsEvent> { event ->
                    CalypsosNightVisionGogglesKeyBindings.registerKeyBindings { event.register(it) }
                    (CalypsosNightVisionGogglesKeyBindings.ACCESSORY_MODE as KeyMapping).keyConflictContext =
                        KeyConflictContext.GUI
                }
                Entrypoint.clientInit()
            }
            addListener<RegisterEvent> { event ->
                when (event.registryKey) {
                    Registries.ITEM -> CalypsosNightVisionGogglesItems.registerItems { id, value ->
                        event.register(Registries.ITEM, id) { value }
                    }

                    Registries.SOUND_EVENT -> CalypsosNightVisionGogglesSoundEvents.registerSoundEvents { id, value ->
                        event.register(Registries.SOUND_EVENT, id) { value(id) }
                    }

                    Registries.MOB_EFFECT -> CalypsosNightVisionGogglesMobEffects.registerMobEffects { id, value ->
                        event.register(Registries.MOB_EFFECT, id) { value }
                    }
                }
            }
        }

        FORGE_BUS.apply {
            addListener<BlockEvent.BreakEvent> {
                PlayerBlockBreakCallback.CALLBACK.invoker.onBreak(it.level, it.player, it.pos, it.state)
            }
        }
    }
}