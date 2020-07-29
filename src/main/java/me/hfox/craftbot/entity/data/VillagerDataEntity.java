package me.hfox.craftbot.entity.data;

public interface VillagerDataEntity {

    VillagerData getData();

    void setData(VillagerData data);

    default VillagerType getType() {
        return getData().getType();
    }

    default void setType(VillagerType type) {
        getData().setType(type);
    }

    default VillagerProfession getProfession() {
        return getData().getProfession();
    }

    default void setProfession(VillagerProfession profession) {
        getData().setProfession(profession);
    }

    default int getLevel() {
        return getData().getLevel();
    }

    default void setLevel(int level) {
        getData().setLevel(level);
    }

}
