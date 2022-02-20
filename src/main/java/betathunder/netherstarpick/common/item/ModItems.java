package betathunder.netherstarpick.common.item;

import java.util.function.Supplier;

import betathunder.netherstarpick.NetherStarPick;
import betathunder.netherstarpick.common.item.custom.NetherStarPickaxe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			NetherStarPick.MOD_ID);
	
	public static final RegistryObject<Item> NETHERSTAR_PICKAXE = registerItem("netherstar_pickaxe",
			() -> new NetherStarPickaxe(new NetherStarTier(), 1, -2.8F,
					new Item.Properties().tab(ItemGroup.TAB_TOOLS)));

	public static RegistryObject<Item> registerItem(String name, Supplier<Item> item) {
		return ITEMS.register(name, item);
	}

	public static void registerItems(IEventBus ie) {
		ITEMS.register(ie);
	}
}
