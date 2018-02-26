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
import org.terasology.logic.console.commandSystem.annotations.Sender;
import org.terasology.registry.CoreRegistry;
import org.terasology.registry.In;


@RegisterSystem(RegisterMode.AUTHORITY)
public class ServerMOTDSystem extends BaseComponentSystem {
    @In
    private EntityManager entityManager;

    private Context context = CoreRegistry.get(Context.class);

    private EntityRef MOTDEntity;
    private MOTDProvider renderMOTD = new MOTDProvider();

    public void initialise() {

    }

    @Override
    public void postBegin() {
        MOTDEntity = renderMOTD.getMOTDEntity(entityManager);
    }

    @Command(shortDescription = "Edit server MOTD", helpText = "Edit the current server MOTD if you are admin." +
            " Use \'a\' - to append to current MOTD" +
            " 'w' for a new one", runOnServer = true)
    public String editMOTD(@CommandParam(value = "a/w") String type, @CommandParam(value = "New Message") String message, @Sender EntityRef admin) {
        MOTDComponent comp = MOTDEntity.getComponent(MOTDComponent.class);
        if (type.equals("a")) {
            comp.motd += message;
        }

        else if (type.equals("w")) {
            comp.motd = message;
        }

        else {
            return "Error in command syntax, use help editMOTD command for help";
        }

        MOTDEntity.saveComponent(comp);
        return "Server MOTD edited use displayMOTD command to view the new MOTD";
    }
}
