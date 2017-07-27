package com.kimbsy.quandary.sprite;

import com.kimbsy.sim.sprite.BaseSprite;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class displays a message box overlay on top of the board with options to click on.
 *
 * @author kimbsy
 */
public class Modal extends BaseSprite {

    /**
     * The different types of Modal.
     */
    public enum ModalType {
        SKIP_TURN,
        END_GAME,
    }

    private static final Font FONT = new Font("Courier", Font.BOLD, 15);
    private static final int GROUP_OFFSET = 15;

    private final ModalType modalType;
    private int width, height;
    private String bodyText;
    private ArrayList<String> options;

    /**
     * Class constructor specifying the Modal type, position, dimensions, body text and list of option button strings.
     *
     * @param modalType The type of the Modal.
     * @param pos       The position of the Modal.
     * @param width     The width of the Modal frame.
     * @param height    The height of the Modal frame.
     * @param bodyText  The text displayed in the Modal body.
     * @param options   The list of Strings displayed in the Modal option buttons.
     */
    public Modal(ModalType modalType, Point pos, int width, int height, String bodyText, ArrayList<String> options) {
        super(pos);
        this.modalType = modalType;
        this.width = width;
        this.height = height;
        this.bodyText = bodyText;
        this.options = options;
    }

    /**
     * Get the type of the Modal.
     *
     * @return The Modal type.
     */
    public ModalType getModalType() {
        return modalType;
    }

    /**
     * Set the body text of the Modal.
     *
     * @param bodyText The new body text.
     */
    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    @Override
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

    /**
     * Determine which option button was clicked on.
     *
     * @param pos The position of the mouse click event.
     * @return The index of the option selected.
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Modal modal = (Modal) o;

        if (width != modal.width) return false;
        if (height != modal.height) return false;
        if (modalType != modal.modalType) return false;
        if (bodyText != null ? !bodyText.equals(modal.bodyText) : modal.bodyText != null) return false;
        return options != null ? options.equals(modal.options) : modal.options == null;
    }

    @Override
    public int hashCode() {
        int result = modalType != null ? modalType.hashCode() : 0;
        result = 31 * result + width;
        result = 31 * result + height;
        result = 31 * result + (bodyText != null ? bodyText.hashCode() : 0);
        result = 31 * result + (options != null ? options.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("modalType", modalType)
                .append("width", width)
                .append("height", height)
                .append("bodyText", bodyText)
                .append("options", options)
                .toString();
    }
}
