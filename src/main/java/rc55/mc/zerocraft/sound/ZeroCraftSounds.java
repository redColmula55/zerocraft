package rc55.mc.zerocraft.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import rc55.mc.zerocraft.ZeroCraft;

public class ZeroCraftSounds {

    //音效
    public static final SoundEvent WRENCH_USE = register("wrench_use");
    //唱片
    public static final SoundEvent DISC_OST_RED_TIDE = register("music_disc.ost_red_tide");
    public static final SoundEvent DISC_IMAGE_SEEK = register("music_disc.image_seek");

    //注册用
    private static SoundEvent register(String id){
        return Registry.register(Registries.SOUND_EVENT, new Identifier(ZeroCraft.MODID,id), SoundEvent.of(new Identifier(ZeroCraft.MODID, id)));
    }
    //初始化注册
    public static void regSounds(){
        ZeroCraft.LOGGER.info("ZeroCraft sound events loaded.");
    }
}
