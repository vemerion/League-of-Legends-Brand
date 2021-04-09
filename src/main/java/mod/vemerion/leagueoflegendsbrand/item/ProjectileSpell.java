package mod.vemerion.leagueoflegendsbrand.item;

import java.util.function.Supplier;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ProjectileSpell extends BrandSpell {

	private Supplier<EntityType<? extends ProjectileEntity>> projectile;
	private float speed;
	private int cooldown;
	private Supplier<SoundEvent> sound;

	public ProjectileSpell(Supplier<EntityType<? extends ProjectileEntity>> projectile, float speed, int cooldown, Supplier<SoundEvent> sound) {
		super();
		this.projectile = projectile;
		this.speed = speed;
		this.cooldown = cooldown;
		this.sound = sound;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		entityLiving.playSound(sound.get(), 1, 0.8f + entityLiving.getRNG().nextFloat() * 0.4f);
		if (!worldIn.isRemote) {
			Vector3d direction = Vector3d.fromPitchYaw(entityLiving.getPitchYaw());
			Vector3d position = entityLiving.getPositionVec().add(direction.getX() * 0.8, 0.8, direction.getZ() * 0.8);
			ProjectileEntity entity = projectile.get().create(worldIn);
			entity.setPosition(position.x, position.y, position.z);
			entity.setShooter(entityLiving);
			entity.func_234612_a_(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0, speed, 0);
			worldIn.addEntity(entity);
		}
		if (entityLiving instanceof PlayerEntity) {
			((PlayerEntity) entityLiving).getCooldownTracker().setCooldown(this, cooldown);
		}
		return stack;
	}
}
