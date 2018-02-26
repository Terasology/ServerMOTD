# ServerMOTD

A module which factors out the Server MOTD functionality into a module.

####Commands in module
- **editMOTD:** 
This command can be run by the server host only, to edit the MOTD in-game. Parameters that can be used are 
a - To append the provided string at the end of current MOTD. 
w - To replace the current MOTD with the provided string.

- **displayMOTD**
This command can be used by the server host as well as the connected clients to view the current MOTD.