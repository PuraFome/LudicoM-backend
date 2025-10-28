# Load .env file into environment for this session then run mvn spring-boot:run
# Usage: .\run-with-env.ps1  (PowerShell)

$envFile = Join-Path $PSScriptRoot '.env'
if (-Not (Test-Path $envFile)) {
    Write-Host ".env file not found at $envFile. Copy .env.example to .env and edit it first." -ForegroundColor Yellow
    exit 1
}

# Read .env lines and set environment variables
Get-Content $envFile | ForEach-Object {
    $line = $_.Trim()
    if ($line -eq '' -or $line.StartsWith('#')) { return }
    $parts = $line -split '=',2
    if ($parts.Length -eq 2) {
        $name = $parts[0].Trim()
        $value = $parts[1]
        Write-Host "Setting env $name" -ForegroundColor Green
        Set-Item -Path "env:$name" -Value $value
    }
}

Write-Host "Starting application with environment variables from .env" -ForegroundColor Cyan
mvn spring-boot:run
