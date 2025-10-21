package settingdust.calypsos_nightvision_goggles.forge.item.nightvision_goggles

import net.minecraftforge.client.extensions.common.IClientItemExtensions
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesVariant
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.render.NightvisionGogglesRenderer
import settingdust.calypsos_nightvision_goggles.v1_20.item.nightvision_goggles.NightvisionGogglesItem
import java.util.function.Consumer

class NightvisionGogglesItem(variant: NightvisionGogglesVariant) : NightvisionGogglesItem(variant) {
    class Factory : settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem.Factory {
        override fun invoke(variant: NightvisionGogglesVariant) =
            settingdust.calypsos_nightvision_goggles.forge.item.nightvision_goggles.NightvisionGogglesItem(variant)
    }

    override fun initializeClient(consumer: Consumer<IClientItemExtensions>) {
        consumer.accept(object : IClientItemExtensions {
            override fun getCustomRenderer() = NightvisionGogglesRenderer
        })
    }
}