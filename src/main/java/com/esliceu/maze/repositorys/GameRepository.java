package com.esliceu.maze.repositorys;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

import com.esliceu.maze.models.Map;
import com.esliceu.maze.models.Door;
import com.esliceu.maze.models.Game;
import com.esliceu.maze.models.Wall;
import com.esliceu.maze.models.Room;
import com.esliceu.maze.models.Score;
import com.esliceu.maze.models.Coin;
import com.esliceu.maze.models.Key;


@Repository
public class GameRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map> getMaps() {
        return jdbcTemplate.query("SELECT * FROM maps", BeanPropertyRowMapper.newInstance(Map.class));
    }

    public Map getMap(Long mapId) {
        return jdbcTemplate.queryForObject("SELECT * FROM maps WHERE id = ?", BeanPropertyRowMapper.newInstance(Map.class), mapId);
    }

    public void createGame(Long userId, Long roomId, Long mapId) {
        jdbcTemplate.update("INSERT INTO games (userId, currentRoomId, mapId) VALUES (?, ?, ?)", userId, roomId, mapId);
    }

    public Game getGame(Long userId, Long mapId) {
        return jdbcTemplate.queryForObject("SELECT * FROM games WHERE userId = ? AND mapId = ? AND endDate IS NULL", BeanPropertyRowMapper.newInstance(Game.class), userId, mapId);
    }

    public List<Game> getGames(Long userId) {
        return jdbcTemplate.query("SELECT * FROM games WHERE userId = ? AND endDate IS NULL", BeanPropertyRowMapper.newInstance(Game.class), userId);
    }

    public void deleteGame(Long gameId) {
        jdbcTemplate.update("DELETE FROM games WHERE id = ?", gameId);
    }

    public Room getRoom(Long roomId) {
        return jdbcTemplate.queryForObject("SELECT * FROM rooms WHERE id = ?", BeanPropertyRowMapper.newInstance(Room.class), roomId);
    }

    public Long getAdjacentRoomId(Long doorId, Long roomId) {
        return jdbcTemplate.queryForObject("SELECT roomId FROM walls WHERE doorId = ? AND roomId != ?", Long.class, doorId, roomId);
    }

    public void updateCurrentRoom(Long userId, Long roomId, Long mapId) {
        jdbcTemplate.update("UPDATE games SET currentRoomId = ? WHERE userId = ? AND mapId = ?", roomId, userId, mapId);
    }

    public Wall getWall(Long roomId, String dir) {
        return jdbcTemplate.queryForObject("SELECT * FROM walls WHERE roomId = ? AND dir = ?", BeanPropertyRowMapper.newInstance(Wall.class), roomId, dir);
    }

    public List<Wall> getWalls(Long roomId) {
        return jdbcTemplate.query("SELECT * FROM walls WHERE roomId = ?", BeanPropertyRowMapper.newInstance(Wall.class), roomId);
    }

    public Door getDoor(Long doorId) {
        return jdbcTemplate.queryForObject("SELECT * FROM doors WHERE id = ?", BeanPropertyRowMapper.newInstance(Door.class), doorId);
    }

    public Coin getCoin(Long roomId) {
        return jdbcTemplate.queryForObject("SELECT * FROM coins WHERE roomId = ?", BeanPropertyRowMapper.newInstance(Coin.class), roomId);
    }

    public Key getRoomKey(Long roomId) {
        return jdbcTemplate.queryForObject("SELECT * FROM `keys` WHERE roomId = ?", BeanPropertyRowMapper.newInstance(Key.class), roomId);
    }

    public Key getKeyById(Long keyId) {
        return jdbcTemplate.queryForObject("SELECT * FROM `keys` WHERE id = ?", BeanPropertyRowMapper.newInstance(Key.class), keyId);
    }

    public void collectCoin(Long gameId, Long coinId) {
        jdbcTemplate.update("UPDATE games SET coins = coins + 1 WHERE id = ?", gameId);
        jdbcTemplate.update("INSERT INTO games_coins (coinId, gamesId) VALUES (?, ?)", coinId, gameId);
    }

    public void collectKey(Long ongoingGameId, Long keyId) {
        int keyValue = jdbcTemplate.queryForObject("SELECT value FROM `keys` WHERE id = ?", Integer.class, keyId);
        jdbcTemplate.update("INSERT INTO games_keys (keyId, gamesId) VALUES (?, ?)", keyId, ongoingGameId);
        jdbcTemplate.update("UPDATE games SET coins = coins - ? WHERE id = ?", keyValue, ongoingGameId);
    }

    public void resetGame(Long userId, Long roomId, Long mapId, Long gameId) {
        jdbcTemplate.update("DELETE FROM games WHERE userId = ? AND id = ?", userId, gameId);
        createGame(userId, roomId, mapId);
    }

    public boolean keyIsCollected(Long keyId, Long ongoingGameId) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM games_keys WHERE keyId = ? AND gamesId = ?", Integer.class, keyId, ongoingGameId) > 0;
    }

    public boolean coinIsCollected(Long coinId, Long ongoingGameId) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM games_coins WHERE coinId = ? AND gamesId = ?", Integer.class, coinId, ongoingGameId) > 0;
    }

    public boolean doorIsOpen(Long doorId, Long gameId) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM games_doors WHERE doorId = ? AND gamesId = ?", Integer.class, doorId, gameId) > 0;
    }

    public void openDoor(Long doorId, Long gameId) {
        jdbcTemplate.update("INSERT INTO games_doors (doorId, gamesId) VALUES (?, ?)", doorId, gameId);
    }

    public List<Key> getGameKeys(Long gameId) {
        return jdbcTemplate.query("SELECT keyId AS id, name FROM `games_keys` INNER JOIN `keys` ON `keys`.id = `games_keys`.keyId WHERE gamesId = ?", BeanPropertyRowMapper.newInstance(Key.class), gameId);
    }

    public void saveGame(Long userId, Long roomId, String endDate, Long mapId) {
        jdbcTemplate.update("UPDATE games SET currentRoomId = ?, endDate = ? WHERE userId = ? AND endDate IS NULL AND mapId = ?", roomId, endDate, userId, mapId);
    }

    public List<Score> getLeaderBoard() {
        return jdbcTemplate.query("SELECT mapId, startDate, endDate, users.username AS username FROM games INNER JOIN users ON users.Id = games.userId WHERE endDate IS NOT NULL", BeanPropertyRowMapper.newInstance(Score.class));
    }

    public List<Score> getLeaderBoardByMap(Long mapId) {
        return jdbcTemplate.query("SELECT mapId, startDate, endDate, users.username AS username FROM games INNER JOIN users ON users.Id = games.userId WHERE endDate IS NOT NULL AND mapId = ?", BeanPropertyRowMapper.newInstance(Score.class), mapId);
    }
}
