package settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant

import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.ProjectileUtil
import net.minecraft.world.item.ItemStack
import net.minecraft.world.phys.EntityHitResult
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeys
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesMobEffects
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesSoundEvents
import settingdust.calypsos_nightvision_goggles.adapter.LivingEntityAdapter.Companion.hasEffect
import settingdust.calypsos_nightvision_goggles.adapter.MobEffectAdapter
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesModeHandler
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesModeHandler.Companion.mode
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesVariant
import settingdust.calypsos_nightvision_goggles.util.MobEffectPredicate
import software.bernie.geckolib.model.DefaultedItemGeoModel
import java.util.*


object WatcherVariant : NightvisionGogglesVariant {
    val ItsWatchingPredicate = MobEffectPredicate(
        CalypsosNightVisionGogglesMobEffects.ItsWatching,
        duration = 10 * 20,
        amplifier = 0,
        ambient = false,
        visible = false,
        showIcon = true
    )

    override val description = listOf(
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.the_watcher_goggles.tooltip.description")
    )

    override val model = DefaultedItemGeoModel<NightvisionGogglesItem>(CalypsosNightVisionGogglesKeys.TheWatcherGoggles)

    private val lookingEntityForEntity = mutableMapOf<UUID, UUID>()
    private val lookingTicksForEntity = mutableMapOf<UUID, Int>()

    override fun tick(stack: ItemStack, owner: LivingEntity) {
        super.tick(stack, owner)
        val mode = stack.mode!!
        if (stack.damageValue >= stack.maxDamage - 1
            || (mode != NightvisionGogglesModeHandler.Mode.AUTO && !mode.isEnabled(stack, owner))
        ) {
            return
        }
        val target = getLookedAtLivingEntity(owner, 16.0) ?: return
        val lookingEntity = lookingEntityForEntity[owner.uuid]
        if (lookingEntity != target.uuid) {
            lookingEntityForEntity[owner.uuid] = target.uuid
            lookingTicksForEntity[owner.uuid] = 0
        }
        val lookingTicks = lookingTicksForEntity[owner.uuid] ?: return
        if (lookingTicks < 20) {
            lookingTicksForEntity[owner.uuid] = lookingTicks + 1
            return
        }
        if (!target.hasEffect(ItsWatchingPredicate.type) && target.addEffect(
                MobEffectAdapter.createMobEffectInstance(
                    ItsWatchingPredicate.type,
                    ItsWatchingPredicate.duration!!,
                    ItsWatchingPredicate.amplifier!!,
                    ItsWatchingPredicate.ambient!!,
                    ItsWatchingPredicate.visible!!,
                    ItsWatchingPredicate.showIcon!!
                )
            )
        ) {
            lookingEntityForEntity.remove(owner.uuid)
            lookingTicksForEntity[owner.uuid] = 0
            target.playSound(CalypsosNightVisionGogglesSoundEvents.AccessoryWatching)
        }
    }

    fun getLookedAtLivingEntity(from: LivingEntity, range: Double): LivingEntity? {
        val eyePos = from.eyePosition
        val lookVec = from.lookAngle
        val end = eyePos.add(lookVec.scale(range))

        val box = from.boundingBox.expandTowards(lookVec.scale(range)).inflate(1.0)

        val hitResult: EntityHitResult? = ProjectileUtil.getEntityHitResult(
            from.level(),
            from,
            eyePos,
            end,
            box
        ) { entity ->
            entity is LivingEntity && !entity.isSpectator && entity.isPickable && entity != from
        }

        return hitResult?.entity as? LivingEntity
    }
}