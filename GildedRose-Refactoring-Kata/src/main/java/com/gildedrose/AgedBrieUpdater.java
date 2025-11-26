package com.gildedrose;

public class AgedBrieUpdater extends DefaultItemUpdater {

    @Override
    public void update(Item item) {
        increaseQuality(item, 1);
        item.setSellIn(item.getSellIn() - 1);

        if (item.getSellIn() < 0) {
            increaseQuality(item, 1);
        }
    }
}

