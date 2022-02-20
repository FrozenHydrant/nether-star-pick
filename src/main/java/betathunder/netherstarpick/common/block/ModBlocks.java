package betathunder.netherstarpick.common.block;

import java.util.function.Supplier;

import betathunder.netherstarpick.NetherStarPick;
import betathunder.netherstarpick.common.item.ModItems;
import betathunder.netherstarpick.common.item.custom.SpawnerSavedItem;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			NetherStarPick.MOD_ID);

	public static final RegistryObject<Block> SPAWNER_SAVED = registerBlock("spawner_saved",
			() -> new Block(Properties.of(Material.METAL).strength(5f, 6f)), true);

	public static RegistryObject<Block> registerBlock(String name, Supplier<Block> b, boolean isSpawnerItem) {
		RegistryObject<Block> tmp = BLOCKS.register(name, b);
		registerBlockItem(name, tmp, isSpawnerItem);
		return tmp;
	}

	public static void registerBlockItem(String name, RegistryObject<Block> b, boolean isSpawnerItem) {
		if (isSpawnerItem) {
			ModItems.registerItem(name,
					() -> new SpawnerSavedItem(b.get(), new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)));
		} else {
			ModItems.registerItem(name,
					() -> new BlockItem(b.get(), new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)));
		}
	}

	public static void registerBlocks(IEventBus ie) {
		BLOCKS.register(ie);
	}
}
