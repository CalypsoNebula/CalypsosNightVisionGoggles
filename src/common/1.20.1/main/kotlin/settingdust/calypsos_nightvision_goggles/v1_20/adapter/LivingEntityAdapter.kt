package settingdust.calypsos_nightvision_goggles.v1_20.adapter

import net.minecraft.core.Holder
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.entity.LivingEntity
import settingdust.calypsos_nightvision_goggles.adapter.LivingEntityAdapter

class LivingEntityAdapter : LivingEntityAdapter {
    override fun LivingEntity.removeEffect(effect: Holder<MobEffect>) =
        removeEffect(effect.value())

    override fun LivingEntity.hasEffect(effect: Holder<MobEffect>) =
        hasEffect(effect.value())
}