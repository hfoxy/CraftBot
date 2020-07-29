package me.hfox.craftbot.entity.data;

public class VillagerData {

    private VillagerType type;
    private VillagerProfession profession;
    private int level;

    public VillagerData(VillagerType type, VillagerProfession profession, int level) {
        this.type = type;
        this.profession = profession;
        this.level = level;
    }

    public VillagerType getType() {
        return type;
    }

    public void setType(VillagerType type) {
        this.type = type;
    }

    public VillagerProfession getProfession() {
        return profession;
    }

    public void setProfession(VillagerProfession profession) {
        this.profession = profession;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
