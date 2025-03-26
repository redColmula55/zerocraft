package rc55.mc.zerocraft.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.screen.FluidTankScreenHandler;

@Environment(EnvType.CLIENT)
public class FluidTankHandledScreen extends HandledScreen<FluidTankScreenHandler> {
    //材质
    private static final Identifier TEXTURE = new Identifier(ZeroCraft.MODID, "textures/gui/container/fluid_tank.png");

    public FluidTankHandledScreen(FluidTankScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);
    }

    /*@Override
    protected void init() {
        super.init();
        this.setup();
        this.handler.addListener((ScreenHandlerListener) this);
    }

    @Override
    public void removed() {
        super.removed();
        this.handler.removeListener((ScreenHandlerListener) this);
    }*/

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx);
        super.render(ctx, mouseX, mouseY, delta);
        this.renderForeground(ctx, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(ctx, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext ctx, float delta, int mouseX, int mouseY) {
        ctx.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        //ctx.drawTexture(TEXTURE, this.x + 59, this.y + 20, 0, this.backgroundHeight + (this.handler.getSlot(0).hasStack() ? 0 : 16), 110, 16);
    }

    @Override
    public void handledScreenTick(){
        super.handledScreenTick();
    }

    private void renderForeground(DrawContext ctx, int mouseX, int mouseY, float delta){

    }
}
