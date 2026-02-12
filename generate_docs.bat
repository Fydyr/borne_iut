@echo off
REM Generation de la documentation Doxygen
REM Usage: generate_docs.bat

cd /d "%~dp0"

where doxygen >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo Erreur : Doxygen n'est pas installe.
    echo   Telechargez-le sur https://www.doxygen.nl/download.html
    exit /b 1
)

echo Generation de la documentation...
doxygen Doxyfile

echo.
echo Documentation generee dans docs\generated\html\
echo Ouverture dans le navigateur...
start "" "docs\generated\html\index.html"
