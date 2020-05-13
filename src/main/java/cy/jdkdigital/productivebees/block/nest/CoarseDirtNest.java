package cy.jdkdigital.productivebees.block.nest;

import cy.jdkdigital.productivebees.block.SolitaryNest;
import cy.jdkdigital.productivebees.init.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.Dimension;

public class CoarseDirtNest extends SolitaryNest
{
    public CoarseDirtNest(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canRepopulateIn(Dimension dimension, Biome biome) {
        return dimension.isSurfaceWorld();
    }

    @Override
    public EntityType<BeeEntity> getNestingBeeType(World world) {
        switch (world.rand.nextInt(2)) {
            case 0:
                return ModEntities.ASHY_MINING_BEE.get();
            case 1:
                return ModEntities.CHOCOLATE_MINING_BEE.get();
            case 2:
            default:
                return ModEntities.LEAFCUTTER_BEE.get();
        }
    }
}
