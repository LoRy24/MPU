# Minecraft Pinging Utilities (MPU)
![GitHub License](https://img.shields.io/github/license/LoRy24/MPU?style=flat-square)
![GitHub top language](https://img.shields.io/github/languages/top/LoRy24/MPU?style=flat-square)
![GitHub manifest version](https://img.shields.io/github/manifest-json/v/LoRy24/MPU?style=flat-square&label=latest%20version)


A modern, lightweight Java library for interacting with the Minecraft Server List Ping protocol.

## 🎗️ Overview
MPU provides an easy-to-use API to ping Minecraft servers and retrieve their status information — including version, player count, MOTD, favicon, and more — without having to deal with raw protocol details.

## 📦 Features
- Supports the latest Minecraft server ping protocol.
- Parses responses into clean, typed Java objects.
- Handles description (MOTD), version, players, favicon, and enforcesSecureChat.
- Lightweight and easy to integrate with any Java project (Spigot, Paper, Velocity, standalone apps, etc.).
- BSD-3-Clause license.

## ⚒️ Installation
To install this framework, you might import it via Maven or Gradle. For maven, just paste this piece of code inside of the
dependencies section:

```xml
<dependency>
  <groupId>com.github.lory24</groupId>
  <artifactId>mpu-framework</artifactId>
  <version>1.0.2</version>
</dependency>
```

If you're using gradle, paste this:

```groovy
implementation 'com.github.lory24:mpu-framework:1.0.2'
```

Otherwise, you have to download the package .jar file and import it manually (unrecommended).

> [!CAUTION]
> In order to include the Gradle or Maven dependency, you'll need to add the GitHub repository as shown in the 
> [Official Guide for Maven](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry) 
> or [Official Guide for Gradle](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry). 
> Remember to create a token and set it in the environment variables or any other method to let maven/gradle use them.

## 🤝 Contributing
Good contributions (including docs) are welcome!
- Fork the repository
- Create a new feature/fix branch
- Commit and push your changes
- Open a Pull Request with a clear description of your changes
If you need more details, check the contributing section.

## ⚠️ Notes
- Some servers return the description field as a rich JSON chat component instead of plain text. MPU parses basic structures, but you may need to handle advanced formatting yourself.
- Ensure compatibility with custom or modified server versions when needed.

## 📄 License
This project is licensed under the *BSD-3-Clause* License. See the [LICENSE](./LICENSE) file for details.
