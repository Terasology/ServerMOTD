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
import org.terasology.registry.CoreRegistry;
import org.terasology.registry.In;

@RegisterSystem(RegisterMode.CLIENT)
public class ClientMOTDSystem extends BaseComponentSystem {
    @In
    private EntityManager entityManager;

    private MOTDProvider renderMOTD = new MOTDProvider();

    private Context context = CoreRegistry.get(Context.class);
    private EntityRef entity;

    public void initialise() {

    }

    @Override
    public void postBegin() {
        displayMOTD();
    }

    @Command(shortDescription = "Displays server MOTD", requiredPermission = PermissionManager.NO_PERMISSION)
    public void displayMOTD() {
        entity = renderMOTD.getMOTDEntity(entityManager);
        renderMOTD.display(entity.getComponent(MOTDComponent.class).motd, context);
    }

    @Command(shortDescription = "Edit server MOTD", helpText = "Edit the current server MOTD if you are admin." +
            " Use \'a\' - to append to current MOTD" +
            " 'w' for a new one", requiredPermission = PermissionManager.CHEAT_PERMISSION)
    public String editMOTD (@CommandParam(value = "a/w") String type, @CommandParam(value = "New Message") String message) {
        entity = renderMOTD.getMOTDEntity(entityManager);
        MOTDComponent comp = entity.getComponent(MOTDComponent.class);
        switch (type) {
            case "a":
                comp.motd += message;
                break;

            case "w":
                comp.motd = message;
                break;

            default:
            return "Error in command syntax, use help editMOTD command for help";
        }

        entity.saveComponent(comp);
        return "Server MOTD edited use displayMOTD command to view the new MOTD";
    }
}
