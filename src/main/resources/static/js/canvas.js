const canvas = document.getElementById("canvas");
const ctx = canvas.getContext("2d");
let playerImage = new Image();
let coinImage = new Image();
let doorImage = new Image();
let keyImage = new Image();

let room = JSON.parse(document.querySelector("#room").textContent);
renderRoom(room);

function collectCoin() {
    let coinForm = document.querySelector("#coin-form");
    let coinInput = document.querySelector("#coin-input");
    coinInput.value = room.id;
    coinForm.submit();
}

window.addEventListener("keydown", (e) => {
    if (e.key == "ArrowUp") {
        navigate("N");
    } else if (e.key == "ArrowDown") {
        navigate("S");
    } else if (e.key == "ArrowLeft") {
        navigate("W");
    } else if (e.key == "ArrowRight") {
        navigate("E");
    }
});


function renderRoom(room) {
    const roomName = document.querySelector("#room-name");

    drawWalls(ctx, room.walls);

    playerImage.src = "/images/sprites_man.png";


    playerImage.id = "player";
    playerImage.onload = () => {
        ctx.drawImage(playerImage, 0, 0, 60, 100, 265, 250, 60, 100);
    }

    if (room.coin != null) {
        placeCoin(ctx, room.coin);
    }

    if (room.key != null) {
        placeKey(ctx, room.key);
    }

    roomName.textContent = room.name;
}

function drawWalls(ctx, walls) {
    ctx.fillStyle = "black";

    ctx.fillRect(0, 0, 600, 20);
    ctx.fillRect(580, 0, 20, 600);
    ctx.fillRect(0, 580, 600, 20);
    ctx.fillRect(0, 0, 20, 580);

    walls.forEach(wall => {
        if (wall.door != null) {
            drawDoor(ctx, wall);
        }
    })
}

function drawDoor(ctx, wall) {
    ctx.fillStyle = wall.door.isOpen ? "white" : "red";
    if (wall.dir == "N") {
        ctx.fillRect(290, 0, 20, 20);
        canvas.addEventListener("click", (e) => {
            let cx = e.clientX - canvas.offsetLeft;
            let cy = e.clientY - canvas.offsetTop;
            if (cx > 290 && cx < 310 && cy < 20) {
                openDoor("N");
            }
        });
    } else if (wall.dir == "S") {
        ctx.fillRect(290, 580, 20, 20);
        canvas.addEventListener("click", (e) => {
            let cx = e.clientX - canvas.offsetLeft;
            let cy = e.clientY - canvas.offsetTop;
            if (cx > 290 && cx < 310 && cy > 580) {
                openDoor("S");
            }
        });
    } else if (wall.dir == "E") {
        ctx.fillRect(580, 290, 20, 20);
        canvas.addEventListener("click", (e) => {
            let cx = e.clientX - canvas.offsetLeft;
            let cy = e.clientY - canvas.offsetTop;
            if (cx > 580 && cy > 290 && cy < 310) {
                openDoor("E");
            }
        });
    } else if (wall.dir == "W") {
        ctx.fillRect(0, 290, 20, 20);
        canvas.addEventListener("click", (e) => {
            let cx = e.clientX - canvas.offsetLeft;
            let cy = e.clientY - canvas.offsetTop;
            if (cx < 20 && cy > 290 && cy < 310) {
                openDoor("W");
            }
        });
    }

}

function placeKey(ctx, key) {
    ctx.fillStyle = "yellow";
    keyImage.src = "/images/key.webp";
    keyImage.id = "key";
    keyImage.onload = () => {
        ctx.drawImage(keyImage, key.cx - 20, key.cy - 20, 40, 40);
    }

    canvas.addEventListener("click", (e) => {
        let cx = e.clientX - canvas.offsetLeft;
        let cy = e.clientY - canvas.offsetTop;
        if (Math.abs(cx - key.cx) < 20 && Math.abs(cy - key.cy) < 20) {
            collectKey();
        }
    });
}

function placeCoin(ctx, coin) {
    ctx.fillStyle = "yellow";
    coinImage.src = "/images/coin.gif";
    coinImage.id = "coin";
    coinImage.onload = () => {
        ctx.drawImage(coinImage, coin.cx - 20, coin.cy - 20, 40, 40);
    }
  
    canvas.addEventListener("click", (e) => {
        let cx = e.clientX - canvas.offsetLeft;
        let cy = e.clientY - canvas.offsetTop;
        if (Math.abs(cx - room.coin.cx) < 20 && Math.abs(cy - room.coin.cy) < 20) {
            collectCoin();
        }
    });
}

function navigate(dir) {
    let navigateForm = document.querySelector("#nav-form");
    let navDirection = document.querySelector("#nav-direction");
    navDirection.value = dir;
    navigateForm.submit();
}

function collectKey() {
    let keyForm = document.querySelector("#key-form");
    let keyInput = document.querySelector("#key-input");
    keyInput.value = room.id;
    keyForm.submit();
}

function openDoor(direction) {
    let doorForm = document.querySelector("#door-form");
    let doorInput = document.querySelector("#door-direction");
    doorInput.value = direction;
    doorForm.submit();
}