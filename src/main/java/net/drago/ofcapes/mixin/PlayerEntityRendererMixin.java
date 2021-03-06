package net.drago.ofcapes.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.drago.ofcapes.client.render.CapeRender;
import net.drago.ofcapes.client.render.ElytraRender;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin
        extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public PlayerEntityRendererMixin(Context context,
            PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = {"<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Z)V"}, at = @At("RETURN"))
    private void ConstructorMixinPlayerEntityRenderer(Context context, boolean bl, CallbackInfo info) {
        this.addFeature(new CapeRender(this));
        this.addFeature(new ElytraRender<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>(this, context.getModelLoader()));
        this.features.removeIf(renderer -> renderer instanceof ElytraFeatureRenderer);
    }
}