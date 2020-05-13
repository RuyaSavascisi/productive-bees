package cy.jdkdigital.productivebees.event;

import cy.jdkdigital.productivebees.integrations.jei.ProduciveBeesJeiPlugin;
import cy.jdkdigital.productivebees.recipe.AdvancedBeehiveRecipe;
import cy.jdkdigital.productivebees.recipe.BeeSpawningBigRecipe;
import cy.jdkdigital.productivebees.recipe.BeeSpawningRecipe;
import cy.jdkdigital.productivebees.recipe.CentrifugeRecipe;
import cy.jdkdigital.productivebees.util.BeeHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventHandler
{
    @SubscribeEvent
    public void entityRightClicked(PlayerInteractEvent.EntityInteract entityInteract) {
        ItemStack itemStack = entityInteract.getItemStack();
        Entity entity = entityInteract.getTarget();

        if (!itemStack.isEmpty() && entity instanceof BeeEntity) {
            World world = entityInteract.getWorld();
            PlayerEntity player = entityInteract.getPlayer();
            BlockPos pos = entity.getPosition();
            Hand hand = entityInteract.getHand();

            BeeEntity newBee = BeeHelper.itemInteract((BeeEntity) entity, itemStack, world, entity.serializeNBT(), player, hand, entity.getHorizontalFacing());

            if (newBee != null) {
                // PLay event with smoke
                world.addParticle(ParticleTypes.POOF, pos.getX(), pos.getY() + 1, pos.getZ(), 0.2D, 0.1D, 0.2D);
                world.playSound(player, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BEEHIVE_WORK, SoundCategory.NEUTRAL, 1.0F, 1.0F);

                world.addEntity(newBee);
                entity.remove();
            }
        }
    }

    @SubscribeEvent
    public static void recipe(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
        event.getRegistry().register(new AdvancedBeehiveRecipe.Serializer<>(AdvancedBeehiveRecipe::new).setRegistryName(ProduciveBeesJeiPlugin.CATEGORY_ADVANCED_BEEHIVE_UID));
        event.getRegistry().register(new CentrifugeRecipe.Serializer<>(CentrifugeRecipe::new).setRegistryName(ProduciveBeesJeiPlugin.CATEGORY_CENTRIFUGE_UID));
        event.getRegistry().register(new BeeSpawningRecipe.Serializer<>(BeeSpawningRecipe::new).setRegistryName(ProduciveBeesJeiPlugin.CATEGORY_BEE_SPAWNING_UID));
        event.getRegistry().register(new BeeSpawningBigRecipe.Serializer<>(BeeSpawningBigRecipe::new).setRegistryName(ProduciveBeesJeiPlugin.CATEGORY_BEE_SPAWNING_BIG_UID));
    }
}
