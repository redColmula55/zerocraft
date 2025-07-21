package rc55.mc.zerocraft.api;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public final class Utils {
    private Utils() {}
    //循环遍历Iterable
    public static <T> T cycle(Iterable<T> elements, @Nullable T current, boolean inverse) {
        return inverse ? Util.previous(elements, current) : Util.next(elements, current);
    }
    //随机,chance百分比几率为true
    public static boolean getRandomPercent(Random random, int chance) {
        return chance - random.nextBetween(0, 99) > 0;
    }
    //发送提示信息
    public static void sendMessage(PlayerEntity player, Text message, boolean overlay) {
        if (player instanceof ServerPlayerEntity serverPlayer) serverPlayer.sendMessageToClient(message, overlay);
    }
    public static void sendMessage(PlayerEntity player, Text message) {
        sendMessage(player, message, true);
    }
    //提供属性值
    public static <T extends Comparable<T>> String getPropertyValue(BlockState state, Property<T> property) {
        return property.name(state.get(property));
    }
    //设置方块属性
    public static <T extends Comparable<T>> BlockState withProperty(BlockState state, Property<T> property, String name) {
        return property.parse(name).map(value -> state.with(property, value)).orElse(state);
    }
    //提供翻译键
    //容器
    public static String getContainerTransKey(BlockEntityType<?> type) {
        return Util.createTranslationKey("container", Registries.BLOCK_ENTITY_TYPE.getId(type));
    }
    //物品组
    public static String getItemGroupTransKey(RegistryKey<ItemGroup> key) {
        return Util.createTranslationKey("itemGroup", key.getValue());
    }
    //伤害类型
    public static String getDamageTypeTransKey(RegistryKey<DamageType> key) {
        return "death.attack." + removeUnderline(key.getValue().getPath());
    }

    //将以下划线分割的字符串转为驼峰法
    public static String removeUnderline(String str) {
        StringBuilder sb = new StringBuilder();
        boolean shallReplace = false;
        for (char c : str.toCharArray()) {
            if (shallReplace) {
                sb.append(Character.toUpperCase(c));
                shallReplace = false;
                continue;
            }
            if (c == '_') {
                shallReplace = true;
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
