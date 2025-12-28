package settingdust.calypsos_nightvision_goggles.fabric.v1_20.item.nightvision_goggles

import dev.emi.trinkets.CreativeTrinketSlot
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.FabricPacket
import net.fabricmc.fabric.api.networking.v1.PacketType
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.inventory.Slot
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.adapter.LoaderAdapter
import settingdust.calypsos_nightvision_goggles.fabric.v1_20.util.TrinketsContainerType
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesNetworking
import settingdust.calypsos_nightvision_goggles.util.ContainerType

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
        val isCreativeTrinket = slot.javaClass.name == "dev.emi.trinkets.CreativeTrinketSlot"
        val type = if (isCreativeTrinket) {
            TrinketsContainerType.TRINKET_TYPE
        } else {
            ContainerType.NORMAL
        }
        val data = if (isCreativeTrinket) {
            TrinketsContainerType.Trinket((slot as CreativeTrinketSlot).type.group, slot.type.name)
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
) : FabricPacket {
    companion object {
        val TYPE = PacketType.create(CalypsosNightVisionGoggles.id("switch_mode")) { buf ->
            C2SSwitchModePacket(buf.readVarInt(), buf.readUtf(), buf)
        }

        init {
            ServerPlayNetworking.registerGlobalReceiver(TYPE) { packet, player, _ ->
                NightvisionGogglesNetworking.handleSwitchMode(
                    packet.slotIndex,
                    ContainerType.ALL.getValue(packet.containerType),
                    player,
                    packet.data
                )
            }
        }
    }

    constructor(slotIndex: Int, containerType: String, buf: FriendlyByteBuf) : this(
        slotIndex,
        containerType,
        ContainerType.ALL[containerType]!!.dataSerializer(buf)
    )

    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(slotIndex)
        buf.writeUtf(containerType)
        data.write(buf)
    }

    override fun getType() = TYPE
}