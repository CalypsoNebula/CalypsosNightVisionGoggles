package settingdust.calypsos_nightvision_goggles.v1_20.adapter

import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import settingdust.calypsos_nightvision_goggles.adapter.MobEffectAdapter

class MobEffectAdapter : MobEffectAdapter {
    override val Nightvision = BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(
        ResourceKey.create(
            Registries.MOB_EFFECT,
            ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, "night_vision")
        )
    )

    override val Weakness = BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(
        ResourceKey.create(
            Registries.MOB_EFFECT,
            ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, "weakness")
        )
    )

    override val Slowness = BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(
        ResourceKey.create(
            Registries.MOB_EFFECT,
            ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, "slowness")
        )
    )

    override val Speed = BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(
        ResourceKey.create(
            Registries.MOB_EFFECT,
            ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, "speed")
        )
    )

    override val JumpBoost = BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(
        ResourceKey.create(
            Registries.MOB_EFFECT,
            ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, "jump_boost")
        )
    )

    override val Glowing = BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(
        ResourceKey.create(
            Registries.MOB_EFFECT,
            ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, "glowing")
        )
    )

    override val DigSpeed: Holder<MobEffect> = BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(
        ResourceKey.create(
            Registries.MOB_EFFECT,
            ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, "haste")
        )
    )

    override val MobEffectInstance.effectHolder: Holder<MobEffect>
        get() = BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(
            BuiltInRegistries.MOB_EFFECT.getResourceKey(effect).orElseThrow()
        )
    override val MobEffectInstance.effectReference: MobEffect
        get() = effect

    override fun createMobEffectInstance(
        effect: Holder<MobEffect>,
        duration: Int,
        amplifier: Int,
        ambient: Boolean,
        visible: Boolean,
        showIcon: Boolean
    ) = MobEffectInstance(effect.value(), duration, amplifier, ambient, visible, showIcon)
}