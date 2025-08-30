package com.geovannycode;

import com.geovannycode.store.StoreCqrsApplication;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.core.ApplicationModule;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

import java.util.stream.Collectors;

public class ModularityTest {

    private static final Logger log = LoggerFactory.getLogger(ModularityTest.class);

    ApplicationModules modules = ApplicationModules.of(StoreCqrsApplication.class);

    /**
     * Verifica reglas modulares, dependencias y ciclos.
     */
    @Test
    void shouldHaveValidModularStructure() {
        modules.verify();
    }

    /**
     * Genera documentación automática (AsciiDoc, C4 y PlantUML) en target/spring-modulith-docs/.
     */
    @Test
    void shouldGenerateDocumentation() throws Exception {
        new Documenter(modules)
                .writeDocumentation()           // AsciiDoc
                .writeModuleCanvases()          // Diagramas C4
                .writeIndividualModulesAsPlantUml(); // PlantUML
    }

    /**
     * Test informativo - muestra qué módulos encontró Spring Modulith.
     */
    @Test
    void showModuleStructure() {
        log.info("🏗️ Estructura de módulos detectada:");
        modules.forEach(module -> {
            var name = module.getDisplayName();
            var basePkg = module.getBasePackage().getName();

            // Dependencias directas (o usa getDependencies(modules, DependencyDepth.DIRECT, ...) si prefieres)
            long directDeps = module.getDirectDependencies(modules).uniqueModules().count();

            String depsList = module.getDirectDependencies(modules)
                    .uniqueModules()
                    .map(ApplicationModule::getDisplayName)
                    .sorted()
                    .collect(Collectors.joining(", "));

            if (depsList.isBlank()) depsList = "(ninguna)";

            log.info("📦 Módulo: {}", name);
            log.info("   📁 Paquete: {}", basePkg);
            log.info("   🔗 Dependencias directas ({}): {}", directDeps, depsList);
        });
    }
}
