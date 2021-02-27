package net.drago.ofcapes.mixin;

import java.math.BigInteger;
import java.util.Random;

import com.mojang.authlib.exceptions.AuthenticationException;

import org.spongepowered.asm.mixin.Mixin;

import net.drago.ofcapes.ofcapes;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.SkinOptionsScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;

@Mixin(SkinOptionsScreen.class)
public abstract class SkinOptionsScreenMixin extends GameOptionsScreen {

    protected SkinOptionsScreenMixin(Screen parent, GameOptions gameOptions) {
        super(parent, gameOptions, new TranslatableText("options.skinCustomisation.title"));
    }

    private TranslatableText ownNameLabelOn = new TranslatableText("Your username: ON");
    private TranslatableText ownNameLabelOff = new TranslatableText("Your username: OFF");

    //@Inject(method = "init()V", at = @At("TAIL"))
    @Override
    public void init() {
        int integer2 = 0;
        for (final PlayerModelPart lv : PlayerModelPart.values()) {
            this.<CyclingButtonWidget<Boolean>>addButton(CyclingButtonWidget.method_32613(this.gameOptions.isPlayerModelPartEnabled(lv)).build(this.width / 2 - 155 + integer2 % 2 * 160, this.height / 6 + 24 * (integer2 >> 1), 150, 20, lv.getOptionName(), (cyclingButtonWidget, boolean4) -> this.gameOptions.togglePlayerModelPart(lv, boolean4)));
            ++integer2;
        }
        this.<AbstractButtonWidget>addButton(Option.MAIN_HAND.createButton(this.gameOptions, this.width / 2 - 155 + integer2 % 2 * 160, this.height / 6 + 24 * (integer2 >> 1), 150));
        if (++integer2 % 2 == 1) {
            ++integer2;
        }
        this.<AbstractButtonWidget>addButton(new ButtonWidget(this.width / 2 - 155 + integer2 % 2 * 160, this.height / 6 + 24 * (integer2 >> 1), 150, 20, ofcapes.showPlayersOwnName? ownNameLabelOn : ownNameLabelOff, (buttonWidget) -> {
            if(ofcapes.showPlayersOwnName) {
                ofcapes.showPlayersOwnName = false;
                ofcapes.saveConfig();
                buttonWidget.setMessage(ownNameLabelOff);
            } else {
                ofcapes.showPlayersOwnName = true;
                ofcapes.saveConfig();
                buttonWidget.setMessage(ownNameLabelOn);                
            }
        }));
        ++integer2;
        
        if (integer2 % 2 == 1) {
            ++integer2;
        }
        
        this.<AbstractButtonWidget>addButton(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 24 * (integer2 >> 1), 200, 20, new TranslatableText("Open Cape Editor..."), (button) -> {
            final Random r1 = new Random();
            final Random r2 = new Random(System.identityHashCode(new Object()));
            final BigInteger random1Bi = new BigInteger(128, r1);
            final BigInteger random2Bi = new BigInteger(128, r2);
            final BigInteger serverBi = random1Bi.xor(random2Bi);
            final String serverId = serverBi.toString(16);
            try {
                this.client.getSessionService().joinServer(this.client.getSession().getProfile(), this.client.getSession().getAccessToken(), serverId);
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }

            String uRL = String.format("https://optifine.net/capeChange?u=%s&n=%s&s=%s", this.client.getSession().getUuid(), this.client.getSession().getUsername(), serverId);

            Util.getOperatingSystem().open(String.format(uRL));
        }));
        ++integer2;

        if (integer2 % 2 == 1) {
            ++integer2;
        }

        this.<AbstractButtonWidget>addButton(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 24 * (integer2 >> 1), 200, 20, ScreenTexts.DONE, (buttonWidget) -> {
            this.client.openScreen(this.parent);
        }));
    }
    
}
