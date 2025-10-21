package settingdust.calypsos_nightvision_goggles.v1_21.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesItems;
import settingdust.calypsos_nightvision_goggles.adapter.AccessoryIntegration;
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesModeHandler;
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.RegularVariant;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @ModifyReturnValue(method = "getNightVisionScale", at = @At("RETURN"))
    private static float getNightVisionScale(float original, LivingEntity entity, float nanoTime) {
        var effect = entity.getEffect(MobEffects.NIGHT_VISION);
        var equipped = AccessoryIntegration.Companion.getEquipped(
            entity,
            CalypsosNightVisionGogglesItems.INSTANCE.getNightvisionGoggles()
        );
        if (equipped == null
            || !RegularVariant.INSTANCE.isFromAccessory(effect)
            || !NightvisionGogglesModeHandler.Companion.getMode(equipped).isEnabled().invoke(equipped, entity)) {
            return original;
        }
        return 1.0f;
    }
}
