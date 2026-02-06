# Minecraft RCON Maven Plugin

A Maven plugin for executing **RCON commands on a Minecraft server** during the build lifecycle.
It is primarily used to automate plugin reloads or administrative commands after a successful JAR build.

---
![Demo](https://github.com/Nek0rise/Minecraft-RCON-Maven-plugin/blob/main/media/demo.gif?raw=true)

## Features

- Execute **multiple RCON commands** in order
- Runs automatically in a selected Maven phase
- No external scripts or tools required

---

## Requirements

- **Java:** 8 or newer
- **Maven:** 3.8+
- **Minecraft Server:** RCON enabled

---

## Minecraft server Setup (Required)

Enable RCON in `server.properties`:

```
enable-rcon=true
rcon.password=YourPassword
rcon.port=25575
```

---

## Installation

### Option A - Maven Repository

Add it to your `pom.xml`:

```xml
    <pluginRepositories>
        <pluginRepository>
            <id>minecraft-rcon-maven-plugin</id>
            <url>https://repo.codemc.io/repository/nek0rise/</url>
        </pluginRepository>
    </pluginRepositories>
```

---

### Option B - Manual / Offline Installation

1. Download the `.zip` archive from **[Releases](https://github.com/Nek0rise/Minecraft-RCON-Maven-plugin/releases)**
2. Extract the archive
3. Run one of the following scripts:
   - **Windows:** `install.bat`
   - **Linux / macOS:** `install.sh`

This installs the plugin into your local Maven repository.

---

## Usage in a Project

Add the plugin to your project `pom.xml`.
It is recommended to place it **after all other build plugins**.

### Example Configuration

```xml
<build>
    <plugins>
        <plugin>
            <groupId>uwu.nekorise</groupId>
            <artifactId>minecraft-rcon-maven-plugin</artifactId>
            <version>1.0.1</version>

            <executions>
                <execution>
                    <id>rcon-after-package</id>
                    <phase>package</phase>
                    <goals>
                        <goal>rcon</goal>
                    </goals>
                </execution>
            </executions>

            <configuration>
                <host>127.0.0.1</host>
                <port>25575</port>
                <password>testPass</password>

                <commands>
                    <command>plugman reload MyPlugin</command>
                    <command>say Build completed</command>
                </commands>

                <skip>false</skip>
            </configuration>
        </plugin>
    </plugins>
</build>
```

Run the build:

```
mvn package
```

At the end of the `package` phase, the plugin connects to the Minecraft server via RCON and executes all commands sequentially.

---

## Configuration Parameters

| Name       | Type           | Required | Description |
|------------|----------------|----------|-------------|
| `host`     | `String`       | Yes      | Minecraft server address |
| `port`     | `int`          | Yes      | RCON port |
| `password` | `String`       | Yes      | RCON password |
| `commands` | `List<String>` | Yes      | Commands to execute (in order) |
| `skip`     | `boolean`      | No       | Disable execution (`false` by default) |

---

## Commands Example

```xml
<commands>
    <command>plugman reload MyPlugin</command>
    <command>lp reload</command>
    <command>say Reload completed</command>
</commands>
```

---

## Disabling Execution

### Via `pom.xml`

```xml
<skip>true</skip>
```

### Via command line

```bash
mvn package -Dskip=true
```

---

## Notes

- Ensure RCON is enabled and reachable
- Avoid unsafe reloads of third-party plugins
- **NEVER** commit RCON passwords (Better use `.env`)

