package it.unibo.oop.hearthcode.model.ai.executor.impl;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.ai.action.impl.AttackCardAction;
import it.unibo.oop.hearthcode.model.ai.action.impl.AttackHeroAction;
import it.unibo.oop.hearthcode.model.ai.action.impl.PlayCardAction;
import it.unibo.oop.hearthcode.model.ai.executor.api.AiActionExecutor;
import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;

/**
 * Implementation of {@link AiActionExecutor}.
 */
public final class AiActionExecutorImpl implements AiActionExecutor {

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final BoardGame game, final AiAction action) {
        if (action instanceof PlayCardAction play) {
            game.place(play.cardId());
            return;
        }
        if (action instanceof AttackHeroAction attackHero) {
            game.attackHero(attackHero.attackerId());
            return;
        }
        if (action instanceof AttackCardAction attackCard) {
            game.attackCard(
                attackCard.attackerId(),
                attackCard.defenderId()
            );
        }
    }

}
