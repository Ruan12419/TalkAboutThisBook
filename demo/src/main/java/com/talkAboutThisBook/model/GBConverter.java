/*
 * This class was found at https://5balloons.info/consuming-rest-api-in-java-spring-through-resttemplate/
 * It uses Jackson to convert the JSON from Google Book Api into POJO classes
 * access the above link for more details
 */

package com.talkAboutThisBook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GBConverter {
    private int totalItems;
    private GBItemsWrapper[] items;

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public GBItemsWrapper[] getItems() {
        return items;
    }

    public void setItems(GBItemsWrapper[] items) {
        this.items = items;
    }
}
