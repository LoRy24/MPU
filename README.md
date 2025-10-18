# Minecraft Pinging Utilities (MPU)
A modern, lightweight Java library for interacting with the Minecraft Server List Ping protocol.

## 🎗️ Overview
MPU provides an easy-to-use API to ping Minecraft servers and retrieve their status information — including version, player count, MOTD, favicon, and more — without having to deal with raw protocol details.

## 📦 Features
- Supports the latest Minecraft server ping protocol.
- Parses responses into clean, typed Java objects.
- Handles description (MOTD), version, players, favicon, and enforcesSecureChat.
- Lightweight and easy to integrate with any Java project (Spigot, Paper, Velocity, standalone apps, etc.).
- BSD-3-Clause license.

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
