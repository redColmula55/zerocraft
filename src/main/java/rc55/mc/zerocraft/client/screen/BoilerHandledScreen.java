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
import rc55.mc.zerocraft.screen.BoilerScreenHandler;

@Environment(EnvType.CLIENT)
public class BoilerHandledScreen extends HandledScreen<BoilerScreenHandler> implements ScreenHandlerListener {
    //材质
    private static final Identifier TEXTURE = new Identifier(ZeroCraft.MODID, "textures/gui/container/boiler.png");

    private final BoilerScreenHandler handler;
    private int waterAmount;
    private int steamAmount;
    private int burnTime;

    public BoilerHandledScreen(BoilerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
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
        ctx.drawText(this.textRenderer, Text.of("Water: "+this.handler.getInputAmount()), this.x+30, this.y+15, 0x000000, false);
        ctx.drawText(this.textRenderer, Text.of("Steam: "+this.handler.getOutputAmount()), this.x+30, this.y+20, 0x000000, false);
        ctx.drawText(this.textRenderer, Text.of("Burn Time: "+this.handler.getBurnTime()), this.x+30, this.y+25, 0x000000, false);
    }
    //初始化
    private void setup() {

    }
    //每刻执行
    @Override
    public void handledScreenTick() {

    }
    //更新
    @Override
    public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {

    }
    @Override
    public void onPropertyUpdate(ScreenHandler handler, int property, int value) {

    }
}
