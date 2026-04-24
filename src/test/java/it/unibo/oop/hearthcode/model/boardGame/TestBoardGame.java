package it.unibo.oop.hearthcode.model.boardgame;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;
import it.unibo.oop.hearthcode.model.boardgame.impl.BoardGameFactory;

final class TestBoardGame {
    private BoardGame board;

    @BeforeEach
    void initTest() {
        this.board = BoardGameFactory.createDefaultGame();
    }

    @Test
    void testException() {
        try {
            this.board.startGame();
        } catch (final IllegalStateException e) {
            fail("Exception was collected");
        }
    }
}
