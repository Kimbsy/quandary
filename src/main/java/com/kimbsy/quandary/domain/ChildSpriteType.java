package com.kimbsy.quandary.domain;

import com.kimbsy.quandary.sprite.QuandaryBoard;
import com.kimbsy.sim.sprite.Sprite;

/**
 * This enum lists the types of {@link Sprite} objects that can be children of the {@link QuandaryBoard}.
 *
 * @author kimbsy
 */
public enum ChildSpriteType {
    SQUARE,
    HIGHLIGHT,
    PAWN,
}
