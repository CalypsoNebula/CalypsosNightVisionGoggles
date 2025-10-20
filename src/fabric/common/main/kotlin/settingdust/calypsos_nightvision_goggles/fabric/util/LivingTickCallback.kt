package settingdust.calypsos_nightvision_goggles.fabric.util

import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.world.entity.LivingEntity

object LivingTickCallback {
    @JvmField
    val EVENT =
        EventFactory.createArrayBacked(Callback::class.java) { listeners ->
            Callback {
                for (callback in listeners) {
                    callback.onLivingTick(it)
                }
            }
        }

    fun interface Callback {
        fun onLivingTick(entity: LivingEntity)
    }
}