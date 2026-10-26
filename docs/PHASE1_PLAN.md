# Phase 1 Chess Project Plan

## Team Members and Branches

- Andres — Repository owner and core model  
  Branch: `feature/cid32-AR`

- Ben — Pawn, Rook, and Knight  
  Branch: `feature/<Ben-GitHub-username>`

- Alex — Bishop, Queen, King, and input parsing  
  Branch: `feature/<Alex-GitHub-username>`

- John — Board, game flow, and integration  
  Branch: `feature/JonMekk`

All personal feature branches will be created from `dev`. Each member will open at least one pull request from their feature branch into `dev`. The final pull request will go from `dev` into `main`.

## Milestone 1: Project Setup and Core Model

### Implementation Tasks

- Create the Java package structure.
- Create the `Color` enum.
- Create the `Position` class.
- Create the abstract `Piece` class.
- Create the `Player` class.
- Establish shared constructors and method signatures.
- Add Javadocs to classes, attributes, constructors, and methods.

### Manual Tests

- Compile all Java class skeletons.
- Convert positions such as `E2` and `A8` into board coordinates.
- Reject invalid positions such as `E9`.
- Create sample white and black pieces.

## Milestone 2: Chess Piece Classes

### Implementation Tasks

- Implement `Pawn`.
- Implement `Rook`.
- Implement `Knight`.
- Implement `Bishop`.
- Implement `Queen`.
- Implement `King`.
- Give every piece its correct symbol and movement behavior.
- Add Javadocs to every piece class.

### Manual Tests

- Test every piece from a center square.
- Test every piece near an edge or corner.
- Confirm every class extends `Piece`.
- Confirm every piece has the correct color and symbol.

## Milestone 3: Board Setup and Display

### Implementation Tasks

- Create an 8x8 `Piece[][]` board.
- Place all 32 pieces in their correct starting positions.
- Display files A-H and ranks 1-8.
- Display piece symbols and empty squares clearly.
- Implement methods to retrieve and move pieces.

### Manual Tests

- Confirm the board contains 32 pieces.
- Verify the starting positions of all kings and queens.
- Verify the white and black pawn rows.
- Move a piece and display the updated board.
- Confirm the original square becomes empty.

## Milestone 4: CLI Workflow and Basic Validation

### Implementation Tasks

- Create the console input loop.
- Parse moves such as `E2 E4`.
- Accept lowercase input and additional spaces.
- Track the current player.
- Alternate between white and black turns.
- Reject out-of-bounds positions.
- Reject moves from an empty square.
- Reject moving the wrong color.
- Reject capturing a friendly piece.
- Continue running after invalid input.
- Complete the README and final documentation.

### Manual Tests

- Enter `E2 E4` and confirm the move succeeds.
- Enter `e2 e4` and confirm lowercase input is accepted.
- Enter input containing additional spaces.
- Enter `E9 E4` and confirm it is rejected.
- Enter `hello` and confirm the program does not crash.
- Try moving from an empty square.
- Try moving a black piece during White's turn.
- Try capturing a piece of the same color.
- Confirm the turn changes only after a valid move.

## Git and Collaboration Workflow

1. Andres manages the `main` and `dev` branches.
2. Each member works on one `feature/<github-username>` branch.
3. Each member makes multiple meaningful commits.
4. Each member opens at least one pull request into `dev`.
5. Each member reviews at least one teammate's pull request.
6. Approved work is merged into `dev`.
7. The complete program is tested on `dev`.
8. A final pull request is opened from `dev` into `main`.
9. All remote branches will remain available until grading is complete.

## AI Usage

Each member will maintain:

`docs/ai-usage/<github-username>.md`

Each AI-use entry will record:

- What the member asked
- What suggestions were used or changed
- The result or feedback
- Whether AI created the commit

All AI-generated work will be reviewed and understood by the team member before it is committed.
