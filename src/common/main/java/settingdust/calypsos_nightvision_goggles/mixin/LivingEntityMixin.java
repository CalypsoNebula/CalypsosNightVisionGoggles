package settingdust.calypsos_nightvision_goggles.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import settingdust.calypsos_nightvision_goggles.util.event.LivingHurtEvents;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "hurt", at = @At("HEAD"))
    private void calypsos_nightvision_goggles$tick$invokeHurtEvent(
        final DamageSource source,
        final float amount,
        final CallbackInfoReturnable<Boolean> cir,
        @Local(argsOnly = true) LocalFloatRef amountRef
    ) {
        amountRef.set(
            LivingHurtEvents.MODIFY_DAMAGE
                .getInvoker()
                .onLivingHurt((LivingEntity) (Object) this, source, amount, amount));
    }

    @ModifyExpressionValue(
        method = "causeFallDamage",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;calculateFallDamage(FF)I"
        )
    )
    private int calypsos_nightvision_goggles$causeFallDamage$modifyFallDamage(
        int originalDamage,
        @Local(argsOnly = true) DamageSource source
    ) {
        float modifiedDamage = LivingHurtEvents.MODIFY_FALL_DAMAGE
            .getInvoker()
            .onFallDamage((LivingEntity) (Object) this, source, (float) originalDamage);
        return (int) modifiedDamage;
    }
}
