package settingdust.calypsos_nightvision_goggles.item.nightvision_goggles

import net.minecraft.network.chat.Component
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import settingdust.calypsos_nightvision_goggles.adapter.ItemStackAdapter.Companion.hurtNoBreak
import settingdust.calypsos_nightvision_goggles.adapter.MobEffectAdapter
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesModeHandler.Companion.mode
import settingdust.calypsos_nightvision_goggles.util.MobEffectPredicate
import software.bernie.geckolib.model.GeoModel

interface NightvisionGogglesVariant {
    companion object {
        val NightvisionPredicate = MobEffectPredicate(
            MobEffectAdapter.Nightvision,
            duration = 2 * 20,
            amplifier = 0,
            ambient = false,
            visible = false,
            showIcon = true
        )
    }

    val model: GeoModel<NightvisionGogglesItem>

    val description: List<Component>
        get() = emptyList()

    fun tick(stack: ItemStack, owner: LivingEntity)  {
        if (stack.mode == null) stack.mode = NightvisionGogglesModeHandler.Mode.AUTO
        if (stack.damageValue >= stack.maxDamage - 1 || !stack.mode!!.isEnabled(stack, owner)) {
            return
        }
        owner.addEffect(
            MobEffectAdapter.createMobEffectInstance(
                NightvisionPredicate.type,
                NightvisionPredicate.duration!!,
                NightvisionPredicate.amplifier!!,
                NightvisionPredicate.ambient!!,
                NightvisionPredicate.visible!!,
                NightvisionPredicate.showIcon!!
            )
        )
        stack.hurtNoBreak(owner, 1)
    }

    fun MobEffectInstance?.isFromAccessory(): Boolean = NightvisionPredicate.test(this)
}