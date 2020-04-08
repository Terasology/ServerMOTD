# ServerMOTD

A module which implements the functionality to display a MOTD when a client joins.

####Commands in module

- **appendToMOTD:**
This command can be run by the server host only, to edit the MOTD in-game. The message provided as an argument wtih the command is appended at the end of the current MOTD.

- **displayMOTD**
This command can be used by the server host as well as the connected clients to view the current MOTD.

- **overwriteMOTD**
This command can be run by the server host only, to edit the MOTD in-game. The message provided as an argument wtih the command is overwrites the current MOTD.
