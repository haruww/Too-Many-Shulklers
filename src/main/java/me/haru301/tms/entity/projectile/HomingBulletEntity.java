package me.haru301.tms.entity.projectile;

import me.haru301.tms.init.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class HomingBulletEntity extends AbstractFireballEntity
{
    public int explosionPower = 1;
    private Entity target;
    public HomingBulletEntity(EntityType<? extends HomingBulletEntity> type, World world)
    {
        super(type, world);
    }

    public HomingBulletEntity(World worldIn, LivingEntity shooter, LivingEntity target, double accelX, double accelY, double accelZ)
    {
        super(ModEntities.HOMING_BULLET.get(), shooter, accelX, accelY, accelZ, worldIn);
        this.target = target;
    }

    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        this.remove();
    }

    public float getBrightness() {
        return 1.0F;
    }

    public boolean isBurning() {
        return false;
    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onEntityHit(EntityRayTraceResult result) {
        super.onEntityHit(result);
        Entity entity = result.getEntity();
        Entity entity1 = this.getShooter();
        LivingEntity livingentity = entity1 instanceof LivingEntity ? (LivingEntity)entity1 : null;
        boolean flag = entity.attackEntityFrom(DamageSource.causeIndirectDamage(this, livingentity).setProjectile(), 4.0F);
        if (flag) {
            this.applyEnchantments(livingentity, entity);
            if (entity instanceof LivingEntity) {
                ((LivingEntity)entity).addPotionEffect(new EffectInstance(Effects.POISON, 40));
            }
        }

        /*
        if (!this.world.isRemote) {
            Entity entity = result.getEntity();
            Entity entity1 = this.getShooter();
            LivingEntity livingentity = entity1 instanceof LivingEntity ? (LivingEntity)entity1 : null;
            boolean flag = entity.attackEntityFrom(DamageSource.causeIndirectDamage(this, livingentity).setProjectile(), 4.0F);

            if (flag) {
                this.applyEnchantments(livingentity, entity);
                if (entity instanceof LivingEntity) {
                    ((LivingEntity)entity).addPotionEffect(new EffectInstance(Effects.POISON, 50));
                }
            }
            ((ServerWorld)this.world).spawnParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY(), this.getPosZ(), 2, 0.2D, 0.2D, 0.2D, 0.0D);
            this.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0F, 1.0F);
        }

         */
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("ExplosionPower", this.explosionPower);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("ExplosionPower", 99)) {
            this.explosionPower = compound.getInt("ExplosionPower");
        }

    }

    public void tick()
    {
        Entity entity = this.getShooter();
        if (this.world.isRemote || (entity == null || !entity.removed) && this.world.isBlockLoaded(this.getPosition())) {
            super.tick();

            this.doBlockCollisions();
            Vector3d vector3d = this.getMotion();
            double d0 = this.getPosX() + vector3d.x;
            double d1 = this.getPosY() + vector3d.y;
            double d2 = this.getPosZ() + vector3d.z;
            ProjectileHelper.rotateTowardsMovement(this, 0.2F);

            this.world.addParticle(ParticleTypes.END_ROD, this.getPosX() - vector3d.x, this.getPosY() - vector3d.y + 0.15D, this.getPosZ() - vector3d.z, 0.0D, 0.0D, 0.0D);

            if (this.isInWater()) {
                for(int i = 0; i < 4; ++i) {
                    this.world.addParticle(ParticleTypes.BUBBLE, d0 - vector3d.x * 0.25D, d1 - vector3d.y * 0.25D, d2 - vector3d.z * 0.25D, vector3d.x, vector3d.y, vector3d.z);
                }
            }

            if(target!=null)
            {
                Vector3d projectileVecPos = this.getPositionVec();
                Vector3d targetVecPos = this.target.getPositionVec();
                targetVecPos = targetVecPos.add(0, 1, 0);
                Vector3d goalVec3 = targetVecPos.subtract(projectileVecPos).normalize();
                this.setMotion(goalVec3.scale(0.15D));
            }
            this.setPosition(d0, d1, d2);
        } else {
            this.remove();
        }
    }

    protected boolean isFireballFiery() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
        /*
        if (!this.world.isRemote) {
            this.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HURT, 1.0F, 1.0F);
            ((ServerWorld)this.world).spawnParticle(ParticleTypes.CRIT, this.getPosX(), this.getPosY(), this.getPosZ(), 15, 0.2D, 0.2D, 0.2D, 0.0D);
            this.remove();
        }

        return true;

         */
    }

    @Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
