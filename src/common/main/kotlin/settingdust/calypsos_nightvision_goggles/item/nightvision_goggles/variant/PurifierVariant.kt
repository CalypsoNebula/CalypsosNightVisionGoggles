package settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant

import net.minecraft.network.chat.Component
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeys
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesSoundEvents
import settingdust.calypsos_nightvision_goggles.adapter.ItemStackAdapter.Companion.hurtNoBreak
import settingdust.calypsos_nightvision_goggles.adapter.LivingEntityAdapter.Companion.removeEffect
import settingdust.calypsos_nightvision_goggles.adapter.MobEffectAdapter.Companion.effectHolder
import settingdust.calypsos_nightvision_goggles.adapter.MobEffectAdapter.Companion.effectReference
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesModeHandler
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesModeHandler.Companion.mode
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesVariant
import software.bernie.geckolib.model.DefaultedItemGeoModel
import java.util.*

object PurifierVariant : NightvisionGogglesVariant {
    override val description = listOf(
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.purifier_goggles.tooltip.description")
    )

    override val model = DefaultedItemGeoModel<NightvisionGogglesItem>(CalypsosNightVisionGogglesKeys.PurifierGoggles)

    private val lastTickForEntity = mutableMapOf<UUID, Int>()

    override fun tick(stack: ItemStack, owner: LivingEntity) {
        super.tick(stack, owner)
        val mode = stack.mode!!
        if (stack.damageValue >= stack.maxDamage - 1
            || (mode != NightvisionGogglesModeHandler.Mode.AUTO && !mode.isEnabled(stack, owner))
        ) {
            return
        }
        val lastTick = lastTickForEntity[owner.uuid]
        if (lastTick != null && owner.tickCount - lastTick < 60) return
        lastTickForEntity[owner.uuid] = owner.tickCount
        var result = false
        for (instance in owner.activeEffects.filter { it.effectReference.category === MobEffectCategory.HARMFUL }) {
            if (owner.removeEffect(instance.effectHolder)) result = true
        }
        if (result) {
            stack.hurtNoBreak(owner, 90 * 20)
            owner.playSound(CalypsosNightVisionGogglesSoundEvents.AccessoryPurify)
        }
    }
}