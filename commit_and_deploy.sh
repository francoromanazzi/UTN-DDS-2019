#!/usr/bin/env bash


git add .

echo "Descripcion del commit:"
read -i -e MESSAGE

git commit -m"$MESSAGE"
git push