package com.example.examplemod.saveddata;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.tileentity.ChunkChestTileEntity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.items.ItemStackHandler;

import java.util.HashMap;
import java.util.Map;

public class WorldData extends WorldSavedData {
    private static int INV_SIZE = 16*16*256*27;
    private static String DATA_NAME = ExampleMod.MODID + "_Data";

    public WorldData(String name) {
        super(name);
        mynbt = new NBTTagCompound();
    }

    public static WorldData get(World world) {
        MapStorage storage = world.getPerWorldStorage();
        WorldData instance = (WorldData) storage.getOrLoadData(WorldData.class, DATA_NAME);

        if (instance == null) {
            instance = new WorldData(DATA_NAME);
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

    private Map<String, ItemStackHandler> chunkChestHandlers = new HashMap<>();

    public ItemStackHandler getChunkChest(int chunkx, int chunky) {
        setDefault(mynbt, "chunkchest", new NBTTagCompound());
        NBTTagCompound chunkchest = mynbt.getCompoundTag("chunkchest");

        String key = chunkx + "_" + chunky;

        if (chunkChestHandlers.containsKey(key)) {
            return chunkChestHandlers.get(key);
        } else {
            ItemStackHandler handler = new ItemStackHandler(INV_SIZE) {
                @Override
                protected void onContentsChanged(int slot) {
                    WorldData.this.markDirty();
                }
            };
            if (!chunkchest.hasKey(key)) {
                chunkchest.setTag(key, handler.serializeNBT());
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
        for (Map.Entry<String, ItemStackHandler> entry : chunkChestHandlers.entrySet()) {
            setDefault(mynbt, "chunkchest", new NBTTagCompound());
            NBTTagCompound chunkchest = mynbt.getCompoundTag("chunkchest");
            chunkchest.setTag(entry.getKey(), entry.getValue().serializeNBT());
        }
        nbt.setTag("mynbt", mynbt);
        return nbt;
    }
}
