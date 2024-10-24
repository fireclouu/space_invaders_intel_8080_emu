# Space Invaders - Intel 8080 emulator

Playable Space Invaders machine emulation with **Intel 8080 CPU interpreter**, written in Java.\
\
[![Android CI](https://github.com/fireclouu/space_invaders_android/actions/workflows/android.yml/badge.svg?branch=master)](https://github.com/fireclouu/space_invaders_android/actions/workflows/android.yml)

---

### Screenshot
<img alt="screenshot" src="https://github.com/user-attachments/assets/fd39c362-f21d-44af-b9af-5975c919885f" width="200"/>

### Platform

[![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://github.com/fireclouu/space_invaders_intel_8080_emu/releases/download/v2.0/app-release.apk)

```text
Version 2.0
- Added Player 2 support by fixing and rewriting interrupt handler
- fixes thread issues and input stream handling
- Improved stability when suspending application
- automatically scale and position to wider screen
- MMU feature implemented
- fixed persistent high score feature
- fixed button drawable
- rewrite and optimized surface draw function
- accurately mapping VRAM pixel points to host
- dynamic orientation changes
- performance boost by fixing incorrect hardware canvas implementation
- correct aspect ratio display on dynamic windows
- smoother canvas blit
```

```text
Version 1.3
Android
- fixes to few deprecated libraries
- optimizations by removing unused flags
- fixed adaptive display size
```

```text
Version 0.1
Android
- Initial release
- Supports Android 10 and up
```

> [!TIP]
> You can also get latest builds
> via [Actions](https://github.com/fireclouu/space_invaders_android/actions) tab.

### Assets

- [Sound assets](https://samples.mameworld.info/)
- [Button face Graphics assets](https://ya-webdesign.com)

### Resources

- [emulator101](http://emulator101.com/)
- [superzazu](https://github.com/superzazu/8080)
- <a href="https://www.flaticon.com/free-icons/space-invaders" title="space invaders icons">Space
  invaders icons created by IconMark - Flaticon</a>
