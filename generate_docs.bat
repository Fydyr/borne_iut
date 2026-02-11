@echo off
echo ============================================
echo   Generation de la documentation
echo   Borne Arcade IUT
echo ============================================
echo.

where doxygen >nul 2>&1
if %errorlevel% neq 0 (
    echo ERREUR : Doxygen n'est pas installe ou pas dans le PATH.
    echo Telechargez-le sur https://www.doxygen.nl/download.html
    pause
    exit /b 1
)

echo Lancement de Doxygen...
doxygen Doxyfile

if %errorlevel% equ 0 (
    echo.
    echo Documentation generee avec succes !
    echo Ouvrir : docs\generated\html\index.html
    echo.
    start "" "docs\generated\html\index.html"
) else (
    echo.
    echo ERREUR lors de la generation.
)

pause
