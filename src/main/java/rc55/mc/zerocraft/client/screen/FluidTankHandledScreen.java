package rc55.mc.zerocraft.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;
import rc55.mc.zerocraft.screen.FluidTankScreenHandler;

@Environment(EnvType.CLIENT)
public class FluidTankHandledScreen extends HandledScreen<FluidTankScreenHandler> implements ScreenHandlerListener {
    //材质
    private static final Identifier TEXTURE = new Identifier(ZeroCraft.MODID, "textures/gui/container/fluid_tank.png");

    private final FluidTankScreenHandler handler;
    private String storedFluidId;
    private int storedFluidAmount;

    public FluidTankHandledScreen(FluidTankScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);
        this.handler = handler;
    }
    //打开
    @Override
    protected void init() {
        super.init();
        this.setup();
        this.handler.addListener(this);
    }
    //关闭
    @Override
    public void removed() {
        super.removed();
        this.handler.removeListener(this);
    }
    //屏幕渲染
    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx);
        super.render(ctx, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(ctx, mouseX, mouseY);
    }
    //背景
    @Override
    protected void drawBackground(DrawContext ctx, float delta, int mouseX, int mouseY) {
        ctx.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        this.renderFluid(ctx, mouseX, mouseY, delta);
    }
    //每刻执行
    @Override
    public void handledScreenTick(){
        super.handledScreenTick();
        this.storedFluidId = this.handler.getStoredFluidId();
        this.storedFluidAmount = this.handler.getStoredFluidAmount();
    }
    //打开时执行
    private void setup() {
        this.storedFluidId = this.handler.getStoredFluidId();
        this.storedFluidAmount = this.handler.getStoredFluidAmount();
    }
    //渲染文字
    private void renderFluid(DrawContext ctx, int mouseX, int mouseY, float delta){
        int textX = this.x+45;
        int textY = this.y+55;
        if (this.storedFluidAmount != 0){
            ctx.drawText(this.textRenderer, Text.translatable(FluidTankScreenHandler.TANK_CARRYING_TRANS_KEY, new Object[]{Text.of(Integer.toString(this.storedFluidAmount)), Text.translatable(this.storedFluidId)}), textX, textY, 0xffffff, true);
        } else {
            ctx.drawText(this.textRenderer, Text.translatable(FluidTankScreenHandler.TANK_EMPTY_TRANS_KEY), textX, textY, 0xffffff, true);
        }
    }
    //更新
    @Override
    public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
        this.storedFluidId = this.handler.getStoredFluidId();
        this.storedFluidAmount = this.handler.getStoredFluidAmount();
    }

    @Override
    public void onPropertyUpdate(ScreenHandler handler, int property, int value) {

    }
}
