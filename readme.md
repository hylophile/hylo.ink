# Deploy
```sh
$ rclone copy -LP . mve:web --exclude '.*{/**,}'
```

# Dev setup
```json
[
  [
    {
      "cwd": "/home/n/code/misc/hyloink",
      "prog": "npx tailwindcss -i ./input.css -o ./web/tailwind.css --watch"
    }
  ],
  [{ "cwd": "/home/n/code/misc/hyloink", "prog": "bb run nrepl" }],
  [
    {
      "cwd": "/home/n/code/misc/hyloink",
      "prog": "ls hyloink.clj | entr bb hyloink.clj"
    }
  ],
  [{ "cwd": "/home/n/code/misc/hyloink", "prog": "caddy run" }],
  [
    {
      "cwd": "/home/n/code/misc/hyloink",
      "prog": "./picfit/bin/picfit -c picfit-config.json "
    }
  ],
  [
    {
      "cwd": "/home/n/code/misc/hyloink",
      "prog": "websocat -t ws-l:127.0.0.1:9999 broadcast:mirror: -E"
    }
  ],
  [
    {
      "cwd": "/home/n/code/misc/hyloink",
      "prog": "ls web/index.html | entr -s 'echo hi | websocat ws://127.0.0.1:9999'"
    }
  ],
  [
    {
      "cwd": "/home/n/code/misc/hyloink",
      "prog": "ls Caddyfile | entr -s 'caddy reload'"
    }
  ],
  [{ "cwd": "/home/n/code/misc/hyloink" }]
]
```

