# adventure-game-java
The 3rd semester homework

## src folder structure (program layers)

### file
Responsible for handling file related operations and the distribution of data. Such as reading objects from files and giving them to the proper container object.

### game
Contains java code responsible for the game logic, such as fighting, moving, talking to NPCs, calculating damage, etc.

### uilogic
Contains scripts that connect the UI to the game logic. It is responsible for sending calculated position to the UI and to the game logic objects, it transfers data from the game logic layer to the UI and vice versa. It does calculations that is based on UI data but NOT displaying (such as the range of an attack based on player & enemy position on the grid) and everything that is related to data transfer from game logic layer to UI layer.

### ui
Only responsible for displaying data. Includes custom UI elements and UI handlers.