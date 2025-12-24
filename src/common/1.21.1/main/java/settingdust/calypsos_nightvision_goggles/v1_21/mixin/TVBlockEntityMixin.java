package settingdust.calypsos_nightvision_goggles.v1_21.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.mehvahdjukaar.vista.common.CassetteItem;
import net.mehvahdjukaar.vista.common.TVBlockEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TVBlockEntity.class)
public class TVBlockEntityMixin {
    @WrapMethod(method = "canPlaceItem")
    private boolean calypsos_nightvision_goggles$allowCustomCassettes(
        final int index,
        final ItemStack stack,
        final Operation<Boolean> original
    ) {
        // 检查是否为我们的自定义磁带
        if (stack.getItem() instanceof CassetteItem) {
            return true;
        }
        return original.call(index, stack);
    }
}
