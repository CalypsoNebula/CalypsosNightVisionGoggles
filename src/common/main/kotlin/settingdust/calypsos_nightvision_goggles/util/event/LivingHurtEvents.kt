package settingdust.calypsos_nightvision_goggles.util.event

import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.LivingEntity

object LivingHurtEvents {
    @JvmField
    val MODIFY_DAMAGE = Event<ModifyDamage> { listeners ->
        ModifyDamage { entity, source, originalAmount, amount ->
            var result = amount
            for (listener in listeners) {
                result = listener.onLivingHurt(entity, source, originalAmount, result)
            }
            result
        }
    }

    fun interface ModifyDamage {
        fun onLivingHurt(entity: LivingEntity, source: DamageSource, originalAmount: Float, amount: Float): Float
    }
}