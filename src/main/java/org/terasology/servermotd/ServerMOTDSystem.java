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

import org.terasology.module.DependencyResolver;
import org.terasology.module.Module;
import org.terasology.module.ModuleEnvironment;
import org.terasology.module.ModulePathScanner;
import org.terasology.module.ModuleRegistry;
import org.terasology.module.ResolutionResult;
import org.terasology.module.TableModuleRegistry;
import org.terasology.module.sandbox.BytecodeInjector;
import org.terasology.module.sandbox.ModuleSecurityManager;
import org.terasology.module.sandbox.ModuleSecurityPolicy;
import org.terasology.module.sandbox.StandardPermissionProviderFactory;
import org.terasology.naming.Name;


import java.nio.file.Paths;
import java.security.Policy;
import java.util.Collections;
import java.util.Set;

public class ServerMOTDSystem {

    public ModuleRegistry buildModuleRegistry() {
        ModuleRegistry registry = new TableModuleRegistry();
        new ModulePathScanner().scan(registry, Paths.get(""));
        return registry;
    }

    public Set<Module> determineModuleSet() {
        DependencyResolver resolver = new DependencyResolver(buildModuleRegistry());
        ResolutionResult resolutionResult = resolver.resolve(new Name("ModuleOne"), new Name("ModuleTwo"));
        if (resolutionResult.isSuccess()) {
            return resolutionResult.getModules();
        } else {
            throw new RuntimeException("Unable to resolve compatible dependency set for ModuleOne and ModuleTwo");
        }
    }

    public ModuleEnvironment establishSecureModuleEnvironment() {
        StandardPermissionProviderFactory permissionProviderFactory = new StandardPermissionProviderFactory();

        permissionProviderFactory.getBasePermissionSet().addAPIPackage("com.example.api");
        permissionProviderFactory.getBasePermissionSet().addAPIPackage("sun.reflect");
        permissionProviderFactory.getBasePermissionSet().addAPIClass(String.class);

        Policy.setPolicy(new ModuleSecurityPolicy());
        System.setSecurityManager(new ModuleSecurityManager());
        return new ModuleEnvironment(determineModuleSet(), permissionProviderFactory,
                Collections.<BytecodeInjector>emptyList());
    }

    public void initialize() {
        ModuleEnvironment environment = establishSecureModuleEnvironment();

        for (Class<?> type : environment.getTypesAnnotatedWith(MOTD.class)) {

        }
    }
}
