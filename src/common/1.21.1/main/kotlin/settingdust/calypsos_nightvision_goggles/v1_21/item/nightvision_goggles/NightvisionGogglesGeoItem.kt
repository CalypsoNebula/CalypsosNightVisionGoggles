package settingdust.calypsos_nightvision_goggles.v1_21.item.nightvision_goggles

import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.render.NightvisionGogglesGeoItem
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.render.NightvisionGogglesRenderer
import software.bernie.geckolib.animatable.client.GeoRenderProvider
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager
import software.bernie.geckolib.util.GeckoLibUtil
import java.util.function.Consumer

open class NightvisionGogglesGeoItem : NightvisionGogglesGeoItem {
    private val animatableCache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    override fun registerControllers(registrar: AnimatableManager.ControllerRegistrar) {
    }

    override fun getAnimatableInstanceCache() = animatableCache

    override fun createGeoRenderer(consumer: Consumer<GeoRenderProvider>) {
        consumer.accept(object : GeoRenderProvider {
            override fun getGeoItemRenderer() = NightvisionGogglesRenderer
        })
    }
}