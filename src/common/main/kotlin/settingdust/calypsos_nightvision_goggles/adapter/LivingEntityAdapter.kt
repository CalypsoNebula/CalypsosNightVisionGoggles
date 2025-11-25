package settingdust.calypsos_nightvision_goggles.adapter

import net.minecraft.core.Holder
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity
import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil

interface LivingEntityAdapter {
    companion object : LivingEntityAdapter by ServiceLoaderUtil.findService()

    fun LivingEntity.removeEffect(effect: Holder<MobEffect>): Boolean

    fun LivingEntity.hasEffect(effect: Holder<MobEffect>): Boolean

    fun LivingEntity.getEffect(effect: Holder<MobEffect>): MobEffectInstance?
}