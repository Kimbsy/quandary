package com.kimbsy.quandary.sprite;

import com.kimbsy.sim.sprite.BaseSprite;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author kimbsy
 */
public class Modal extends BaseSprite {

    public enum ModalType {
        SKIP_TURN,
        DISPLAY_WINNER,
    }

    private static final Font FONT = new Font("Courier", Font.BOLD, 15);
    private static final int GROUP_OFFSET = 15;

    private final ModalType modalType;
    private int width, height;
    private String bodyText;
    private ArrayList<String> options;

    public Modal(ModalType modalType, Point pos, int width, int height, String bodyText, ArrayList<String> options) {
        super(pos);
        this.modalType = modalType;
        this.width = width;
        this.height = height;
        this.bodyText = bodyText;
        this.options = options;
    }

    public ModalType getModalType() {
        return modalType;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(getPos().x + 3, getPos().y + 3, width, height);
        g2d.setColor(Color.GRAY);
        g2d.fillRect(getPos().x, getPos().y, width, height);

        FontMetrics fontMetrics = g2d.getFontMetrics(FONT);

        int bodyTextOffset = (width - fontMetrics.stringWidth(bodyText)) / 2;

        g2d.setColor(Color.BLACK);
        g2d.setFont(FONT);
        g2d.drawString(bodyText, getPos().x + bodyTextOffset, getPos().y + 40);

        int buttonCount = options.size();
        int buttonWidth = width / buttonCount - 30;
        int buttonOffset = buttonWidth + 30;

        int i = 0;
        for (String option : options) {
            int optionX = getPos().x + GROUP_OFFSET + buttonOffset * i;
            int optionY = getPos().y + 80;
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect(optionX + 3, optionY + 3, buttonWidth, 30);
            g2d.setColor(Color.WHITE);
            g2d.fillRect(optionX, optionY, buttonWidth, 30);

            int optionOffsetX = (buttonWidth - fontMetrics.stringWidth(option)) / 2;
            g2d.setColor(Color.BLACK);
            g2d.setFont(FONT);
            g2d.drawString(option, optionX + optionOffsetX, optionY + 20);
            i++;
        }
    }

    public int getChoice(Point pos) {
        int buttonCount = options.size();
        int buttonWidth = width / buttonCount - 30;

        for (int i = 0; i < buttonCount; i++) {
            int x = getPos().x + GROUP_OFFSET + (buttonWidth + 30) * i;
            int y = getPos().y + 80;
            int w = width / buttonCount - 30;
            int h = 30;

            if (new Rectangle(x, y, w, h).contains(pos)) {
                return i;
            }
        }

        return -1;
    }
}
