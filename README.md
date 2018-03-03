# ServerMOTD

A module which implements the functionality to display a MOTD when a client joins.

####Commands in module

- **displayMOTD**
This command can be used by the server host as well as the connected clients to view the current MOTD.

- **editMOTD:** 
This command can be run by the server host only, to edit the MOTD in-game. Parameters that can be used are 
a - To append the provided string at the end of current MOTD. 
w - To replace the current MOTD with the provided string.

- **overwriteMOTD**
This command is same as using `editMOTD` with the `w` argument.