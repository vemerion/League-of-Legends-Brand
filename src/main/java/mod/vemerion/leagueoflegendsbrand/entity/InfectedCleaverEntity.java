package mod.vemerion.leagueoflegendsbrand.entity;

import mod.vemerion.leagueoflegendsbrand.helper.Helper;
import mod.vemerion.leagueoflegendsbrand.init.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class InfectedCleaverEntity extends SkillshotEntity {

	public InfectedCleaverEntity(EntityType<? extends InfectedCleaverEntity> type, World world) {
		super(type, world, 25);
	}

	public InfectedCleaverEntity(EntityType<? extends InfectedCleaverEntity> type, double x, double y, double z,
			World worldIn) {
		super(type, x, y, z, worldIn, 25);
	}

	@Override
	protected void onEntityHit(EntityRayTraceResult result) {
		if (!world.isRemote) {
			Entity target = result.getEntity();
			Entity shooter = func_234616_v_();
			if (target instanceof LivingEntity && shooter instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) shooter;
				((LivingEntity) target).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 40, 0));
				target.attackEntityFrom(DamageSource.causePlayerDamage(player), 4);
				player.heal(target instanceof PlayerEntity ? 2 : 1);
			}

		}
		playSound(ModSounds.INFECTED_CLEAVER_IMPACT, 0.8f, Helper.soundPitch(rand));
		remove();
	}

}
