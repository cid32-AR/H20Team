# H20Team
CS3354 Project

Team: Andres, Ben, Alex, Jon

---

## Scope (Phase 1)

**Implemented:**
- All six piece movements (pawn, knight, bishop, rook, queen, king)
- Turn alternation (white → black → white → …)
- Board rendering in the console
- Input parsing and validation
- Friendly-fire prevention (cannot capture your own piece)
- Path blocking for sliding pieces (bishop, rook, queen)

**Not implemented (by design in this phase):**
- Check, checkmate, stalemate
- Castling
- En passant
- Pawn promotion

The game ends only when a player quits the process — it does **not** declare a
winner.

---

# 1. Clone and enter the project
```bat
git clone https://github.com/cid32-AR/H20Team.git
```
```bat
cd H20Team
```
# 2. Compile (from project root, where src/ lives)
```bat
mkdir out
```
```bat
dir /s /b src\*.java > sources.txt
```
```bat
javac -d out @sources.txt
```
```bat
del sources.txt
```

# 3. Run (replace Main with your entry class — see below)
```bat
java -cp out Main
```

---

## How To Play:
- Move format: <from> <to> — two squares separated by whitespace.

- Squares: file A–H, rank 1–8. Case-insensitive (e2 e4 = E2 E4).

- Turns alternate automatically (white, black, white, …).

- Board symbols: wp bp wN bN wB bB wR bR wQ bQ wK bK.

- Coordinate convention (internal): column A=0 … H=7; rank 1=row 0 … 8=row 7.

- To quit: Ctrl+C (or close the terminal).

---
