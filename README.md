# Whitespace

### Small Note:

While having been worked on in 2024, this project has been uploaded to Github in 2025 to be viewable publicly.

## Brief Description

Whitespace is a 2D adventure visual novel alongside a simple turn-based combat system.

You play as an individual whose consciousness was digitized and uploaded to a server to be tested on. Your goal is to
escape your captors and return to the life you had. 

## What will the Application do?

The application will run a video game starting from the title screen containing the options of load game and start game.

The actual game's content will revolve around a 2D character that the player can control moving from screen to screen
with certain actions (e.g. entering a room, interacting with an item/npc) triggering a "cutscene" and thus advancing the
storyline. Certain interactions with npcs will begin a simple turn-based traditional RPG combat sequence.

## Who will use it?

Considering that this is purposed for entertainment more than anything (it is a game after all), the target audience is
anyone capable of using mouse and keyboard and, launch applications, and has free time to enjoy a short but fun game.

## Why is this Project of Interest to you?

Developing games has always been a passion for me, and thus I wanted to use this opportunity to make use of the skills I
had developed from using Scratch, Unity, Unreal Engine, and Gamemaker Studio to design a simple game that I can
reasonably make. This would be a first for me as I have never made a "gateway project" and had instead only devoted
myself to massive and elaborate projects that I could not feasibly complete.

## User Stories

- As a user, I want to be able to move the player character up, down, left, and right
- As a user, I want to be able to interact with items, thus picking them up and depositing them into my inventory
- As a user, I want to be able to access my inventory and view all items I have
- As a user, I want to be able to equip armor and accessories (only one of each at any point in time)
- As a user, I want to be able to use items of type consumable by selecting it in my inventory outside of combat
- As a user, I want to be able to interact with npcs and begin a dialogue sequence represented by text boxes
- As a user, I want to be able to select certain dialogue options that will lead to an alternative narrative progression
- As a user, I want to be able to control when a text box changes to the next during a dialogue sequence
- As a user, I want to be able to interact and thus begin a turn-base combat sequence with certain npcs
  - Within combat, as a user, I want to be able to select one action to perform per turn among the following:
    - Attack
    - Use skill
    - Use item
- As a user, I want to be able to change scenes by walking far enough to the edge of the screen or through a door
- As a user, I want to be able to collide with fixtures and not be able to move inside of them
- As a user, I want to be able to save my game-state (if I so choose)
- As a user, I want to be able to load my game-state from file (if I so choose)
- As a user, I want to be able to move to a door, perform an interaction, and thus end up in another room
- As a user, I want there to be no limit to the new rooms I can enter

# Instructions for Grader

- The keyboard controls are as follows:
  - During anytime while playing:
    - Pressing `O` will save the game
    - Pressing `P` will load the stored game state
  - During dialogue:
    - Pressing the `SPACE` key will continue onto the next dialogue component
  - During combat (Not yet fully implemented):
    - Pressing `1` will select attack
    - Pressing `2` will select skill
    - Pressing `3` will select item
    - Pressing `4` will select flee
    - Pressing the `ENTER` key will enter your selection
    - If skill was selected, the `LEFT` and `RIGHT` arrow keys will change what skill is selected and pressing the 
      `ENTER` key will enter your selection
    - If item was selected, the `LEFT` and `RIGHT` arrow keys will change what item is selected and pressing the
      `ENTER` key will enter your selection
  - During an adventure
    - Pressing `Q` will switch the visibility of the inventory
    - Pressing the arrow keys or the `WASD` set of keys will move the player around
    - Pressing `F` will pick up an item and add it to the player's inventory within the character's pickup radius
    - Pressing `E` will interact with any interactable object within the player's pickup radius, including moving to 
      another room
- In order to perform adding an X to Y:
  - Move the player to one of the items on the ground (yellow boxes outlined with black) and press `F`
  - **NOTE:** This is infinitely replicable as if the current room runs out of items, then the player can navigate to
    one of the gates (stairs) and press `E` to move to the next room where there are more items
- In order to view all Xs added to the Y:
  - Press `Q` when you are outside a cutscene and this will toggle the visibility of the list of items in the inventory

# Phase 4: Task 2

Fri Apr 05 09:15:51 PDT 2024  
New room of type 'Dungeon1' was generated

Fri Apr 05 09:15:55 PDT 2024  
Following item was generated at x = -14 and y = -32 in current room: Holy pants of Luck

Fri Apr 05 09:15:57 PDT 2024  
Following item was added to player's inventory: Holy pants of Luck

Fri Apr 05 09:15:59 PDT 2024  
New room of type 'Dungeon1' was generated

Fri Apr 05 09:15:59 PDT 2024  
Following item was generated at x = 125 and y = 65 in current room: Blood-stained chestwear of Durin

Fri Apr 05 09:15:59 PDT 2024  
Following item was generated at x = 261 and y = 76 in current room: Wicked helmet imbued with redness

Fri Apr 05 09:15:59 PDT 2024  
Following item was generated at x = 84 and y = 193 in current room: Dark gloves that happen to be broken

Fri Apr 05 09:16:02 PDT 2024  
Following item was added to player's inventory: Dark gloves that happen to be broken

Fri Apr 05 09:16:05 PDT 2024  
Following item was added to player's inventory: Blood-stained chestwear of Durin

Fri Apr 05 09:16:06 PDT 2024  
Following item was added to player's inventory: Wicked helmet imbued with redness

# Phase 4: Task 3

The most unquestionably important refactoring method that should be implemented is the singleton. Previously, reference
injections were required to pass along classes that would only ever need one instance because I had no clue how to have
a global accessor for a single static instance of a class. Several examples of classes that only ever needed one static 
instance include WhiteSpace, GamePanel, Console, JsonReader, JsonWriter, WhiteSpaceGame, Player, PlayableSceneManager,
CutSceneManager, CombatSceneManager. If this were implemented, this would remove the necessity of reference injections.