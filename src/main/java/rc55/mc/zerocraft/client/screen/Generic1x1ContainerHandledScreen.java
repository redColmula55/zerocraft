package rc55.mc.zerocraft.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.screen.Generic1x1ContainerScreenHandler;

public class Generic1x1ContainerHandledScreen extends HandledScreen<Generic1x1ContainerScreenHandler> {
    //材质
    private static final Identifier TEXTURE = new Identifier(ZeroCraft.MODID, "textures/gui/container/generic_1x1.png");

    public Generic1x1ContainerHandledScreen(Generic1x1ContainerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    //屏幕渲染
    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx);
        super.render(ctx, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(ctx, mouseX, mouseY);
    }
    @Override
    protected void drawBackground(DrawContext ctx, float delta, int mouseX, int mouseY) {
        ctx.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }
}
