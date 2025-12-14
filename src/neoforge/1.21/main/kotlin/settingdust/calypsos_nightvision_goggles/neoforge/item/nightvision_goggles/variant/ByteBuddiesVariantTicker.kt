package settingdust.calypsos_nightvision_goggles.neoforge.item.nightvision_goggles.variant

import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.EntitySelector
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.turtleboi.bytebuddies.entity.entities.ByteBuddyEntity
import settingdust.calypsos_nightvision_goggles.adapter.LoaderAdapter
import settingdust.calypsos_nightvision_goggles.adapter.MobEffectAdapter
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.ByteBuddiesVariant
import java.util.*

class ByteBuddiesVariantTicker : ByteBuddiesVariant.Ticker {
    val byteBuddiesExists by lazy { LoaderAdapter.isModLoaded("bytebuddies") }
    private val lastTickForEntity = mutableMapOf<UUID, Long>()

    override fun tick(stack: ItemStack, owner: LivingEntity) {
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
                    .and { !(it as LivingEntity).hasEffect(ByteBuddiesVariant.DigSpeedPredicate.type) }
            )

        for (entity in buddyEntities) {
            entity.addEffect(
                MobEffectInstance(MobEffectAdapter.Speed, 60 * 20, 1, false, false, false)
            )
        }
    }
}