package me.hfox.craftbot.protocol.play.server.data;

import me.hfox.craftbot.nbt.Tag;

public class SlotData {

    private boolean present;
    private Integer id;
    private Byte count;
    private Tag nbt;

    public SlotData() {
        this(false, null, null, null);
    }

    public SlotData(Integer id, Byte count, Tag nbt) {
        this(true, id, count, nbt);
    }

    private SlotData(boolean present, Integer id, Byte count, Tag nbt) {
        this.present = present;
        this.id = id;
        this.count = count;
        this.nbt = nbt;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getCount() {
        return count;
    }

    public void setCount(Byte count) {
        this.count = count;
    }

    public Tag getNbt() {
        return nbt;
    }

    public void setNbt(Tag nbt) {
        this.nbt = nbt;
    }

}
