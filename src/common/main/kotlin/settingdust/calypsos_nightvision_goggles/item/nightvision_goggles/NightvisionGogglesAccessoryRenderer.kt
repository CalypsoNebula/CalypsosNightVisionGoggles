package settingdust.calypsos_nightvision_goggles.item.nightvision_goggles

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.Minecraft
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.LivingEntityRenderer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import settingdust.calypsos_nightvision_goggles.util.AccessoryRenderer
import settingdust.calypsos_nightvision_goggles.util.AccessoryRenderer.Companion.transformToModelPart

object NightvisionGogglesAccessoryRenderer : AccessoryRenderer {
    override fun render(
        stack: ItemStack,
        owner: LivingEntity,
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        light: Int
    ) {
        val entityRenderer =
            Minecraft.getInstance().entityRenderDispatcher.getRenderer(owner) as? LivingEntityRenderer<*, *>
                ?: return
        val entityModel = entityRenderer.model as? HumanoidModel<*> ?: return
        poseStack.pushPose()
        poseStack.transformToModelPart(entityModel.head)
        poseStack.mulPose(Axis.ZP.rotationDegrees(180f))
        poseStack.scale(0.625f, 0.625f, 0.625f)
        Minecraft.getInstance().itemRenderer.renderStatic(
            owner,
            stack,
            ItemDisplayContext.HEAD,
            false,
            poseStack,
            buffer,
            owner.level(),
            light,
            OverlayTexture.NO_OVERLAY,
            0
        )
        poseStack.popPose()
    }
}