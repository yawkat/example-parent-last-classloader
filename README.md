Example of using a parent-last class loader to load an extension at runtime.

Usage:

```bash
mvn package
java -cp parent-module/target/parent-module-1.0-SNAPSHOT.jar at.yawk.test.ParentMain
```

- The child-module is a compile-time dependency of parent-module, but is not initially present on the classpath
- Some code in parent-module uses child-module
- ParentMain creates a parent-last classloader with itself and the child-module jar to run the parent-module code that depends on child-module
