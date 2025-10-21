package settingdust.calypsos_nightvision_goggles.v1_20.item.nightvision_goggles

import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.render.NightvisionGogglesGeoItem
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.core.animation.AnimatableManager
import software.bernie.geckolib.util.GeckoLibUtil

open class NightvisionGogglesGeoItem : NightvisionGogglesGeoItem {
    private val animatableCache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    override fun registerControllers(registrar: AnimatableManager.ControllerRegistrar) {
    }

    override fun getAnimatableInstanceCache() = animatableCache
}