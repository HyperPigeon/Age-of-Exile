package com.robertx22.age_of_exile.a_libraries.neat;

public class HealthBarRenderer {

    /*
    public static Entity getEntityLookedAt(Entity e) {
        Entity foundEntity = null;
        final double finalDistance = 32;
        double distance = finalDistance;
        RayTraceResult pos = raycast(e, finalDistance);
        Vector3d positionVector = e.position();

        if (e instanceof PlayerEntity)
            positionVector = positionVector.add(0, e.getEyeHeight(e.getPose()), 0);

        if (pos != null)
            distance = pos.getLocation()
                .distanceTo(positionVector);

        Vector3d lookVector = e.getLookAngle();
        Vector3d reachVector = positionVector.add(lookVector.x * finalDistance, lookVector.y * finalDistance, lookVector.z * finalDistance);

        Entity lookedEntity = null;
        List<Entity> entitiesInBoundingBox = e.level
            .getEntities(e, e.getBoundingBox()
                .inflate(lookVector.x * finalDistance, lookVector.y * finalDistance, lookVector.z * finalDistance)
                .inflate(1F, 1F, 1F));
        double minDistance = distance;

        for (Entity entity : entitiesInBoundingBox) {
            if (entity.canBeCollidedWith()) {
                AxisAlignedBB collisionBox = entity.getBoundingBox();
                Optional<Vector3d> interceptPosition = collisionBox.rayTrace(positionVector, reachVector);

                if (collisionBox.contains(positionVector)) {
                    if (0.0D < minDistance || minDistance == 0.0D) {
                        lookedEntity = entity;
                        minDistance = 0.0D;
                    }
                } else if (interceptPosition.isPresent()) {
                    double distanceToEntity = positionVector.distanceTo(interceptPosition.get());

                    if (distanceToEntity < minDistance || minDistance == 0.0D) {
                        lookedEntity = entity;
                        minDistance = distanceToEntity;
                    }
                }
            }

            if (lookedEntity != null && (minDistance < distance || pos == null))
                foundEntity = lookedEntity;
        }

        return foundEntity;
    }

    public static RayTraceResult raycast(Entity e, double len) {
        Vector3d vec = new Vector3d(e.getPosX(), e.getY(), e.getZ());
        if (e instanceof PlayerEntity)
            vec = vec.add(new Vector3d(0, e.getEyeHeight(e.getPose()), 0));

        Vector3d look = e.getLookVec();
        if (look == null)
            return null;

        return raycast(vec, look, e, len);
    }

    public static RayTraceResult raycast(Vector3d origin, Vector3d ray, Entity e, double len) {
        Vector3d next = origin.add(ray.normalize()
            .scale(len));
        return e.world.rayTraceBlocks(new RayTraceContext(origin, next, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, e));
    }

    @Nonnull
    public static ItemStack getIcon(LivingEntity entity, boolean boss) {
        if (boss) {
            return new ItemStack(Items.NETHER_STAR);
        }
        CreatureAttribute attr = entity.getMobType();
        if (attr == CreatureAttribute.ARTHROPOD) {
            return new ItemStack(Items.SPIDER_EYE);
        } else if (attr == CreatureAttribute.UNDEAD) {
            return new ItemStack(Items.ROTTEN_FLESH);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public static int getColor(LivingEntity entity, boolean colorByType, boolean boss) {
        if (colorByType) {
            int r = 0;
            int g = 255;
            int b = 0;
            if (boss) {
                r = 128;
                g = 0;
                b = 128;
            }
            if (entity instanceof MonsterEntity) {
                r = 255;
                g = 0;
                b = 0;
            }
            return 0xff000000 | r << 16 | g << 8 | b;
        } else {
            float health = MathHelper.clamp(entity.getHealth(), 0.0F, entity.getMaxHealth());
            float hue = Math.max(0.0F, (health / entity.getMaxHealth()) / 3.0F - 0.07F);
            return Color.HSBtoRGB(hue, 1.0F, 1.0F);
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if ((!NeatConfig.renderInF1 && !Minecraft.isGuiEnabled()) || !NeatConfig.draw)
            return;

        ActiveRenderInfo renderInfo = mc.gameRenderer.getActiveRenderInfo();
        MatrixStack matrixStack = event.getMatrixStack();
        float partialTicks = event.getPartialTicks();
        Entity cameraEntity = renderInfo.getRenderViewEntity() != null ? renderInfo.getRenderViewEntity() : mc.player;

        if (NeatConfig.showOnlyFocused) {
            Entity focused = getEntityLookedAt(mc.player);
            if (focused != null && focused instanceof LivingEntity && focused.isAlive()) {
                renderHealthBar((LivingEntity) focused, mc, matrixStack, partialTicks, renderInfo, cameraEntity);
            }
        } else {
            Vector3d cameraPos = renderInfo.getProjectedView();
            final ClippingHelper clippingHelper = new ClippingHelper(matrixStack.getLast()
                .getMatrix(), event.getProjectionMatrix());
            clippingHelper.setCameraPosition(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());

            ClientWorld client = mc.world;
            if (client != null) {
                for (Entity entity : client.getAllEntities()) {
                    if (entity != null && entity instanceof LivingEntity && entity != cameraEntity && entity.isAlive() && entity.getRecursivePassengers()
                        .isEmpty() && entity.isInRangeToRender3d(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ()) && (entity.ignoreFrustumCheck || clippingHelper.isBoundingBoxInFrustum(entity.getBoundingBox()))) {
                        renderHealthBar((LivingEntity) entity, mc, matrixStack, partialTicks, renderInfo, cameraEntity);
                    }
                }
            }
        }
    }

    public void renderHealthBar(LivingEntity passedEntity, Minecraft mc, MatrixStack matrixStack, float partialTicks, ActiveRenderInfo renderInfo, Entity viewPoint) {
        Stack<LivingEntity> ridingStack = new Stack<>();

        LivingEntity entity = passedEntity;
        ridingStack.push(entity);

        while (entity.getRidingEntity() != null && entity.getRidingEntity() instanceof LivingEntity) {
            entity = (LivingEntity) entity.getRidingEntity();
            ridingStack.push(entity);
        }

        matrixStack.pushPose();
        while (!ridingStack.isEmpty()) {
            entity = ridingStack.pop();
            boolean boss = !entity.isNonBoss();

            String entityID = entity.getType()
                .getRegistryName()
                .toString();
            if (NeatConfig.blacklist.contains(entityID))
                continue;

            processing:
            {
                float distance = passedEntity.distanceTo(viewPoint);
                if (distance > NeatConfig.maxDistance || !passedEntity.canSee(viewPoint) || entity.isInvisible())
                    break processing;
                if (!NeatConfig.showOnBosses && !boss)
                    break processing;
                if (!NeatConfig.showOnPlayers && entity instanceof PlayerEntity)
                    break processing;
                if (entity.getMaxHealth() <= 0)
                    break processing;
                if (!NeatConfig.showFullHealth && entity.getHealth() == entity.getMaxHealth())
                    break processing;

                double x = passedEntity.xOld + (passedEntity.getX() - passedEntity.xOld) * partialTicks;
                double y = passedEntity.yOld + (passedEntity.getY() - passedEntity.yOld) * partialTicks;
                double z = passedEntity.zOld + (passedEntity.getZ() - passedEntity.zOld) * partialTicks;

                EntityRendererManager renderManager = Minecraft.getInstance()
                    .getEntityRenderDispatcher();
                Vector3d renderPos = renderManager.info.getProjectedView();

                matrixStack.pushPose();
                matrixStack.translate((float) (x - renderPos.getX()), (float) (y - renderPos.getY() + passedEntity.getHeight() + NeatConfig.heightAbove), (float) (z - renderPos.getZ()));
                IRenderTypeBuffer.Impl buffer = mc.getRenderTypeBuffers()
                    .getBufferSource();
                ItemStack icon = getIcon(entity, boss);
                final int light = 0xF000F0;
                renderEntity(mc, matrixStack, buffer, renderInfo, entity, light, icon, boss);
                matrixStack.pop();

                matrixStack.translate(0.0D, -(NeatConfig.backgroundHeight + NeatConfig.barHeight + NeatConfig.backgroundPadding), 0.0D);
            }
        }
        matrixStack.popPose();

    }

    private void renderEntity(Minecraft mc, MatrixStack matrixStack, IRenderTypeBuffer.Impl buffer, ActiveRenderInfo renderInfo, LivingEntity entity, int light, ItemStack icon, boolean boss) {
        Quaternion rotation = renderInfo.rotation()
            .copy();
        rotation.mul(-1.0F);
        matrixStack.mulPose(rotation);
        float scale = 0.026666672F;
        matrixStack.scale(-scale, -scale, scale);
        float health = MathHelper.clamp(entity.getHealth(), 0.0F, entity.getMaxHealth());
        float percent = (health / entity.getMaxHealth()) * 100.0F;
        float size = NeatConfig.plateSize;
        float textScale = 0.5F;

        String name = (entity.hasCustomName() ? entity.getCustomName() : entity.getDisplayName()).getString();
        if (entity.hasCustomName())
            name = TextFormatting.ITALIC + name;

        float namel = mc.font.width(name) * textScale;
        if (namel + 20 > size * 2) {
            size = namel / 2.0F + 10.0F;
        }
        float healthSize = size * (health / entity.getMaxHealth());
        MatrixStack.Entry entry = matrixStack.getLast();
        Matrix4f modelViewMatrix = entry.getMatrix();
        Vector3f normal = new Vector3f(0.0F, 1.0F, 0.0F);
        normal.transform(entry.normal());
        IVertexBuilder builder = buffer.getBuffer(NeatRenderType.getHealthBarType(NeatRenderType.HEALTH_BAR_TEXTURE));
        float padding = NeatConfig.backgroundPadding;
        int bgHeight = NeatConfig.backgroundHeight;
        int barHeight = NeatConfig.barHeight;

        // Background
        if (NeatConfig.drawBackground) {
            builder.vertex(modelViewMatrix, -size - padding, -bgHeight, 0.01F)
                .uv(0.0F, 0.0F)
                .color(0, 0, 0, 64)
                .normal(normal.x(), normal.y(), normal.z())
                .uv2(light)
                .endVertex();
            builder.vertex(modelViewMatrix, -size - padding, barHeight + padding, 0.01F)
                .uv(0.0F, 0.5F)
                .color(0, 0, 0, 64)
                .normal(normal.x(), normal.y(), normal.z())
                .uv2(light)
                .endVertex();
            builder.vertex(modelViewMatrix, size + padding, barHeight + padding, 0.01F)
                .uv(1.0F, 0.5F)
                .color(0, 0, 0, 64)
                .normal(normal.x(), normal.y(), normal.z())
                .uv2(light)
                .endVertex();
            builder.vertex(modelViewMatrix, size + padding, -bgHeight, 0.01F)
                .uv(1.0F, 0.0F)
                .color(0, 0, 0, 64)
                .normal(normal.x(), normal.y(), normal.z())
                .uv2(light)
                .endVertex();
        }

        // Health Bar
        int argb = getColor(entity, NeatConfig.colorByType, boss);
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;

        builder.vertex(modelViewMatrix, -size, 0, 0.001F)
            .uv(0.0F, 0.75F)
            .color(r, g, b, 127)
            .normal(normal.x(), normal.y(), normal.z())
            .uv2(light)
            .endVertex();
        builder.vertex(modelViewMatrix, -size, barHeight, 0.001F)
            .uv(0.0F, 1.0F)
            .color(r, g, b, 127)
            .normal(normal.x(), normal.y(), normal.z())
            .uv2(light)
            .endVertex();
        builder.vertex(modelViewMatrix, healthSize * 2 - size, barHeight, 0.001F)
            .uv(1.0F, 1.0F)
            .color(r, g, b, 127)
            .normal(normal.x(), normal.y(), normal.z())
            .uv2(light)
            .endVertex();
        builder.vertex(modelViewMatrix, healthSize * 2 - size, 0, 0.001F)
            .uv(1.0F, 0.75F)
            .color(r, g, b, 127)
            .normal(normal.x(), normal.y(), normal.z())
            .uv2(light)
            .endVertex();

        //Health bar background
        if (healthSize < size) {
            builder.vertex(modelViewMatrix, -size + healthSize * 2, 0, 0.001F)
                .uv(0.0F, 0.5F)
                .color(0, 0, 0, 127)
                .normal(normal.x(), normal.y(), normal.z())
                .uv2(light)
                .endVertex();
            builder.vertex(modelViewMatrix, -size + healthSize * 2, barHeight, 0.001F)
                .uv(0.0F, 0.75F)
                .color(0, 0, 0, 127)
                .normal(normal.x(), normal.y(), normal.z())
                .uv2(light)
                .endVertex();
            builder.vertex(modelViewMatrix, size, barHeight, 0.001F)
                .uv(1.0F, 0.75F)
                .color(0, 0, 0, 127)
                .normal(normal.x(), normal.y(), normal.z())
                .uv2(light)
                .endVertex();
            builder.vertex(modelViewMatrix, size, 0, 0.001F)
                .uv(1.0F, 0.5F)
                .color(0, 0, 0, 127)
                .normal(normal.x(), normal.y(), normal.z())
                .uv2(light)
                .endVertex();
        }

        {
            int white = 0xFFFFFF;
            int black = 0x000000;
            matrixStack.translate(-size, -4.5F, 0F);
            matrixStack.scale(textScale, textScale, textScale);
            modelViewMatrix = matrixStack.getLast()
                .getMatrix();
            mc.font.drawInBatch(name, 0, 0, white, false, modelViewMatrix, buffer, false, black, light);

            float s1 = 0.75F;
            matrixStack.pushPose();
            {
                matrixStack.scale(s1, s1, s1);
                modelViewMatrix = matrixStack.getLast()
                    .getMatrix();

                int h = NeatConfig.hpTextHeight;
                String maxHpStr = TextFormatting.BOLD + "" + Math.round(entity.getMaxHealth() * 100.0) / 100.0;
                String hpStr = "" + Math.round(health * 100.0) / 100.0;
                String percStr = (int) percent + "%";

                if (maxHpStr.endsWith(".00"))
                    maxHpStr = maxHpStr.substring(0, maxHpStr.length() - 3);
                if (hpStr.endsWith(".00"))
                    hpStr = hpStr.substring(0, hpStr.length() - 3);

                if (NeatConfig.showCurrentHP)
                    mc.font.drawInBatch(hpStr, 2, h, white, false, modelViewMatrix, buffer, false, black, light);
                if (NeatConfig.showMaxHP)
                    mc.font.drawInBatch(maxHpStr, (int) (size / (textScale * s1) * 2) - 2 - mc.font.getStringWidth(maxHpStr), h, white, false, modelViewMatrix, buffer, false, black, light);
                if (NeatConfig.showPercentage)
                    mc.font.drawInBatch(percStr, (int) (size / (textScale * s1)) - mc.font.getStringWidth(percStr) / 2, h, white, false, modelViewMatrix, buffer, false, black, light);
                if (NeatConfig.enableDebugInfo && mc.options.renderDebug)
                    mc.font.drawInBatch("ID: \"" + entity.getType()
                        .getRegistryName()
                        .toString() + "\"", 0, h + 16, white, false, modelViewMatrix, buffer, false, black, light);
            }
            matrixStack.popPose();

            matrixStack.pushPose();
            int off = 0;
            s1 = 0.5F;
            matrixStack.scale(s1, s1, s1);
            matrixStack.translate(size / (textScale * s1) * 2, 0F, 0F);
            mc.textureManager.bind(AtlasTexture.LOCATION_BLOCKS);
            if (NeatConfig.showAttributes) {
                renderIcon(mc, off, 0, icon, matrixStack, buffer, light);
                off -= 16;
            }

            matrixStack.pop();
        }
    }

    private void renderIcon(Minecraft mc, int vertexX, int vertexY, @Nonnull ItemStack icon, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light) {
        matrixStack.pushPose();
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-90));
        matrixStack.translate(vertexY - 16, vertexX - 16, 0.0D);
        matrixStack.scale(16.0F, 16.0F, 1.0F);
        try {
            ResourceLocation registryName = icon.getItem()
                .getRegistryName();
            Pair<ResourceLocation, ResourceLocation> pair = Pair.of(PlayerContainer.BLOCK_ATLAS, new ResourceLocation(registryName.getNamespace(), "item/" + registryName.getPath()));
            TextureAtlasSprite sprite = mc.getTextureAtlas(pair.getFirst())
                .apply(pair.getSecond());
            MatrixStack.Entry entry = matrixStack.getLast();
            Matrix4f modelViewMatrix = entry.pose();
            Vector3f normal = new Vector3f(0.0F, 1.0F, 0.0F);
            normal.transform(entry.normal());
            IVertexBuilder builder = buffer.getBuffer(NeatRenderType.getNoIconType());
            if (icon.isEmpty()) { //Wonky workaround to make text stay in position & make empty icon not rendering
                builder.vertex(modelViewMatrix, 0.0F, 0.0F, 0.0F)
                    .color(0, 0, 0, 0)
                    .endVertex();
                builder.vertex(modelViewMatrix, 0.0F, 1.0F, 0.0F)
                    .color(0, 0, 0, 0)
                    .endVertex();
                builder.vertex(modelViewMatrix, 1.0F, 1.0F, 0.0F)
                    .color(0, 0, 0, 0)
                    .endVertex();
                builder.vertex(modelViewMatrix, 1.0F, 0.0F, 0.0F)
                    .color(0, 0, 0, 0)
                    .endVertex();
            } else {
                builder = buffer.getBuffer(NeatRenderType.getHealthBarType(AtlasTexture.LOCATION_BLOCKS));
                builder.vertex(modelViewMatrix, 0.0F, 0.0F, 0.0F)
                    .uv(sprite.getU0(), sprite.getV1())
                    .color(255, 255, 255, 255)
                    .normal(normal.x(), normal.y(), normal.z())
                    .uv2(light)
                    .endVertex();
                builder.vertex(modelViewMatrix, 0.0F, 1.0F, 0.0F)
                    .uv(sprite.getU1(), sprite.getV1())
                    .color(255, 255, 255, 255)
                    .normal(normal.x(), normal.y(), normal.z())
                    .uv2(light)
                    .endVertex();
                builder.vertex(modelViewMatrix, 1.0F, 1.0F, 0.0F)
                    .uv(sprite.getU1(), sprite.getV0())
                    .color(255, 255, 255, 255)
                    .normal(normal.x(), normal.y(), normal.z())
                    .uv2(light)
                    .endVertex();
                builder.vertex(modelViewMatrix, 1.0F, 0.0F, 0.0F)
                    .uv(sprite.getU0(), sprite.getV0())
                    .color(255, 255, 255, 255)
                    .normal(normal.x(), normal.y(), normal.z())
                    .uv2(light)
                    .endVertex();
            }
            //Wonky workaround for making corner icons stay in position
            builder = buffer.getBuffer(NeatRenderType.getNoIconType());
            builder.vertex(modelViewMatrix, 0.0F, 0.0F, 0.0F)
                .color(0, 0, 0, 0)
                .endVertex();
            builder.vertex(modelViewMatrix, 0.0F, 1.0F, 0.0F)
                .color(0, 0, 0, 0)
                .endVertex();
            builder.vertex(modelViewMatrix, 1.0F, 1.0F, 0.0F)
                .color(0, 0, 0, 0)
                .endVertex();
            builder.vertex(modelViewMatrix, 1.0F, 0.0F, 0.0F)
                .color(0, 0, 0, 0)
                .endVertex();
        } catch (Exception ignored) {
            matrixStack.pop();
            return;
        }
        matrixStack.pop();
    }
    */

}