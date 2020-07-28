package me.hfox.craftbot.entity;

public class VillagerData {

    private int type;
    private int profession;
    private int level;

    public VillagerData(int type, int profession, int level) {
        this.type = type;
        this.profession = profession;
        this.level = level;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getProfession() {
        return profession;
    }

    public void setProfession(int profession) {
        this.profession = profession;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
