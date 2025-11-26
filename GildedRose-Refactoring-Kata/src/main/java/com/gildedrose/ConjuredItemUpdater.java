package com.gildedrose;

public class ConjuredItemUpdater extends DefaultItemUpdater {

    @Override
    public void update(Item item) {
        decreaseQuality(item, 2);
        item.setSellIn(item.getSellIn() - 1);

        if (item.getSellIn() < 0) {
            decreaseQuality(item, 2);
        }
    }
}

