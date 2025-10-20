package settingdust.calypsos_nightvision_goggles.fabric.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import settingdust.calypsos_nightvision_goggles.fabric.util.LivingTickCallback;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void calypsos_nightvision_goggles$tick(CallbackInfo ci) {
        LivingTickCallback.EVENT.invoker().onLivingTick((LivingEntity) (Object) this);
    }
}
