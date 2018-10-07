/*
 * Copyright 2017 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.servermotd;

import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.console.commandSystem.annotations.Command;
import org.terasology.logic.console.commandSystem.annotations.CommandParam;
import org.terasology.logic.permission.PermissionManager;
import org.terasology.logic.players.LocalPlayer;
import org.terasology.registry.In;
import org.terasology.servermotd.events.DisplayMotdEvent;
import org.terasology.servermotd.events.EditMotdEvent;

@RegisterSystem(RegisterMode.CLIENT)
public class ClientMOTDSystem extends BaseComponentSystem {
    @In
    LocalPlayer localPlayer;

    @Override
    public void postBegin() {
        displayMotd();
    }

    @Command(shortDescription = "Append to server MOTD",
            helpText = "Append a message to the current server MOTD if you are admin.",
            requiredPermission = PermissionManager.CHEAT_PERMISSION)
    public String appendToMotd (@CommandParam(value = "New Message") String message) {
        EditMotdEvent editEvent = new EditMotdEvent();
        editEvent.setEditMessage(message);
        editEvent.setOverwriteMotd(false);
        localPlayer.getCharacterEntity().send(editEvent);

        return "Server MOTD edited use displayMOTD command to view the new MOTD";
    }

    @Command(shortDescription = "Displays server MOTD",
            requiredPermission = PermissionManager.NO_PERMISSION)
    public void displayMotd() {
        DisplayMotdEvent displayEvent = new DisplayMotdEvent();
        localPlayer.getCharacterEntity().send(displayEvent);
    }

    @Command(shortDescription = "Overwites the current server MOTD",
            helpText = "Overwrites the current server MOTD if you are admin.",
            requiredPermission = PermissionManager.CHEAT_PERMISSION)
    public String overwriteMotd (@CommandParam(value = "New Message") String message) {
        EditMotdEvent editEvent = new EditMotdEvent();
        editEvent.setOverwriteMotd(true);
        editEvent.setEditMessage(message);
        localPlayer.getCharacterEntity().send(editEvent);
        return "Server MOTD edited use displayMOTD command to view the new MOTD";
    }
}
