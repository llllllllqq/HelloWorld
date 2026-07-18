# HelloWorld Android App

一个简单的 Android HelloWorld 应用，通过 GitHub Actions 自动编译。

## 使用方法

### 1. 推送到 GitHub

```bash
cd HelloWorld
git init
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/<你的用户名>/HelloWorld.git
git push -u origin main
```

### 2. 触发编译

- **自动**：推送 `main` 分支或创建 PR 时会自动触发编译
- **手动**：在 GitHub 仓库的 Actions 页面，选择 **Build Android APK** → **Run workflow**

### 3. 下载 APK

编译完成后，在 Actions 页面对应运行记录中，找到 **Artifacts** 区域，下载 `app-debug.zip`，解压即可得到 `app-debug.apk`。

## 项目结构

```
HelloWorld/
├── .github/workflows/build.yml   # GitHub Actions 编译配置
├── app/
│   ├── build.gradle.kts           # 应用模块构建配置
│   └── src/main/
│       ├── AndroidManifest.xml    # 应用清单
│       ├── java/.../MainActivity.java  # 主Activity
│       └── res/                   # 资源文件
├── build.gradle.kts               # 根项目构建配置
├── settings.gradle.kts            # 项目设置
└── gradle.properties              # Gradle 属性
```
