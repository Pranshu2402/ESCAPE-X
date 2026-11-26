
# ESCAPE-X — Backend Integration Guide

**Developed by:** Member 5  
**Project:** ESCAPE-X

## 1. Overview

This guide explains how the other team members can connect their modules to the ESCAPE-X backend.

### Technologies Used
- Java 21
- SQLite Database
- JDBC
- Maven
- JUnit 5
- JavaFX

## 2. Backend Structure

The backend follows this package structure:

```text
com.escapex
├── database
│   └── DatabaseManager.java
├── models
│   ├── PlayerProfile.java
│   ├── GameProgress.java
│   └── LeaderboardEntry.java
└── services
    ├── PlayerService.java
    ├── GameProgressService.java
    ├── ScoreService.java
    └── LeaderboardService.java
```

## 3. Database Initialization

Before using any backend services, initialize the database.

```java
import com.escapex.database.DatabaseManager;

DatabaseManager.initializeDatabase();
```

The database is stored locally at:

```text
~/.escapex/escape_x.db
```

Two main database tables are created:

- `players` — stores player information.
- `game_progress` — stores game progress, score, time and inventory.

## 4. Player Registration

**Used by Member 4 — JavaFX UI**

Create a PlayerService object:

```java
import com.escapex.services.PlayerService;

PlayerService playerService = new PlayerService();
```

Register a new player:

```java
int playerId =
    playerService.registerPlayer("PlayerOne");
```

Retrieve a player's ID:

```java
int id = playerService.getPlayerId("PlayerOne");
```

Retrieve a player profile:

```java
var profile =
    playerService.getPlayerProfile("PlayerOne");

if (profile != null) {
    System.out.println(profile.getUsername());
    System.out.println(profile.getCreatedAt());
}
```

The player ID should be retained and passed to other backend services.

## 5. Saving and Loading Game Progress

**Used by Member 1 — Game Engine**

Create a GameProgressService object:

```java
import com.escapex.services.GameProgressService;
import com.escapex.models.GameProgress;

GameProgressService progressService =
    new GameProgressService();
```

Save game progress:

```java
GameProgress progress = new GameProgress(
    playerId,
    "Room 2",
    250,
    420,
    "[\"Key\", \"Map\"]"
);

progressService.saveProgress(progress);
```

The parameters represent:

1. Player ID
2. Current room
3. Score
4. Elapsed time in seconds
5. Inventory stored as a JSON string

Load saved progress:

```java
GameProgress saved =
    progressService.loadProgress(playerId);

if (saved != null) {
    System.out.println(saved.getCurrentRoom());
    System.out.println(saved.getScore());
    System.out.println(saved.getElapsedSeconds());
    System.out.println(saved.getInventoryJson());
}
```

If the player already has saved progress, calling `saveProgress()` updates their existing record.

If no saved progress exists, `loadProgress()` returns `null`.

## 6. Score Management

**Used by Member 2 — Puzzle System**

Create a ScoreService object:

```java
import com.escapex.services.ScoreService;

ScoreService scoreService = new ScoreService();
```

Award points for solving a puzzle:

```java
int newScore =
    scoreService.awardPuzzlePoints(100);
```

Apply a hint penalty:

```java
int newScore =
    scoreService.applyHintPenalty(100);
```

Apply an incorrect-answer penalty:

```java
int newScore =
    scoreService.applyWrongAttemptPenalty(100);
```

Add bonus points:

```java
int newScore =
    scoreService.addBonusPoints(100, 50);
```

Update and save a player's score in the database:

```java
int updatedScore =
    scoreService.updatePlayerScore(playerId, 100);
```

### Current Scoring Rules

| Action | Points |
|---|---:|
| Solve puzzle | +100 |
| Use hint | -20 |
| Incorrect answer | -10 |
| Bonus | Depends on bonus |

The penalty methods prevent scores from falling below zero.

## 7. Inventory Persistence

**Used by Member 3 — Inventory System**

The backend stores inventory as a JSON-formatted string.

Example:

```java
String inventory =
    "[\"Key\", \"Map\", \"Code\"]";
```

Save inventory through GameProgress:

```java
GameProgress progress = new GameProgress(
    playerId,
    "Room 2",
    250,
    420,
    inventory
);

progressService.saveProgress(progress);
```

Retrieve saved inventory:

```java
String savedInventory =
    saved.getInventoryJson();
```

The inventory module is responsible for converting this JSON data into its inventory objects.

## 8. Leaderboard

**Used by Member 4 — JavaFX UI**

Create a LeaderboardService object:

```java
import com.escapex.services.LeaderboardService;

LeaderboardService leaderboardService =
    new LeaderboardService();
```

Retrieve the top 10 players:

```java
var leaderboard =
    leaderboardService.getLeaderboard(10);

for (var entry : leaderboard) {

    System.out.println(
        entry.getUsername() + " - " +
        entry.getScore()
    );
}
```

### Leaderboard Ranking

Players are ranked by:

1. Highest score first.
2. If scores are equal, the lower elapsed time ranks higher.

## 9. Important Integration Rules

- Use the existing `com.escapex` package structure.
- Do not create duplicate database or service classes.
- Use the shared Maven project.
- Use `playerId` to identify players across modules.
- Handle `SQLException` when calling database services.
- A `null` result from `loadProgress()` means no saved game exists.
- A `null` result from `getPlayerProfile()` means the player was not found.
- Inventory must be supplied as a JSON-formatted string.
- Do not store passwords in plaintext if authentication is added later.

## 10. Testing

Run the backend test suite from the project root:

```powershell
mvn clean test
```

The backend currently has 21 automated tests covering:

- Player registration and profile retrieval
- Duplicate and invalid usernames
- Database foreign-key enforcement
- Saving and loading game progress
- Save and resume functionality
- Leaderboard ranking
- Score calculations and penalties

All tests should pass before integration.

---

**Backend Module — Member 5**

**ESCAPE-X Java Desktop Application**
