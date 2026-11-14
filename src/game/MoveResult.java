package game;

/** Describes what happened when a player tried to move. */
public enum MoveResult {
    MOVED,
    CAPTURED,
    KING_CAPTURED,
    INVALID_MOVE,
    GAME_OVER
}