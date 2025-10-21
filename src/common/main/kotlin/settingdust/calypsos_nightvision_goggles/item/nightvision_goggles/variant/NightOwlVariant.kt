package settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant

import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesMobEffects
import settingdust.calypsos_nightvision_goggles.adapter.MobEffectAdapter
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesModeHandler.Companion.mode
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesVariant
import settingdust.calypsos_nightvision_goggles.util.MobEffectPredicate

object NightOwlVariant : NightvisionGogglesVariant {
    val ShadowHopperPredicate = MobEffectPredicate(
        CalypsosNightVisionGogglesMobEffects.ShadowHopper,
        duration = 5,
        amplifier = 0,
        ambient = false,
        visible = false,
        showIcon = true
    )

    override val description = listOf(
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.night_owl_goggles.tooltip.description")
    )

    override fun tick(stack: ItemStack, owner: LivingEntity) {
        super.tick(stack, owner)
        val mode = stack.mode!!
        if (stack.damageValue >= stack.maxDamage - 1 || !mode.isEnabled(stack, owner)) {
            return
        }
        owner.addEffect(
            MobEffectAdapter.createMobEffectInstance(
                ShadowHopperPredicate.type,
                ShadowHopperPredicate.duration!!,
                ShadowHopperPredicate.amplifier!!,
                ShadowHopperPredicate.ambient!!,
                ShadowHopperPredicate.visible!!,
                ShadowHopperPredicate.showIcon!!
            )
        )
    }
}