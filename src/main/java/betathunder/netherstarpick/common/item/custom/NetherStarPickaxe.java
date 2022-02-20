package betathunder.netherstarpick.common.item.custom;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.world.World;
import betathunder.netherstarpick.common.block.ModBlocks;

public class NetherStarPickaxe extends PickaxeItem {

	public NetherStarPickaxe(IItemTier p_i48478_1_, int bonusDamage, float digSpeed, Properties p_i48478_4_) {
		super(p_i48478_1_, bonusDamage, digSpeed, p_i48478_4_);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player) {
		World currentLevel = player.level;
		TileEntity currentBlock = currentLevel.getBlockEntity(pos);

		if (currentBlock instanceof MobSpawnerTileEntity) {
			if (!currentLevel.isClientSide()) {
				currentLevel.removeBlockEntity(pos);
				currentLevel.destroyBlock(pos, false);

				CompoundNBT spawnerDataTag = currentBlock.getUpdateTag();
				String mobId = spawnerDataTag.getCompound("SpawnData").getString("id");
				StringBuilder pendingName = new StringBuilder("Spawner (");
				int nameStart = mobId.indexOf(":") + 1;
				int nextUnderscore = nameStart;
				while (nextUnderscore < mobId.length()) {
					nextUnderscore = mobId.indexOf("_", nextUnderscore + 1);
					if (nextUnderscore == -1) {
						nextUnderscore = mobId.length();
					}
					pendingName.append(mobId.substring(nameStart, nameStart + 1).toUpperCase());
					pendingName.append(mobId.substring(nameStart + 1, nextUnderscore));
					if (nextUnderscore < mobId.length()) {
						pendingName.append(" ");
					}
					nameStart = nextUnderscore + 1;
				}
				pendingName.append(")");
				StringTextComponent nameTextComponent = new StringTextComponent(pendingName.toString());
				nameTextComponent.setStyle(Style.EMPTY.withItalic(false));
				spawnerDataTag.remove("id");
				spawnerDataTag.remove("x");
				spawnerDataTag.remove("y");
				spawnerDataTag.remove("z");

				ItemStack spawnerDrop = new ItemStack(ModBlocks.SPAWNER_SAVED.get().asItem(), 1);
				spawnerDrop.setHoverName(nameTextComponent);
				spawnerDrop.addTagElement("BlockEntityTag", spawnerDataTag);
				currentLevel
						.addFreshEntity(new ItemEntity(currentLevel, pos.getX(), pos.getY(), pos.getZ(), spawnerDrop));
				itemstack.hurtAndBreak(1, player, (playerEntity) -> {
					playerEntity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
				});
			}
			return true;
		}
		return false;
	}
}
