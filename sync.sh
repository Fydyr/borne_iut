#!/bin/bash

sudo date -s "$(wget -qSO- --no-check-certificate --max-redirects=0 https://www.google.com/ 2>&1 | grep Date: | cut -d' ' -f5-8)Z"

echo "Time synchronized with Google server."

wait 5