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

    @JvmField
    val MODIFY_FALL_DAMAGE = Event<ModifyFallDamage> { listeners ->
        ModifyFallDamage { entity, source, damage ->
            var result = damage
            for (listener in listeners) {
                result = listener.onFallDamage(entity, source, result)
            }
            result
        }
    }

    fun interface ModifyDamage {
        fun onLivingHurt(entity: LivingEntity, source: DamageSource, originalAmount: Float, amount: Float): Float
    }

    fun interface ModifyFallDamage {
        fun onFallDamage(entity: LivingEntity, source: DamageSource, damage: Float): Float
    }
}