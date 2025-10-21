package settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant

import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeys
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesVariant
import software.bernie.geckolib.model.DefaultedItemGeoModel

object RegularVariant : NightvisionGogglesVariant {
    override val model =
        DefaultedItemGeoModel<NightvisionGogglesItem>(CalypsosNightVisionGogglesKeys.NightvisionGoggles)
}