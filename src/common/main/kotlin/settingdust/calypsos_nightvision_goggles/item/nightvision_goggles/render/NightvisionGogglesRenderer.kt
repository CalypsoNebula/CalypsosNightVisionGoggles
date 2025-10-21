package settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.render

import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.RegularVariant
import software.bernie.geckolib.renderer.GeoItemRenderer

object NightvisionGogglesRenderer :
    GeoItemRenderer<NightvisionGogglesItem>(RegularVariant.model) {
    override fun getGeoModel() = animatable.variant.model
}