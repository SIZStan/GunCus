package de.cas_ual_ty.guncus.client;

import java.util.List;
import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;

import de.cas_ual_ty.guncus.item.attachments.Optic;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.data.IModelData;

public class BakedModelOptic implements IBakedModel
{
    private final IBakedModel modelMain;
    
    public BakedModelOptic(IBakedModel modelMain)
    {
        this.modelMain = modelMain;
    }
    
    @Override
    public ItemOverrideList getOverrides()
    {
        return this.modelMain.getOverrides();
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public TextureAtlasSprite getParticleTexture()
    {
        return this.modelMain.getParticleTexture();
    }
    
    @Override
    public TextureAtlasSprite getParticleTexture(IModelData data)
    {
        return this.modelMain.getParticleTexture(data);
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand)
    {
        return this.modelMain.getQuads(state, side, rand);
    }
    
    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand, IModelData extraData)
    {
        return this.modelMain.getQuads(state, side, rand, extraData);
    }
    
    @Override
    public boolean isAmbientOcclusion()
    {
        return this.modelMain.isAmbientOcclusion();
    }
    
    @Override
    public boolean isBuiltInRenderer()
    {
        return this.modelMain.isBuiltInRenderer();
    }
    
    @Override
    public boolean isSideLit()
    {
        return this.modelMain.isSideLit();
    }
    
    @Override
    public boolean isGui3d()
    {
        return this.modelMain.isGui3d();
    }
    
    @Override
    public IBakedModel handlePerspective(TransformType transformType, MatrixStack mat)
    {
        if(transformType == TransformType.FIRST_PERSON_RIGHT_HAND)
        {
            PlayerEntity entityPlayer = ProxyClient.getClientPlayer();
            
            if(entityPlayer != null && !entityPlayer.isSprinting() && entityPlayer.getHeldItemMainhand().getItem() instanceof Optic && ProxyClient.BUTTON_AIM_DOWN.get())
            {
                ItemStack itemStack = entityPlayer.getHeldItemMainhand();
                Optic optic = (Optic)itemStack.getItem();
                
                if(optic != null && optic.canAim())
                {
                    mat.push();
                    mat.scale(0, 0, 0);
                    return this;
                }
            }
        }
        
        this.modelMain.handlePerspective(transformType, mat);
        return this;
    }
}
