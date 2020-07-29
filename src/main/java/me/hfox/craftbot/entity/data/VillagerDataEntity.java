package me.hfox.craftbot.entity.data;

public interface VillagerDataEntity {

    VillagerData getData();

    void setData(VillagerData data);

    default int getType() {
        return getData().getType();
    }

    default void setType(int type) {
        getData().setType(type);
    }

    default int getProfession() {
        return getData().getType();
    }

    default void setProfession(int profession) {
        getData().setProfession(profession);
    }

    default int getLevel() {
        return getData().getType();
    }

    default void setLevel(int level) {
        getData().setLevel(level);
    }

}
