# SKE Snake Ladder

## Which knowledge from GRASP were applied ?

### 1. Creator
  - BoardView creates Board
  - Player creates Piece

### 2. Information Expert
  - Board knows about Square object.
  - Player knows about Piece object.
  - Game knows about the turn because it knows about all of players.

### 3. Controller
  - Game should be connected to UI layer (GameActivity) because from sequence diagram UI is never touch models directly. It's only touch Game.
  - Board should be connected to UI layer (BoardView) because BoardView is never touch Square directly. It's only touch Board.

### 4. Polymorphism
  - AbstractDie in an abstract-class for type of Die. And DieCup just knows all type of die in only AbstractDie.

### 5. Protected variations
  - AbstractDie is protects Die's information by using abstract-class.

### 6. Low coupling
  - GameActivity send number of players to Game and then Game create the Player. When Played has changed then it will not impact to GameActivity.
  
### 7. High cohesion (Focus in the thing that it shoudl doing)
  - Die (roll)
  - Piece (set and get position)

----

## Sequence Diagram

### 1. take turn (play)
![Alt text](http://taweesoft.xyz/work/soft_spec/images/Soft_Spec_SKE_Snake_SD1.png)

### 2. reset
![Alt text](http://taweesoft.xyz/work/soft_spec/images/Soft_Spec_SKE_Snake_SD2.png)
