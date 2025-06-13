package com.project.travelquest.avatar.dto;

import lombok.Data;

public class AvatarPresetDto {
    private String skin;
    private String face;
    private String hands;
    private String bottoms;
    private String tops;
    private String hats;

    // Getters & Setters
    public String getSkin() { return skin; }
    public void setSkin(String skin) { this.skin = skin; }

    public String getFace() { return face; }
    public void setFace(String face) { this.face = face; }

    public String getHands() { return hands; }
    public void setHands(String hands) { this.hands = hands; }

    public String getBottoms() { return bottoms; }
    public void setBottoms(String bottoms) { this.bottoms = bottoms; }

    public String getTops() { return tops; }
    public void setTops(String tops) { this.tops = tops; }

    public String getHats() { return hats; }
    public void setHats(String hats) { this.hats = hats; }
}
