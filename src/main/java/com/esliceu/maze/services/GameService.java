package com.esliceu.maze.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.time.Duration;

import com.google.gson.Gson;

import com.esliceu.maze.models.Map;
import com.esliceu.maze.models.Wall;
import com.esliceu.maze.models.Door;
import com.esliceu.maze.models.Game;
import com.esliceu.maze.models.Room;
import com.esliceu.maze.models.Score;
import com.esliceu.maze.models.Key;
import com.esliceu.maze.repositorys.GameRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.esliceu.maze.exceptions.NoCoinException;

@Service
public class GameService {

    @Autowired
    private UserService userService;

    @Autowired
    private GameRepository GameRepository;

    @Autowired
    private Gson gson;

    public List<Map> getMaps() {
        return GameRepository.getMaps();
    }
    
    public Map getMap(Long mapId) {
        return GameRepository.getMap(mapId);
    }

    public void createGame(Long userId, Long roomId, Long mapId) {
        try {
            GameRepository.getGame(userId, mapId);
        } catch (Exception e) {
            GameRepository.createGame(userId, roomId, mapId);
        }
    }

    public Game getGame(Long userId, Long mapId) {
        return GameRepository.getGame(userId, mapId);
    }

    public List<Game> getGames(Long userId) {
        return GameRepository.getGames(userId);
    }

    public List<Wall> getWalls(Long roomId) {
        return GameRepository.getWalls(roomId);
    }

    public Door getDoor(Long doorId) {
        return GameRepository.getDoor(doorId);
    }

    public Room getRoom(Long roomId, Long userId, Long mapId) {
        Long gameId = GameRepository.getGame(userId, mapId).getId();
        Room room = GameRepository.getRoom(roomId);
        if (!coinIsCollected(roomId, gameId) && room.hasCoin()) {
            room.setCoin(GameRepository.getCoin(roomId));
        }
        if (!keyIsCollected(roomId, gameId) && room.hasKey()) {
            room.setKey(GameRepository.getRoomKey(roomId));
        }

        room.setWalls(getWalls(roomId));

        for (Wall wall : room.getWalls()) {
            if (wall.getDoorId() != null) {
                wall.setDoor(getDoor(wall.getDoorId()));
                if (!doorIsOpen(wall.getDoorId(), gameId) && !wall.getDoor().getIsOpen()) {
                    wall.getDoor().setIsOpen(false);
                } else {
                    wall.getDoor().setIsOpen(true);
                }
            }
        }
        return room;
    }

    public String getRoomJson(Long roomId, Long userId, Long mapId) {
        return gson.toJson(getRoom(roomId, userId, mapId));
    }

    public void updateCurrentRoom(Long userId, Long roomId, Long mapId) {
        GameRepository.updateCurrentRoom(userId, roomId, mapId);
    }

    public void navigate(String dir, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Long mapId = (Long) session.getAttribute("mapId");
        Long roomId = getGame(userService.getUserIdByUsername(req), mapId).getCurrentRoomId();
        Long doorId = GameRepository.getWall(roomId, dir).getDoorId();
        if (doorId == null) {
            throw new RuntimeException("No door in this direction");
        }
        if (!doorIsOpen(doorId, GameRepository.getGame(userService.getUserIdByUsername(req), mapId).getId()) && !getDoor(doorId).getIsOpen()) {
            throw new RuntimeException("Door is locked");
        }
        Long nextRoomId = GameRepository.getAdjacentRoomId(doorId, roomId);
        updateCurrentRoom(userService.getUserIdByUsername(req), nextRoomId, mapId);
    }

    public boolean isEnd(String dir, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Long mapId = (Long) session.getAttribute("mapId");
        Game game = getGame(userService.getUserIdByUsername(req), mapId);
        Long roomId = game.getCurrentRoomId();
        Long doorId = GameRepository.getWall(roomId, dir).getDoorId();
        if (doorId == null) {
            return false;
        }
        if (!doorIsOpen(doorId, game.getId()) && !getDoor(doorId).getIsOpen()) {
            return false;
        }
        Map map = GameRepository.getMap(game.getMapId());
        return map.getFinalRoomId() == GameRepository.getAdjacentRoomId(doorId, roomId);
    }

    public void collectCoin(Long roomId, Long userId, Long mapId) throws NoCoinException {
        Long gameId = GameRepository.getGame(userId, mapId).getId();
        if (roomId != getGame(userId, mapId).getCurrentRoomId()) {
            throw new RuntimeException("You are not in the room");
        }
        if (coinIsCollected(roomId, gameId)) {
            throw new NoCoinException("Coin already collected");
        }
        try {
            Long coinId = GameRepository.getCoin(roomId).getId();
            GameRepository.collectCoin(gameId, coinId);
        } catch (Exception e) {
            throw new RuntimeException("No coin in this room");
        }
    }

