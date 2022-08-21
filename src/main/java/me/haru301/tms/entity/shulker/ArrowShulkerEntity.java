package me.haru301.tms.entity.shulker;

import me.haru301.tms.entity.AbstractCustomShulkerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import java.util.EnumSet;

public class ArrowShulkerEntity extends AbstractCustomShulkerEntity
{
    public ArrowShulkerEntity(EntityType<? extends ShulkerEntity> type, World world)
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
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0D)
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
            LivingEntity livingentity = ArrowShulkerEntity.this.getAttackTarget();
            if (livingentity != null && livingentity.isAlive()) {
                return ArrowShulkerEntity.this.world.getDifficulty() != Difficulty.PEACEFUL;
            } else {
                return false;
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.attackTime = 20; //TODO Balance Fix
            ArrowShulkerEntity.this.updateArmorModifier(100);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            ArrowShulkerEntity.this.updateArmorModifier(0);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (ArrowShulkerEntity.this.world.getDifficulty() != Difficulty.PEACEFUL) {
                --this.attackTime;
                LivingEntity livingentity = ArrowShulkerEntity.this.getAttackTarget();
                ArrowShulkerEntity.this.getLookController().setLookPositionWithEntity(livingentity, 180.0F, 180.0F);
                double d0 = ArrowShulkerEntity.this.getDistanceSq(livingentity);
                if (d0 < 1024.0D) {
                    if (this.attackTime <= 0) {
                        this.attackTime = 20; //TODO Balance Fix

                        this.attackEntityWithRangedAttack(livingentity, 1);

                        //CustomShulkerEntity.this.world.addEntity(new ShulkerBulletEntity(CustomShulkerEntity.this.world, CustomShulkerEntity.this, livingentity, CustomShulkerEntity.this.getAttachmentFacing().getAxis()));
                        //ArrowShulkerEntity.this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 2.0F, (ArrowShulkerEntity.this.rand.nextFloat() - ArrowShulkerEntity.this.rand.nextFloat()) * 0.2F + 1.0F);
                    }
                } else {
                    ArrowShulkerEntity.this.setAttackTarget(null);
                }

                super.tick();
            }
        }

        public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
            ItemStack itemstack = ArrowShulkerEntity.this.findAmmo(ArrowShulkerEntity.this.getHeldItem(ProjectileHelper.getWeaponHoldingHand(ArrowShulkerEntity.this, item -> item instanceof net.minecraft.item.BowItem)));
            AbstractArrowEntity abstractarrowentity = this.fireArrow(itemstack, distanceFactor);
            if (ArrowShulkerEntity.this.getHeldItemMainhand().getItem() instanceof net.minecraft.item.BowItem)
                abstractarrowentity = ((net.minecraft.item.BowItem) ArrowShulkerEntity.this.getHeldItemMainhand().getItem()).customArrow(abstractarrowentity);
            double d0 = target.getPosX() - ArrowShulkerEntity.this.getPosX();
            double d1 = target.getPosYHeight(0.3333333333333333D) - abstractarrowentity.getPosY();
            double d2 = target.getPosZ() - ArrowShulkerEntity.this.getPosZ();
            double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
            abstractarrowentity.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, 0.0f);
            ArrowShulkerEntity.this.playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 2.0F, (ArrowShulkerEntity.this.rand.nextFloat() - ArrowShulkerEntity.this.rand.nextFloat()) * 0.2F + 1.0F);
            ArrowShulkerEntity.this.world.addEntity(abstractarrowentity);
        }

        protected AbstractArrowEntity fireArrow(ItemStack arrowStack, float distanceFactor) {
            return ProjectileHelper.fireArrow(ArrowShulkerEntity.this, arrowStack, distanceFactor);
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
            return ArrowShulkerEntity.this.getAttackTarget() == null && ArrowShulkerEntity.this.rand.nextInt(40) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return ArrowShulkerEntity.this.getAttackTarget() == null && this.peekTime > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.peekTime = 20 * (1 + ArrowShulkerEntity.this.rand.nextInt(3));
            ArrowShulkerEntity.this.updateArmorModifier(30);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            if (ArrowShulkerEntity.this.getAttackTarget() == null) {
                ArrowShulkerEntity.this.updateArmorModifier(0);
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
            return ArrowShulkerEntity.this.world.getDifficulty() == Difficulty.PEACEFUL ? false : super.shouldExecute();
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
