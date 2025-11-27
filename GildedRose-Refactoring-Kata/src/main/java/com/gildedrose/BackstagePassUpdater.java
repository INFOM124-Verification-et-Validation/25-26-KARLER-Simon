package com.gildedrose;

public class BackstagePassUpdater extends DefaultItemUpdater {

    @Override
    public void update(Item item) {
        increaseQuality(item, 1);

        if (item.getSellIn() <= 10) {
            increaseQuality(item, 1);
        }
        if (item.getSellIn() <= 5) {
            increaseQuality(item, 1);
        }

        item.setSellIn(item.getSellIn() - 1);

        if (item.getSellIn() < 0) {
            item.setQuality(0);
        }
    }
}

