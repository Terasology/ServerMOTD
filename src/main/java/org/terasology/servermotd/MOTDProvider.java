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
import org.terasology.network.NetworkComponent;
import org.terasology.rendering.nui.NUIManager;
import org.terasology.rendering.nui.layers.mainMenu.MessagePopup;

import java.util.Iterator;

public class MOTDProvider {

    public void display(String motd, Context context) {
        NUIManager nuiManager = context.get(NUIManager.class);
        nuiManager.pushScreen(MessagePopup.ASSET_URI, MessagePopup.class).setMessage("Server MOTD", motd);
    }

    public EntityRef getMOTDEntity(EntityManager entityManager) {
        Iterable<EntityRef> MOTDEntities = entityManager.getEntitiesWith(MOTDComponent.class);
        Iterator<EntityRef> i = MOTDEntities.iterator();

        if(i.hasNext()) {
            return i.next();
        }
        else {
            MOTDComponent MOTDComp = new MOTDComponent();
            MOTDComp.motd = "default";

            EntityRef entityRef = entityManager.create(MOTDComp);

            NetworkComponent netComp = new NetworkComponent();
            netComp.replicateMode = NetworkComponent.ReplicateMode.ALWAYS;
            entityRef.addComponent(netComp);

            return entityRef;
        }
    }

}
