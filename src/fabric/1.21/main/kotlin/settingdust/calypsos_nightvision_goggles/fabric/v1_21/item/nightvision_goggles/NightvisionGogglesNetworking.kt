package settingdust.calypsos_nightvision_goggles.fabric.v1_21.item.nightvision_goggles

import dev.emi.trinkets.CreativeTrinketSlot
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.world.inventory.Slot
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.adapter.LoaderAdapter
import settingdust.calypsos_nightvision_goggles.fabric.v1_21.util.TrinketsContainerType
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesNetworking
import settingdust.calypsos_nightvision_goggles.util.ContainerType
import settingdust.calypsos_nightvision_goggles.v1_21.util.CalypsosNightVisionGogglesItemsStreamCodecs

class NightvisionGogglesNetworking : NightvisionGogglesNetworking {
    override fun c2sSwitchMode(slot: Slot, isCreativeSlot: Boolean) {
        require(LoaderAdapter.isClient)
        requireNotNull(TrinketsContainerType)
        val index =
            if (isCreativeSlot) {
                slot.containerSlot
            } else {
                slot.index
            }
        val type = if (slot is CreativeTrinketSlot) {
            TrinketsContainerType.TRINKET_TYPE
        } else {
            ContainerType.NORMAL
        }
        val data = if (slot is CreativeTrinketSlot) {
            TrinketsContainerType.Trinket(slot.type.group, slot.type.name)
        } else {
            ContainerType.Data.Normal
        }
        ClientPlayNetworking.send(C2SSwitchModePacket(index, type, data))
    }
}

data class C2SSwitchModePacket(
    val slotIndex: Int,
    val containerType: String,
    val data: ContainerType.Data
) : CustomPacketPayload {
    companion object {
        val TYPE = CustomPacketPayload.Type<C2SSwitchModePacket>(CalypsosNightVisionGoggles.id("switch_mode"))
        val STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            C2SSwitchModePacket::slotIndex,
            ByteBufCodecs.STRING_UTF8,
            C2SSwitchModePacket::containerType,
            CalypsosNightVisionGogglesItemsStreamCodecs.CONTAINER_TYPE_DATA,
            C2SSwitchModePacket::data,
            ::C2SSwitchModePacket
        )

        init {
            PayloadTypeRegistry.playC2S().register(TYPE, STREAM_CODEC)
            ServerPlayNetworking.registerGlobalReceiver(TYPE) { packet, context ->
                NightvisionGogglesNetworking.handleSwitchMode(
                    packet.slotIndex,
                    ContainerType.ALL.getValue(packet.containerType),
                    context.player(),
                    packet.data
                )
            }
        }
    }

    override fun type() = TYPE
}