# Inventory-Management-System
This application is designed to be a simple inventory management system for a small business that maintains lists of parts and products.

# User Interface
The following actions are available for both parts and products:
* Add
* Modify
* Delete

Both parts and products are displayed in a searchable tables that show their:
* ID
* Name
* Inventory Level
* Price

There is also an exit button.

# Objects
Parts are entered as either "In-House" or "Outsourced" and have the following fields:
* ID
* Name
* Inventory Level (must be between the min and max levels)
* Price
* Minimum inventory (must be lower than maximum inventory)
* Maximum inventory (must be lower than minimum inventory)
* Identifier depending on part type:
  - In-House : Machine ID
  - Outsourced : Company Name

Products have the following entry fields:
* ID
* Name
* Inventory Level (must be between the min and max levels)
* Price
* Minimum inventory (must be lower than maximum inventory)
* Maximum inventory (must be lower than minimum inventory)
* List of associated parts
  - Parts can be added/removed via searchable table

# Built With
  * Java using JetBrains' IntelliJ IDE
  * JavaFX for the User Interface
