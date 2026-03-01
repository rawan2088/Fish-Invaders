# 🐠 Fish Invaders

A fun twist on the classic Space Invaders arcade game, built entirely in Java. Instead of alien invaders, you face waves of colourful fish. Survive as long as you can!

(This was made for Assignment 1 in the software course)

## 🎮 How to Play

- **Left Arrow** — move your ship left
- **Right Arrow** — move your ship right
- **Spacebar** — shoot
- Destroy all the fish before they reach the bottom to survive
- Each wave gets harder — more fish, faster movement
- If any fish reaches your ship, it's game over

---

## 🏆 Scoring

| Action       | Points                   |
| ------------ | ------------------------ |
| Kill a fish  | 100                      |
| Clear a wave | Bonus based on wave size |

---

## 🔧 Requirements

- Java 14 or higher

To check if you have Java installed, open a terminal and run:

```
java --version
```

---

## 🚀 Running the Game

### Option A: Run the .exe (Windows)

1. Download `FishInvaders.exe`
2. Double-click to run
3. If Windows SmartScreen appears, click **More Info** → **Run Anyway**

### Option B: Run from Source

1. Clone or download this repository
2. Compile:

```
javac FishInvaders.java
```

3. Run:

```
java FishInvaders
```

---

## 📁 Project Structure

```
FishInvaders/
├── FishInvaders.java
├── App.java
└── assets/
    ├── Pink.png
    ├── Blue fish.png
    ├── White fish.png
    ├── Brown fish.png
    ├── Pink fish.png
    └── Sound/
        ├── Juhani Junkala [Retro Game Music Pack] Title Screen.wav
        ├── laser-one-shot-2.wav
        ├── explosion-sound-4.wav
        └── game-die.wav
```

---

## 🎨 Assets

All game sprites were hand-crafted by me in **[LibreSprite](https://libresprite.github.io/)**, a free and open source pixel art editor.

- 🚀 Player ship
- 🐟 Blue fish
- 🐟 White fish
- 🐟 Brown fish
- 🐟 Pink fish

---

## 🔊 Sound

- Background music — _Juhani Junkala Retro Game Music Pack_ (free for use)
- Laser, explosion, and game over sounds sourced from [freesound.org](https://freesound.org)

---

## 🛠️ Built With

- Java
- Java Swing (rendering and UI)
- javax.sound.sampled (audio)
- LibreSprite (pixel art assets)

---

_Made with ❤️ in Java_
