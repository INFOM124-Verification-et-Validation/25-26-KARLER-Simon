package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (item.getName().equals("Aged Brie")) {
                new AgedBrieUpdater().update(item);
            } else if (item.getName().startsWith("Backstage passes")) {
                new BackstagePassUpdater().update(item);
            } else if (item.getName().equals("Sulfuras, Hand of Ragnaros")) {
                new SulfurasUpdater().update(item);
            } else if (item.getName().startsWith("Conjured")) {
                new ConjuredItemUpdater().update(item);
            } else {
                new DefaultItemUpdater().update(item);
            }
        }
    }

}
