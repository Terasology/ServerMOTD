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
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.network.NetworkMode;
import org.terasology.network.NetworkSystem;
import org.terasology.registry.CoreRegistry;
import org.terasology.registry.Share;

@Share(ServerMOTD.class)
@RegisterSystem(RegisterMode.ALWAYS)
public class ServerMOTDSystem extends BaseComponentSystem implements ServerMOTD {
    private Context context = CoreRegistry.get(Context.class);

    @Override
    public void initialise() {
        String motd = "default";

        NetworkSystem networkSystem = context.get(NetworkSystem.class);

        if (networkSystem.getMode() == NetworkMode.CLIENT) {
            if (motd != null && motd.length() != 0) {
                MOTDProvider renderMOTD = new MOTDProvider();
                renderMOTD.display(motd, context);
            }
        }
    }
}
