# backendClub

Kotlin + Ktor backend. One‑shot instructions for Linux, macOS, and Windows.

## Setup & Run (all platforms in one place)

### 0) Prereqs
- Java 21 or newer
- Git
- (Gradle not required; we use the wrapper)

### 1) Install Java 21

# --- Linux (Debian/Ubuntu) ---
sudo apt update && sudo apt install -y openjdk-21-jdk && java -version

# --- macOS (Homebrew) ---
brew install openjdk && \
sudo ln -sfn "$(brew --prefix openjdk)"/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk.jdk && \
echo 'export PATH="$(brew --prefix openjdk)/bin:$PATH"' >> ~/.zshrc && \
source ~/.zshrc && \
java -version

# --- Windows 10/11 (PowerShell) ---
# If you have winget:
winget install --id EclipseAdoptium.Temurin.21.JDK -e
# Otherwise (Chocolatey):
# choco install temurin21
java -version

### 2) Get the code
git clone https://github.com/am-p/backendClub.git
cd backendClub

### 3) Run the server (uses Gradle Wrapper)

# Linux/macOS:
./gradlew run

# Windows (PowerShell or cmd):
gradlew.bat run

# Server starts at:
# http://localhost:8080

### 4) Quick test (any platform with curl)

# Health
curl http://localhost:8080/health

# Login (JSON)
curl -X POST http://localhost:8080/login -H "Content-Type: application/json" -d "{\"username\":\"Ariel\",\"password\":\"secret\"}"

### 5) (Optional) Build a distributable and run

# Build installable distribution:
# Linux/macOS:
./gradlew installDist
# Windows:
gradlew.bat installDist

# Run the installed app:
# Linux/macOS:
app/build/install/app/bin/app
# Windows:
app\build\install\app\bin\app.bat

## Notes
- If macOS can’t find `java`, reopen the terminal or `source ~/.zshrc`.
- If Windows says “java not found,” open a new terminal after installing the JDK.
- Default port is 8080; change it in `Application.kt` if needed.
