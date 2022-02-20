package betathunder.netherstarpick.common.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public class NetherStarTier implements IItemTier {
	@Override
	public int getUses() {
		return 512;
	}

	@Override
	public float getSpeed() {
		return 6.0f;
	}

	@Override
	public float getAttackDamageBonus() {
		return 3.0f;
	}

	@Override
	public int getLevel() {
		return 4;
	}

	@Override
	public int getEnchantmentValue() {
		return 30;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.of(Items.NETHER_STAR);
	}
}
