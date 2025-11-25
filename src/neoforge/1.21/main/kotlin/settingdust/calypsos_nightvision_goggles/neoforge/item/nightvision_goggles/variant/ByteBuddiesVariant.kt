package settingdust.calypsos_nightvision_goggles.neoforge.item.nightvision_goggles.variant

import net.minecraft.core.HolderSet
import net.minecraft.network.chat.Component
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EntitySelector
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.event.level.BlockEvent
import net.turtleboi.bytebuddies.entity.entities.ByteBuddyEntity
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeys
import settingdust.calypsos_nightvision_goggles.adapter.AccessoryIntegration
import settingdust.calypsos_nightvision_goggles.adapter.LoaderAdapter
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesModeHandler.Companion.mode
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesVariant
import settingdust.calypsos_nightvision_goggles.neoforge.NeoForgeCalypsosNightVisionGogglesItems
import settingdust.calypsos_nightvision_goggles.util.MobEffectPredicate
import software.bernie.geckolib.model.DefaultedItemGeoModel
import thedarkcolour.kotlinforforge.neoforge.forge.FORGE_BUS
import java.util.*

object ByteBuddiesVariant : NightvisionGogglesVariant {
    val byteBuddiesExists by lazy { LoaderAdapter.isModLoaded("bytebuddies") }
    val DigSpeedPredicate = MobEffectPredicate(
        MobEffects.DIG_SPEED,
        duration = 60 * 20,
        amplifier = 0,
        ambient = false,
        visible = true,
        showIcon = true
    )

    override val model =
        DefaultedItemGeoModel<NightvisionGogglesItem>(CalypsosNightVisionGogglesKeys.ByteBuddiesGoggles)

    override val description = listOf(
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.byte_buddies_goggles.tooltip.description.0"),
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.byte_buddies_goggles.tooltip.description.1"),
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.byte_buddies_goggles.tooltip.description.2")
    )

    private val lastTickForEntity = mutableMapOf<UUID, Long>()

    init {
        FORGE_BUS.addListener { event: BlockEvent.BreakEvent ->
            val owner = event.player
            val stack = AccessoryIntegration.getEquipped(
                owner,
                HolderSet.direct(NeoForgeCalypsosNightVisionGogglesItems.ByteBuddiesGoggles)
            ) ?: return@addListener
            val mode = stack.mode!!
            if (stack.damageValue >= stack.maxDamage - 1 || !mode.isEnabled(stack, owner)) {
                return@addListener
            }
            if (!owner.hasEffect(DigSpeedPredicate.type)) {
                owner.addEffect(
                    MobEffectInstance(
                        DigSpeedPredicate.type,
                        DigSpeedPredicate.duration!!,
                        DigSpeedPredicate.amplifier!!,
                        DigSpeedPredicate.ambient!!,
                        false,
                        DigSpeedPredicate.showIcon!!
                    )
                )
            }
        }
    }

    override fun tick(stack: ItemStack, owner: LivingEntity) {
        super.tick(stack, owner)
        if (!byteBuddiesExists) return
        val lastTick = lastTickForEntity[owner.uuid]
        if (lastTick != null && owner.level().gameTime - lastTick < 20 * 3) return
        lastTickForEntity[owner.uuid] = owner.level().gameTime

        val buddyEntities =
            owner.level().getEntitiesOfClass(
                ByteBuddyEntity::class.java,
                owner.boundingBox.inflate(30.0),
                EntitySelector.ENTITY_STILL_ALIVE
                    .and(EntitySelector.NO_SPECTATORS)
                    .and { !(it as LivingEntity).hasEffect(DigSpeedPredicate.type) }
            )

        for (entity in buddyEntities) {
            entity.addEffect(
                MobEffectInstance(
                    DigSpeedPredicate.type,
                    DigSpeedPredicate.duration!!,
                    DigSpeedPredicate.amplifier!!,
                    DigSpeedPredicate.ambient!!,
                    DigSpeedPredicate.visible!!,
                    DigSpeedPredicate.showIcon!!
                )
            )
        }
    }
}