/*
 * This class was found at https://5balloons.info/consuming-rest-api-in-java-spring-through-resttemplate/
 * It uses Jackson to convert the JSON from Google Book Api into POJO classes
 * access the above link for more details
 */

package com.talkAboutThisBook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GBItemsWrapper {

    private GBVolumeInfoWrapper volumeInfo;

    private String id;

    private int index;

    public GBVolumeInfoWrapper getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(GBVolumeInfoWrapper volumeInfo) {
        this.volumeInfo = volumeInfo;
        this.volumeInfo.setId(getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        this.volumeInfo.setIndex(index);
    }
}
