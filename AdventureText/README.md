# Adventure
A Java based text adventure game which will have a lot of details (eventually)

help=There are 3 different prompts:
??? - is a prompt that wants a specific input, like the player's name
=== - is a prompt that wants a selection from a menu of choices, this i s normally a number.
>>> - This is the main prompt and wants a command.  The commands are detailed below

Commands:
There are a few different commands.  In general a command takes the form of <action verb> <target> or <action verb> <source> <preposition> <target>.  See below for examples.

Move <direction> - This moves you from area to area within the adventure.  The direction options are the 4 compass points, north, east, south and west.

Search - This is a general search of the area you are in.  You can search most things specifically mentioned.  If you are confused, search or search directions (see below)

Search <direction> - This is a directed search in a specific direction, helping you understand what is in that direction or how to move (like if the door is closed, open it first)

Search <target> - This allows you to get more information about a thing, such as a door or a chest or some other thing.

Open <target> - If something is closed, like a door or chest, then you can open it.  You need to open a door before you can move through it.

Close <target> - If something is open and it offends you and you wish to close it, close it.  :)

Take <something> - There are certain things you can take, keys, gems, weapons, etc.  (See note below about "things")

Inventory - This lists out the things you've taken as well as an amount of money you have.

Use <target> - This applies an effect of the target on you.  For example, "use potion".

Use <item> on <target> - This will take the item and apply its purpose to the target.  For example, "use key on door" will unlock or lock a door.

Take <something> from <target> - In some instances the thing you want to take is in or on something else, so you need to be specific about where the something comes from.  (See note below about "things")

Quit - Quits the adventure.  Note that your progress is NOT saved.