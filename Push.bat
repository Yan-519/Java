@echo off
setlocal

echo =========================
echo          Commit
echo =========================
echo.


set /p "commit_message=Commit message: "

:empty_message
if "%commit_message%"=="" (
    echo Commit message cannot be empty.
    set /p "commit_message=Commit message: "

    goto :empty_message
)

echo.
choice /C YN /M "Are you sure you want to save the update?"
if errorlevel 2 exit /b 0

echo.
echo Adding files...
git add .
if errorlevel 1 (
    echo Git add failed.
    pause
    exit /b 1
)

echo.
echo Committing...
git commit -m "%commit_message%"
if errorlevel 1 (
    echo Git commit failed.
    pause
    exit /b 1
)

echo.
echo Pushing...
git push

if errorlevel 1 (
    echo.
    choice /C YN /M "Git push failed. Would you like to force push?"
    if errorlevel 2 exit /b 1

    echo.
    echo Force pushing...
    git push --force

    if errorlevel 1 (
        echo Force push failed.
        pause
        exit /b 1
    )
)

echo.
echo Changes pushed successfully.
pause