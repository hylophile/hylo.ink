const connect = () => {
  const socket = new WebSocket(`ws://localhost:9999`);

  socket.addEventListener("message", () => {
    location.reload();
  });
};

connect();
