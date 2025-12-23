package settingdust.calypsos_nightvision_goggles.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Chicken;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import settingdust.calypsos_nightvision_goggles.goal.CuteChickenTemptGoalFactory;

@Mixin(Chicken.class)
public class ChickenMixin {
    @ModifyExpressionValue(
        method = "registerGoals",
        at = @At(
            value = "NEW",
            target = "(Lnet/minecraft/world/entity/PathfinderMob;DLnet/minecraft/world/item/crafting/Ingredient;Z)Lnet/minecraft/world/entity/ai/goal/TemptGoal;"
        )
    )
    private TemptGoal calypsos_nightvision_goggles$registerGoals$replaceTemptGoal(TemptGoal original) {
        try {
            Chicken chicken = (Chicken) (Object) this;
            return CuteChickenTemptGoalFactory.Companion.create(chicken, original);
        } catch (Exception e) {
            e.printStackTrace();
            return original;
        }
    }
}
