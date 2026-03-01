# InventoryManager
This is a portable Inventory Manager application that I originally made for my student friends to practice coding. It is now intended to be used by people who may not want to use an excel sheet.

HOW TO USE (TUTORIAL):
1. Initial boot screen:
  -The initial boot screen is quite simple. After opening up the application, There will be 3 options. Create, choose and open recent. Create let's you create a new .csv file to manipulate (After v1.0). Choose will         let you choose .csv or .txt files with the right format to manipulate. And the open recent button will let you choose a recent file from the comboBox element and open it up in a quick way.
   
2. Inventory Screen: 
   -After opening up the file you desire, you are greeted with a table with ID, NAME, AMOUNT, PRICE, AVERAGE_PRICE columns with the ID column in red. On the top there are 3 menu items "Operations", "Manipulation            Selection", "Export" respectively. In this menu, if you drag your mouse on any of these options, you will see a drop down menu appear.

     Operations:<
       *Add New Item -> Adds new item to the Inventory (If Modify Database is checked, makes the modification permanent)
       *Remove Menu Item -> Removes item from the Inventory by ID (If Modify Database is checked, makes the modification permanent)
       *Get Inventory Worth -> Gets the whole worth of the Inventory { Σ(PRICE*AMOUNT) }

     Manipulation Selection:
       *Modify Database -> If checked, realizes the changes inside of the permanent storage. (DOES NOT REALIZE CHANGES DONE BEFORE CHECKING THE OPTION)

     Export:
       *Export XML -> Exports the current Inventory to an .XML file (ONLY EXPORTS WHAT'S IN THE PERMANENT STORAGE.)



NOTES FOR v1.0:
-IN THE 1.0 VERSION THE CREATE BUTTON IN THE INITIAL BOOT SCREEN DOES NOT WORK YET
