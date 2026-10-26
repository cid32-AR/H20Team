# Phase 1 Chess Project Plan

## Deadline: Sunday, October 25, 2026, at 11:59 PM Central

## Team responsibilities

| Member | Role | Assigned work |
| --- | --- | --- |
| Andres Repository owner and |   | Manage main and dev; create project structure; |
|   | core-model developer | implement Color, Position, abstract Piece, and |
|   | Player |   |
| Ben | Piece developer 1 | Implement Pawn, Rook, and Knight |
| Alex | Piece developer 2 and | Implement Bishop, Queen, King, and input parsing |
|   | input developer |   |
| John | Board and integration | Implement Board, Game, Main; integrate the project; coordinate testing and README |
|   | developer |   |

## Git branches

Andres manages:

main

dev

Personal branches:

feature/cid32-AR

feature/<ben-github-username>


feature/<alex-github-username>

feature/<john-github-username>

Because Andres’s GitHub username appears to be cid32-AR, his branch should be:

feature/cid32-AR

Use Ben’s, Alex’s, and John’s exact GitHub usernames for their branches.

# Andres — Repository and core model

Files:

docs/PHASE1_PLAN.md

src/model/Color.java

src/model/Position.java

src/pieces/Piece.java

src/game/Player.java

Responsibilities:

- Create and push dev.

- Commit the team plan before Java coding begins.

- Create the package structure.

- Implement Color with WHITE and BLACK.

- Implement chess-position conversion such as E2.

- Implement the abstract Piece superclass.

- Implement basic player information.

- Define the shared method signatures everyone will use.

- Manage approved merges into dev.

- Open the final pull request from dev into main.

Suggested commits:

Add Phase 1 development plan

Create Java package structure

Implement Color and Position classes


## Implement abstract Piece and Player classes

## Ben — Pawn, Rook, and Knight

Files:

src/pieces/Pawn.java

src/pieces/Rook.java

src/pieces/Knight.java

Responsibilities:

- Extend the shared Piece class.

- Implement each piece’s symbol.

- Implement its movement-generation behavior.

- Add complete Javadocs.

- Manually test movement from center and edge squares.

Suggested commits:

Implement Pawn movement and documentation

Implement Rook movement and documentation

Implement Knight movement and documentation

## Alex — Bishop, Queen, King, and input

Files:

src/pieces/Bishop.java

src/pieces/Queen.java

src/pieces/King.java

src/utils/InputParser.java

Responsibilities:

- Implement the three piece subclasses.

- Add symbols and movement behavior.

- Parse move input such as E2 E4.


- Accept lowercase and extra spaces.

- Reject malformed input without crashing.

- Add complete Javadocs.

Suggested commits:

Implement Bishop movement and documentation

Implement Queen and King movement

Add flexible command-line move parser

## John — Board, game, and integration

Files:

src/board/Board.java

src/game/Game.java

src/Main.java

README.md

Responsibilities:

- Create the 8×8 Piece[][] board.

- Place all 32 pieces correctly.

- Display ranks, files, symbols, and empty squares.

- Implement basic board movement operations.

- Create the console game loop.

- Alternate white and black turns.

- Reject:

- Out-of-bounds positions

- Empty starting squares

- Moving the wrong color

- Capturing a friendly piece

- Connect the complete project.

- Coordinate final testing.

- Coordinate the README, with contributions from everyone.

Suggested commits:

Create board initialization and display

Add board movement validation


Implement game loop and turn tracking

Integrate project and update README

## Four milestones for docs/PHASE1_PLAN.md

## Milestone 1: Project setup and core model

Implementation tasks:

- Create packages.

- Create Color, Position, Piece, and Player.

- Establish shared constructors and method signatures.

- Create compilable skeletons.

## Manual tests:

- Compile the project.

- Convert E2 and A8 to board coordinates.

- Create sample white and black pieces.

- Confirm invalid positions are rejected.

## Milestone 2: Chess-piece classes

Implementation tasks:

- Implement all six piece subclasses.

- Add symbols and movement-generation methods.

- Add Javadocs.

## Manual tests:

- Test each piece from a center square.

- Test each piece near an edge.

- Confirm each piece has the correct color and symbol.

## Milestone 3: Board setup and display

Implementation tasks:

- Create the 8×8 board.

- Place all pieces correctly.

- Add coordinate labels and readable output.

- Add basic board operations.


Manual tests:

- Confirm the board contains 32 pieces.

- Verify the king and queen positions.

- Verify white and black pawn rows.

- Move a piece and display the updated board.

## Milestone 4: CLI workflow and validation

Implementation tasks:

- Create the input loop.

- Parse user moves.

- Alternate turns.

- Add basic move-legality checks.

- Complete the README and final documentation.

Manual tests:

E2 E4

e2 e4

E2 E4

E9 E4

hello

E3 E4

E7 E6 during White's turn

E1 D1 when D1 contains a friendly piece

The program should provide an error message and continue running after invalid input.

## Required pull-request process

- 1. Andres creates dev. 2. Andres commits docs/PHASE1_PLAN.md. 3. Everyone creates their personal feature branch from dev. 4. Andres completes the shared foundation. 5. Andres opens a PR from feature/cid32-AR into dev.

- 6. Another member reviews it.


- 7. Andres merges the approved PR.

- 8. Everyone pulls the updated dev into their feature branch.

- 9. Ben, Alex, and John complete their work.

- 10. Each person opens at least one PR into dev.

- 11. Each person reviews at least one teammate’s PR.

- 12. Andres merges approved PRs into dev.

- 13. The team tests the integrated program on dev.

- 14. Andres opens the final PR from dev into main.

- 15. A teammate reviews it before Andres merges it.

- 16. Keep all remote branches until grading is finished.

## Review assignments

Pull request

Andres’s core-model

PR

Ben’s piece PR

Alex’s piece/input PR

John’s board/game PR Andres

Final dev → main PR Any teammate who did not create it

Reviewer

John

Alex

Ben

## AI records

Each member creates:

docs/ai-usage/<github-username>.md

Andres’s file:


docs/ai-usage/cid32-AR.md

Each entry should include:

\### Date or task

- \- Asked:

- \- Used or changed:

- \- Result or feedback:

- \- AI created the commit: yes/no

The only remaining information needed for exact branch names is Ben’s, Alex’s, and John’s

GitHub usernames.
