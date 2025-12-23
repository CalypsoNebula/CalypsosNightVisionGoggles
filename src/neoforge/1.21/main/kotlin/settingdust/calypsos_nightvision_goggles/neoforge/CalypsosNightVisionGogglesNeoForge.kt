package settingdust.calypsos_nightvision_goggles.neoforge

import dev.nyon.klf.MOD_BUS
import net.minecraft.client.KeyMapping
import net.minecraft.core.registries.Registries
import net.minecraft.server.level.ServerPlayer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent
import net.neoforged.neoforge.client.settings.KeyConflictContext
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.level.BlockEvent
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent
import net.neoforged.neoforge.registries.RegisterEvent
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesItems
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeyBindings
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesMobEffects
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesSoundEvents
import settingdust.calypsos_nightvision_goggles.adapter.Entrypoint
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesNetworking
import settingdust.calypsos_nightvision_goggles.neoforge.item.nightvision_goggles.C2SSwitchModePacket
import settingdust.calypsos_nightvision_goggles.util.ContainerType
import settingdust.calypsos_nightvision_goggles.util.event.PlayerBlockBreakCallback
import settingdust.calypsos_nightvision_goggles.v1_21.CalypsosNightVisionGogglesDataComponents

@Mod(CalypsosNightVisionGoggles.ID)
object CalypsosNightVisionGogglesNeoForge {
    init {
        requireNotNull(CalypsosNightVisionGoggles)
        Entrypoint.construct()
        MOD_BUS.apply {
            addListener<FMLCommonSetupEvent> {
                Entrypoint.init()
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
                    Registries.ITEM -> {
                        CalypsosNightVisionGogglesItems.registerItems { id, value ->
                            event.register(Registries.ITEM, id) { value }
                        }
                    }

                    Registries.SOUND_EVENT -> CalypsosNightVisionGogglesSoundEvents.registerSoundEvents { id, value ->
                        event.register(Registries.SOUND_EVENT, id) { value(id) }
                    }

                    Registries.DATA_COMPONENT_TYPE -> CalypsosNightVisionGogglesDataComponents.registerDataComponents { id, value ->
                        event.register(Registries.DATA_COMPONENT_TYPE, id) { value }
                    }

                    Registries.MOB_EFFECT -> CalypsosNightVisionGogglesMobEffects.registerMobEffects { id, value ->
                        event.register(Registries.MOB_EFFECT, id) { value }
                    }
                }
            }
            addListener<RegisterPayloadHandlersEvent> { event ->
                val registrar = event.registrar("1")
                registrar.playToServer(C2SSwitchModePacket.TYPE, C2SSwitchModePacket.STREAM_CODEC) { packet, context ->
                    NightvisionGogglesNetworking.handleSwitchMode(
                        packet.slotIndex,
                        ContainerType.ALL.getValue(packet.containerType),
                        context.player() as ServerPlayer,
                        packet.data
                    )
                }
            }
        }

        NeoForge.EVENT_BUS.apply {
            addListener<BlockEvent.BreakEvent> {
                PlayerBlockBreakCallback.CALLBACK.invoker.onBreak(it.level, it.player, it.pos, it.state)
            }
        }
    }
}