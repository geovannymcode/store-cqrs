@ApplicationModule(
        displayName = "Orders",
        allowedDependencies = {"common", "catalog", "inventory"}
)
package com.geovannycode.store.orders;

import org.springframework.modulith.ApplicationModule;