# H20Team

CS 3354 Chess Project

**Team:** Andres, Ben, Alex, Jon

## Phase 2: Java Swing Chess

Phase 2 adds a graphical chessboard for two players on the same computer.

- Move pieces by clicking two squares or dragging a piece to its destination.
- White moves first. Turns alternate after each successful move.
- The game shows a winner when a player captures the opposing king.
- The side panel shows move history and captured pieces.
- **Undo Last Move** restores the board and turn from before the last successful move.
- Open **Settings** to change the board colors, square size, and piece style. Contrasting outlines keep pieces visible on custom board colors.

## Run the Game (Windows PowerShell)

You need a Java Development Kit (JDK) with `java` and `javac` available in your terminal.

### 1. Clone the Phase 2 branch

```powershell
git clone --branch phase2 https://github.com/cid32-AR/H20Team.git
cd H20Team
```

If you already have the repository, open the `H20Team` folder and run `git switch phase2`.

### 2. Compile

Run these commands from the `H20Team` folder:

```powershell
$javaFiles = (Get-ChildItem -Path .\src -Recurse -Filter *.java -File).FullName
javac -encoding UTF-8 -d out $javaFiles
```

### 3. Launch the GUI

```powershell
java -cp out gui.ChessMainWindow
```

In VS Code, you can also open `src/gui/ChessMainWindow.java` and click **Run** above the `main` method.

## How to Play

1. Click a piece and then click its destination. You can also drag the piece to its destination.
2. Check the **Move History** and **Captured Pieces** panels on the right.
3. Click **Undo Last Move** to take back the last successful move.
4. Capture the opposing king to win.

## Phase 1: Console Chess

To run the console version after compiling:

```powershell
java -cp out Main
```

Enter a move as two squares separated by a space, such as `E2 E4`. The console version checks basic piece movement, blocks sliding pieces when another piece is in their path, and prevents players from capturing their own pieces. Enter `quit` to exit.