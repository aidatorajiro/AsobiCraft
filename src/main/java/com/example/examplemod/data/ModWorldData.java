package com.example.examplemod.data;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.itemhandler.AdjustableItemStackHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;
import java.util.Map;

public class ModWorldData extends WorldSavedData {
    public static int CHUNK_CHEST_SIZE_MAX = 24*256*27; // 24*256 chests = 1 chunk-chest
    public static int CHUNK_CHEST_SIZE_DEFAULT = 4*27; // 4 chests

    private static String DATA_NAME = ExampleMod.MODID + "_Data";

    public ModWorldData(String name) {
        super(name);
        mynbt = new NBTTagCompound();
    }

    public static ModWorldData get(World world) {
        MapStorage storage = world.getPerWorldStorage();
        ModWorldData instance = (ModWorldData) storage.getOrLoadData(ModWorldData.class, DATA_NAME);

        if (instance == null) {
            instance = new ModWorldData(DATA_NAME);
            storage.setData(DATA_NAME, instance);
        }

        return instance;
    }

    private void setDefault(NBTTagCompound nbt, String key , NBTBase item) {
        if (!nbt.hasKey(key)) {
            nbt.setTag(key, item);
        }
    }

    private NBTTagCompound mynbt;

    private Map<String, AdjustableItemStackHandler> chunkChestHandlers = new HashMap<>();

    public AdjustableItemStackHandler getChunkChest(int chunkx, int chunkz) {
        setDefault(mynbt, "chunkchest", new NBTTagCompound());
        NBTTagCompound chunkchest = mynbt.getCompoundTag("chunkchest");

        String key = chunkx + "_" + chunkz;

        if (chunkChestHandlers.containsKey(key)) {
            return chunkChestHandlers.get(key);
        } else {
            AdjustableItemStackHandler handler = new AdjustableItemStackHandler(CHUNK_CHEST_SIZE_DEFAULT) {
                @Override
                protected void onContentsChanged(int slot) {
                    ModWorldData.this.markDirty();
                }
            };
            if (!chunkchest.hasKey(key)) {
                chunkchest.setTag(key, handler.serializeNBT());
                this.markDirty();
            } else {
                handler.deserializeNBT(chunkchest.getCompoundTag(key));
            }
            chunkChestHandlers.put(key, handler);
            return handler;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        if (!nbt.hasKey("mynbt")) {
            mynbt = new NBTTagCompound();
        } else {
            mynbt = nbt.getCompoundTag("mynbt");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        for (Map.Entry<String, AdjustableItemStackHandler> entry : chunkChestHandlers.entrySet()) {
            setDefault(mynbt, "chunkchest", new NBTTagCompound());
            NBTTagCompound chunkchest = mynbt.getCompoundTag("chunkchest");
            chunkchest.setTag(entry.getKey(), entry.getValue().serializeNBT());
        }
        nbt.setTag("mynbt", mynbt);
        return nbt;
    }
}
