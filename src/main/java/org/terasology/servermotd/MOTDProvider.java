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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        nuiManager.pushScreen(MessagePopup.ASSET_URI, MessagePopup.class).setMessage("Server Says", motd);
    }

    public EntityRef getMOTDEntity(EntityManager entityManager) {
        Iterable<EntityRef> motdEntities = entityManager.getEntitiesWith(MOTDComponent.class);
        Iterator<EntityRef> i = motdEntities.iterator();
        Logger logger = LoggerFactory.getLogger(MOTDProvider.class);

        if(i.hasNext()) {
            logger.info("=========================Used old entity=========================");
            int sum = 0;
            EntityRef entityRef = i.next();
            while(i.hasNext()) {
                entityRef = i.next();
                sum++;
            }
            logger.info(Integer.toString(sum + 1));
            return entityRef;
        }
        else {
            MOTDComponent motdComp = new MOTDComponent();
            motdComp.motd = "default";

            EntityRef entityRef = entityManager.create(motdComp);

            NetworkComponent netComp = new NetworkComponent();
            netComp.replicateMode = NetworkComponent.ReplicateMode.ALWAYS;
            entityRef.addComponent(netComp);

            logger.info("=====================Created new entity===========================" + entityRef);

            return entityRef;
        }
    }

}
