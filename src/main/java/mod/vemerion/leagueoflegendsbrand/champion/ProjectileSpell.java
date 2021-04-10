package mod.vemerion.leagueoflegendsbrand.champion;

import java.util.function.Supplier;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ProjectileSpell extends Spell {

	private Supplier<EntityType<? extends ProjectileEntity>> projectile;
	private float speed;
	private int cooldown;
	private Supplier<SoundEvent> sound;

	public ProjectileSpell(Supplier<EntityType<? extends ProjectileEntity>> projectile, float speed, int cooldown,
			Supplier<SoundEvent> sound) {
		super();
		this.projectile = projectile;
		this.speed = speed;
		this.cooldown = cooldown;
		this.sound = sound;
	}

	@Override
	public void finish(ItemStack stack, World world, PlayerEntity player) {
		player.playSound(sound.get(), 1, 0.8f + player.getRNG().nextFloat() * 0.4f);
		if (!world.isRemote) {
			Vector3d direction = Vector3d.fromPitchYaw(player.getPitchYaw());
			Vector3d position = player.getPositionVec().add(direction.getX() * 0.8, 0.8, direction.getZ() * 0.8);
			ProjectileEntity entity = projectile.get().create(world);
			entity.setPosition(position.x, position.y, position.z);
			entity.setShooter(player);
			entity.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0, speed, 0);
			world.addEntity(entity);
		}
		setCooldown(player, stack, cooldown);
	}
}
