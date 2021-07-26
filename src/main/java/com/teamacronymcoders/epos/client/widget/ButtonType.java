package com.teamacronymcoders.epos.client.widget;

public enum ButtonType {
    SKINNY(57, 16, 198, 0, 198, 16),
    THICK(52, 24, 198, 32, 198, 56);

    private int width;
    private int height;
    private int u;
    private int v;
    private int activeU;
    private int activeV;

    ButtonType(int width, int height, int u, int v, int activeU, int activeV) {
        this.width = width;
        this.height = height;
        this.u = u;
        this.v = v;
        this.activeU = activeU;
        this.activeV = activeV;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getU(boolean isHovered) {
        return isHovered ? this.activeU : this.u;
    }

    public int getV(boolean isHovered) {
        return isHovered ? this.activeV : this.v;
    }
}