    public List<String> getGameKeys(Long userId, Long mapId) {
        List<Key> keys = GameRepository.getGameKeys(getGame(userId, mapId).getId());
        return keys.stream().map(Key::getName).toList();
    }

    public void collectKey(Long roomId, Long userId, Long mapId) {
        if (roomId != getGame(userId, mapId).getCurrentRoomId()) {
            throw new RuntimeException("You are not in the room");
        }
        try {
            GameRepository.getRoomKey(roomId);
        } catch (Exception e) {
            throw new RuntimeException("No key in this room");
        }
        Key key = GameRepository.getRoomKey(roomId);
        Game game = GameRepository.getGame(userId, mapId);

        if (keyIsCollected(roomId, game.getId())) {
            throw new RuntimeException("Key already collected");
        }
        if (key.getValue() > game.getCoins()) {
            throw new RuntimeException("Not enough coins to buy key");
        }
        GameRepository.collectKey(game.getId(), key.getId());
    }

    public void resetGame(Long userId, Long mapId) {
        try {
            Game game = GameRepository.getGame(userId, mapId);
            Map map = GameRepository.getMap(game.getMapId());
            GameRepository.resetGame(userId, map.getInitialRoomId(), map.getId(), game.getId());
        } catch (Exception e) {
            throw new RuntimeException("No game to reset");
        }
    }

    public void deleteGame(Long gameId) {
        GameRepository.deleteGame(gameId);
    }

    private boolean keyIsCollected(Long roomId, Long ongoingGameId) {
        try {
            Long keyId = GameRepository.getRoomKey(roomId).getId();
            if (GameRepository.keyIsCollected(keyId, ongoingGameId)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean coinIsCollected(Long roomId, Long ongoingGameId) {
        try {
            Long coinId = GameRepository.getCoin(roomId).getId();
            if (GameRepository.coinIsCollected(coinId, ongoingGameId)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void openDoor(String dir, Long userId, Long mapId) {
        Long currentRoomId = getGame(userId, mapId).getCurrentRoomId();
        Wall wall = GameRepository.getWall(currentRoomId, dir);
        if (wall.getDoorId() == null) {
            throw new RuntimeException("No door in this direction");
        }
        Door door = GameRepository.getDoor(wall.getDoorId());
        Long gameId = getGame(userId, mapId).getId();
        if (door.getIsOpen() || doorIsOpen(door.getId(), gameId)) {
            throw new RuntimeException("Door is already open");
        }
        boolean hasKey = false;
        List<Key> keys = GameRepository.getGameKeys(gameId);
        for (Key key : keys) {
            if (key.getId() == door.getKeyId()) {
                GameRepository.openDoor(door.getId(), gameId);
                hasKey = true;
            }
        }
        if (!hasKey) {
            throw new RuntimeException("You need the " + GameRepository.getKeyById(door.getKeyId()).getName() + " to open this door");
        }
    }

    public boolean doorIsOpen(Long doorId, Long gameId) {
        return GameRepository.doorIsOpen(doorId, gameId);
    }

    public void saveGame(Long userId, Long mapId) {
        Long roomId = getGame(userId, mapId).getCurrentRoomId();
        LocalDateTime endDate = LocalDateTime.now();
        endDate = endDate.minusHours(1);
        GameRepository.saveGame(userId, roomId, endDate, mapId);
    }

    public List<Score> getLeaderBoard() {
        return GameRepository.getLeaderBoard();
    }

    public void sortScores(List<Score> leaderboard) {
            

        leaderboard.sort((a, b) -> {
            Duration timeA = Duration.between(a.getStartDate(), a.getEndDate());
            Duration timeB = Duration.between(b.getStartDate(), b.getEndDate());
            return timeA.compareTo(timeB);
        });

        for (int i = 0; i < leaderboard.size(); i++) {
            leaderboard.get(i).setPosition(i + 1L);
        }
    }

    public void formatTime(Score score) {
        LocalDateTime endDate = score.getEndDate();
        LocalDateTime startDate = score.getStartDate();
        Duration timeDiff = Duration.between(startDate, endDate);
        long days = timeDiff.toDays() % 365;
        long hours = timeDiff.toHours() % 24;
        long minutes = timeDiff.toMinutes() % 60;
        long seconds = timeDiff.getSeconds() % 60;
        String time = "";
        if (days > 0) {
            time += days + "d ";
        }
        if (hours > 0) {
            time += hours + "h ";
        }
        if (minutes > 0) {
            time += minutes + "m ";
        }
        if (seconds > 0) {
            time += seconds + "s";
        }
        score.setTime(time);
    }

    public List<Score> getLeaderBoardByMap(Long mapId) {
        return GameRepository.getLeaderBoardByMap(mapId);
    }
}
