package com.gildedrose;

public class DefaultItemUpdater {

    public void update(Item item) {
        decreaseQuality(item, 1);
        item.setSellIn(item.getSellIn() - 1);

        if (item.getSellIn() < 0) {
            decreaseQuality(item, 1);
        }
    }

    protected void decreaseQuality(Item item, int amount) {
        item.setQuality(Math.max(0, item.getQuality() - amount));
    }

    protected void increaseQuality(Item item, int amount) {
        item.setQuality(Math.min(50, item.getQuality() + amount));
    }
}
