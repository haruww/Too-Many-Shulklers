package me.haru301.tms.entity.shulker;

import me.haru301.tms.entity.AbstractCustomShulkerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.EnumSet;

public class ExplosiveShulkerEntity extends AbstractCustomShulkerEntity
{
    public ExplosiveShulkerEntity(EntityType<? extends ShulkerEntity> type, World world)
    {
        super(type, world);
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(4, new AttackGoal());
        this.goalSelector.addGoal(7, new PeekGoal());
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new AttackNearestGoal(this));
        this.targetSelector.addGoal(2, new DefenseAttackGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute getAttributes() {
        return LivingEntity.registerAttributes()
                .createMutableAttribute(Attributes.MAX_HEALTH, 30.0D) //TODO SET
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 64.0D)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK);
    }

    class AttackGoal extends Goal
    {
        private int attackTime;

        public AttackGoal() {
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            LivingEntity livingentity = ExplosiveShulkerEntity.this.getAttackTarget();
            if (livingentity != null && livingentity.isAlive()) {
                return ExplosiveShulkerEntity.this.world.getDifficulty() != Difficulty.PEACEFUL;
            } else {
                return false;
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.attackTime = 20; //TODO Balance Fix
            ExplosiveShulkerEntity.this.updateArmorModifier(100);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            ExplosiveShulkerEntity.this.updateArmorModifier(0);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (ExplosiveShulkerEntity.this.world.getDifficulty() != Difficulty.PEACEFUL) {
                --this.attackTime;
                LivingEntity livingentity = ExplosiveShulkerEntity.this.getAttackTarget();
                ExplosiveShulkerEntity.this.getLookController().setLookPositionWithEntity(livingentity, 180.0F, 180.0F);
                double d0 = ExplosiveShulkerEntity.this.getDistanceSq(livingentity);
                if (d0 < 4096.0D) {
                    if (this.attackTime <= 0) {
                        this.attackTime = 50; //TODO Balance Fix

                        double d1 = 4.0D;
                        Vector3d vector3d = ExplosiveShulkerEntity.this.getLook(1.0F);
                        double d2 = livingentity.getPosX() - (ExplosiveShulkerEntity.this.getPosX() + vector3d.x * 4.0D);
                        double d3 = livingentity.getPosYHeight(0.5D) - (0.5D + ExplosiveShulkerEntity.this.getPosYHeight(0.5D));
                        double d4 = livingentity.getPosZ() - (ExplosiveShulkerEntity.this.getPosZ() + vector3d.z * 4.0D);

                        FireballEntity fireballentity = new FireballEntity(world, ExplosiveShulkerEntity.this, d2, d3, d4);
                        fireballentity.explosionPower = 4; //TODO Balance Fix
                        fireballentity.setPosition(ExplosiveShulkerEntity.this.getPosX() + vector3d.x * 4.0D, ExplosiveShulkerEntity.this.getPosYHeight(0.5D) + 0.5D, fireballentity.getPosZ() + vector3d.z * 4.0D);
                        world.addEntity(fireballentity);
                        ExplosiveShulkerEntity.this.playSound(SoundEvents.ITEM_FIRECHARGE_USE, 2.0F, (ExplosiveShulkerEntity.this.rand.nextFloat() - ExplosiveShulkerEntity.this.rand.nextFloat()) * 0.2F + 1.0F);
                    }
                } else {
                    ExplosiveShulkerEntity.this.setAttackTarget(null);
                }

                super.tick();
            }
        }
    }

    class PeekGoal extends Goal {
        private int peekTime;

        private PeekGoal() {
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return ExplosiveShulkerEntity.this.getAttackTarget() == null && ExplosiveShulkerEntity.this.rand.nextInt(40) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return ExplosiveShulkerEntity.this.getAttackTarget() == null && this.peekTime > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.peekTime = 20 * (1 + ExplosiveShulkerEntity.this.rand.nextInt(3));
            ExplosiveShulkerEntity.this.updateArmorModifier(30);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            if (ExplosiveShulkerEntity.this.getAttackTarget() == null) {
                ExplosiveShulkerEntity.this.updateArmorModifier(0);
            }

        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.peekTime;
        }
    }

    class AttackNearestGoal extends NearestAttackableTargetGoal<PlayerEntity>
    {
        public AttackNearestGoal(ShulkerEntity shulker) {
            super(shulker, PlayerEntity.class, true);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return ExplosiveShulkerEntity.this.world.getDifficulty() == Difficulty.PEACEFUL ? false : super.shouldExecute();
        }

        protected AxisAlignedBB getTargetableArea(double targetDistance) {
            Direction direction = ((ShulkerEntity)this.goalOwner).getAttachmentFacing();
            if (direction.getAxis() == Direction.Axis.X) {
                return this.goalOwner.getBoundingBox().grow(4.0D, targetDistance, targetDistance);
            } else {
                return direction.getAxis() == Direction.Axis.Z ? this.goalOwner.getBoundingBox().grow(targetDistance, targetDistance, 4.0D) : this.goalOwner.getBoundingBox().grow(targetDistance, 4.0D, targetDistance);
            }
        }
    }

    static class DefenseAttackGoal extends NearestAttackableTargetGoal<LivingEntity> {
        public DefenseAttackGoal(ShulkerEntity shulker) {
            super(shulker, LivingEntity.class, 10, true, false, (p_200826_0_) -> {
                return p_200826_0_ instanceof IMob;
            });
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return this.goalOwner.getTeam() == null ? false : super.shouldExecute();
        }

        protected AxisAlignedBB getTargetableArea(double targetDistance) {
            Direction direction = ((ShulkerEntity)this.goalOwner).getAttachmentFacing();
            if (direction.getAxis() == Direction.Axis.X) {
                return this.goalOwner.getBoundingBox().grow(4.0D, targetDistance, targetDistance);
            } else {
                return direction.getAxis() == Direction.Axis.Z ? this.goalOwner.getBoundingBox().grow(targetDistance, targetDistance, 4.0D) : this.goalOwner.getBoundingBox().grow(targetDistance, 4.0D, targetDistance);
            }
        }
    }
}
