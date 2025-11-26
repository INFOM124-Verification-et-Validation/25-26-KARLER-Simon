package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].getName());
    }

    @Test
    void normalItemQualityDegradesBy1() {
        Item[] items = { new Item("Elixir of the Mongoose", 5, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(9, items[0].getQuality());
        assertEquals(4, items[0].getSellIn());
    }

    @Test
    void normalItemQualityDegradesTwice() {
        Item[] items = { new Item("Elixir of the Mongoose", 0, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(8, items[0].getQuality());
        assertEquals(-1, items[0].getSellIn());
    }

    @Test
    void normalItemQualityNegative() {
        Item[] items = { new Item("Elixir of the Mongoose", 5, 0) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(0, items[0].getQuality());
    }

    @Test
    void agedBrieIncreasesQuality() {
        Item[] items = { new Item("Aged Brie", 5, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(11, items[0].getQuality());
        assertEquals(4, items[0].getSellIn());
    }

    @Test
    void agedBrieIncreasesTwice() {
        Item[] items = { new Item("Aged Brie", 0, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(12, items[0].getQuality());
        assertEquals(-1, items[0].getSellIn());
    }

    @Test
    void agedBrieQualityNever51() {
        Item[] items = { new Item("Aged Brie", 5, 50) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(50, items[0].getQuality());
    }

    @Test
    void sulfurasNeverChanges() {
        Item[] items = { new Item("Sulfuras, Hand of Ragnaros", 5, 80) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(80, items[0].getQuality());
        assertEquals(5, items[0].getSellIn());
    }

    @Test
    void sulfurasNeverChangesExpired() {
        Item[] items = { new Item("Sulfuras, Hand of Ragnaros", -1, 80) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(80, items[0].getQuality());
        assertEquals(-1, items[0].getSellIn());
    }

    @Test
    void conjuredDegradesTwice() {
        Item[] items = { new Item("Conjured Mana Cake", 5, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(8, items[0].getQuality()); // -2
        assertEquals(4, items[0].getSellIn());
    }

    @Test
    void conjuredDegradesFourTimes() {
        Item[] items = { new Item("Conjured Mana Cake", 0, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(6, items[0].getQuality()); // -4
        assertEquals(-1, items[0].getSellIn());
    }

    @Test
    void conjuredQualityNegative() {
        Item[] items = { new Item("Conjured Mana Cake", 5, 1) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(0, items[0].getQuality());
    }

    @Test
    void backstageIncreasesBy1Above10Days() {
        Item[] items = { new Item("Backstage passes", 15, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(11, items[0].getQuality());
    }

    @Test
    void backstageIncreasesBy2At10Days() {
        Item[] items = { new Item("Backstage passes", 10, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(12, items[0].getQuality());
    }

    @Test
    void backstageIncreasesBy3At5Days() {
        Item[] items = { new Item("Backstage passes", 5, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(13, items[0].getQuality());
    }

    @Test
    void backstageQualityDropsTo0AfterConcert() {
        Item[] items = { new Item("Backstage passes", 0, 30) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(0, items[0].getQuality());
    }

}
