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
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.chat.ChatMessageEvent;
import org.terasology.logic.common.DisplayNameComponent;
import org.terasology.network.ColorComponent;
import org.terasology.network.NetworkComponent;
import org.terasology.registry.In;
import org.terasology.rendering.nui.Color;
import org.terasology.servermotd.events.DisplayMotdEvent;
import org.terasology.servermotd.events.EditMotdEvent;


@RegisterSystem(RegisterMode.AUTHORITY)
public class ServerMOTDSystem extends BaseComponentSystem {
    @In
    private EntityManager entityManager;

    @In
    private Context context;

    private StringBuilder message;
    private EntityRef MotdMessageEntity;

    @Override
    public void postBegin() {
        message = new StringBuilder("default");

        DisplayNameComponent displayNameComponent = new DisplayNameComponent();
        displayNameComponent.name = "Server Says";

        ColorComponent colorComponent = new ColorComponent();
        colorComponent.color = Color.MAGENTA;

        NetworkComponent networkComponent = new NetworkComponent();
        networkComponent.replicateMode = NetworkComponent.ReplicateMode.ALWAYS;

        MotdMessageEntity = entityManager.create(displayNameComponent, colorComponent, networkComponent);
    }

    @ReceiveEvent
    public void editMessage(EditMotdEvent editEvent, EntityRef playerEntity) {
        if (editEvent.overwriteMotd) {
            message = new StringBuilder(editEvent.editMessage);
        }

        else {
            message.append(" ");
            message.append(editEvent.editMessage);
        }
    }

    @ReceiveEvent
    public void displayMessage(DisplayMotdEvent displayEvent, EntityRef playerEntity) {
        playerEntity.getOwner().send(new ChatMessageEvent(message.toString(), MotdMessageEntity));
    }
}
