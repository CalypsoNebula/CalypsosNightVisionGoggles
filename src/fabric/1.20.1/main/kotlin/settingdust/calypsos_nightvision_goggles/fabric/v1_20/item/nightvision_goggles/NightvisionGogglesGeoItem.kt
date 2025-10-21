package settingdust.calypsos_nightvision_goggles.fabric.v1_20.item.nightvision_goggles

import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.render.NightvisionGogglesRenderer
import settingdust.calypsos_nightvision_goggles.v1_20.item.nightvision_goggles.NightvisionGogglesGeoItem
import software.bernie.geckolib.animatable.GeoItem
import software.bernie.geckolib.animatable.client.RenderProvider
import java.util.function.Consumer
import java.util.function.Supplier

class NightvisionGogglesGeoItem : NightvisionGogglesGeoItem() {
    private val renderProvider: Supplier<Any> = GeoItem.makeRenderer(this)

    override fun createRenderer(consumer: Consumer<in Any>) {
        consumer.accept(object : RenderProvider {
            override fun getCustomRenderer() = NightvisionGogglesRenderer
        })
    }

    override fun getRenderProvider() = renderProvider
}