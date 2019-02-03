# Dockerizing ADAMS

1. Build Docker images.
```bash
./build.sh
```

2. Run container.
```bash
docker run --rm -it --name adams-develop -v "$(dirname "$(pwd)")":/adams -v "${HOME}":/home/adams -v /tmp/.X11-unix:/tmp/.X11-unix -e DISPLAY=${DISPLAY} -p 127.0.0.1:5005:5005 adams/develop:builder
```

3. Build ADAMS inside container.
```bash
mvn -T 2C package -DskipTests=true
```

4.1. Run ADAMS.
```bash
java -cp ./adams-core/target/adams-core-19.2.0-jar-with-dependencies.jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005 adams.gui.Main
```

4.2. Or alternatively run fully packaged ADAMS.
```bash
docker run --rm -it --name adams-develop -v /tmp/.X11-unix:/tmp/.X11-unix -e DISPLAY=${DISPLAY} adams/base:latest
``` 

5. Remote debug run configurations for IntelliJ.

    - Menu > Run > Edit Configurations...
    - Add New Configuration > Remote
    - Edit configurations to match
    ```
     Name: attach to docker
     Debugger mode: Attach to remote JVM
     Host: localhost
     Port: 5005
     Use module classpath: adams-core
     ```
    - Save
    - Menu > Run > Debug...
    - Select 'attach to docker'
    
    IDEA should be connected to using JPDA to the JVM inside the container.