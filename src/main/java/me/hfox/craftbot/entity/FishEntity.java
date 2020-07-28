package me.hfox.craftbot.entity;

public interface FishEntity extends WaterAnimalEntity {

    boolean isFromBucket();

    void setFromBucket(boolean fromBucket);

}
