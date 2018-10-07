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

import org.terasology.context.Context;
import org.terasology.entitySystem.entity.EntityManager;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.console.commandSystem.annotations.Command;
import org.terasology.logic.console.commandSystem.annotations.CommandParam;
import org.terasology.logic.permission.PermissionManager;
import org.terasology.logic.players.LocalPlayer;
import org.terasology.registry.CoreRegistry;
import org.terasology.registry.In;
import org.terasology.rendering.nui.NUIManager;
import org.terasology.servermotd.events.DisplayMotdEvent;
import org.terasology.servermotd.events.EditMotdEvent;

@RegisterSystem(RegisterMode.CLIENT)
public class ClientMOTDSystem extends BaseComponentSystem {
    @In
    LocalPlayer localPlayer;

    @In
    private Context context;

    public void initialise() {

    }

    @Command(shortDescription = "Append to server MOTD",
            helpText = "Append a message to the current server MOTD if you are admin.",
            requiredPermission = PermissionManager.CHEAT_PERMISSION,
            runOnServer = true)
    public String appendToMotd (@CommandParam(value = "New Message") String message) {
        EditMotdEvent editEvent = new EditMotdEvent(false, message);
        localPlayer.getCharacterEntity().send(editEvent);

        return "Server MOTD edited use displayMOTD command to view the new MOTD";
    }

    @Command(shortDescription = "Displays server MOTD",
            requiredPermission = PermissionManager.NO_PERMISSION)
    public void displayMotd() {
        NUIManager nuiManager = context.get(NUIManager.class);
        localPlayer.getCharacterEntity().send(new DisplayMotdEvent(nuiManager));
    }

    @Command(shortDescription = "Overwites the current server MOTD",
            helpText = "Overwrites the current server MOTD if you are admin.",
            requiredPermission = PermissionManager.CHEAT_PERMISSION,
            runOnServer = true)
    public String overwriteMotd (@CommandParam(value = "New Message") String message) {
        EditMotdEvent editEvent = new EditMotdEvent(true, message);
        localPlayer.getCharacterEntity().send(editEvent);
        return "Server MOTD edited use displayMOTD command to view the new MOTD";
    }
}
