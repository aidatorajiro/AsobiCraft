package com.example.examplemod.saveddata;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.tileentity.ChunkChestTileEntity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.items.ItemStackHandler;

public class WorldData extends WorldSavedData {
    private static int INV_SIZE = 16*16*256*27;
    private static String DATA_NAME = ExampleMod.MODID + "_Data";

    public WorldData() {
        super(DATA_NAME);
        mynbt = new NBTTagCompound();
    }

    public static WorldData get(World world) {
        MapStorage storage = world.getPerWorldStorage();
        WorldData instance = (WorldData) storage.getOrLoadData(WorldData.class, DATA_NAME);

        if (instance == null) {
            instance = new WorldData();
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

    public ItemStackHandler getChunkChest(ItemStackHandler handler, int chunkx, int chunky) {
        setDefault(mynbt, "chunkchest", new NBTTagCompound());
        NBTTagCompound chunkchest = mynbt.getCompoundTag("chunkchest");
        String key = chunkx + "_" + chunky;
        if (!chunkchest.hasKey(key)) {
            chunkchest.setTag(key, new ItemStackHandler(INV_SIZE).serializeNBT());
            handler.setSize(INV_SIZE);
        } else {
            handler.deserializeNBT(chunkchest.getCompoundTag(key));
        }
        return handler;
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
        nbt.setTag("mynbt", mynbt);
        return nbt;
    }
}
