package settingdust.calypsos_nightvision_goggles.forge

import net.minecraftforge.network.NetworkRegistry
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.forge.item.nightvision_goggles.C2SSwitchModePacket

@Suppress("INACCESSIBLE_TYPE")
object CalypsosNightVisionGogglesNetworking {
    const val PROTOCOL_VERSION = "1"
    val CHANNEL = NetworkRegistry.newSimpleChannel(
        CalypsosNightVisionGoggles.id("main"),
        { PROTOCOL_VERSION },
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    )
    private var index = 0

    init {
        CHANNEL.registerMessage(
            index++,
            C2SSwitchModePacket::class.java,
            { packet, buf -> packet.encode(buf) },
            { C2SSwitchModePacket.decode(it) },
            { packet, context -> C2SSwitchModePacket.handle(packet, context) })
    }
}