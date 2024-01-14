#!/usr/bin/env bash

folder="web/art/1080p/"
mkdir -p "$folder"
for file in export/*.png; do
    filename=$(basename "$file")
    new_filename=$(echo "$filename" | sed 's/kra/hylo.fullhd/g')

    destination="${folder}/${new_filename}"

    if [ ! -e "$destination" ]; then
        echo "Converting ${filename} to ${new_filename} ..."
        convert "$file" -resize 1920x "$destination"
    fi
done

folder="web/art/thumbnail/"
mkdir -p "$folder"
for file in export/*.png; do
    filename=$(basename "$file")
    new_filename=$(echo "$filename" | sed 's/kra/thumbnail/g; s/\.png$/.jpg/g')

    destination="${folder}/${new_filename}"

    if [ ! -e "$destination" ]; then
        echo "Converting ${filename} to ${new_filename} ..."
        convert "$file" -resize 404x "$destination"
    fi
done
