package com.esliceu.maze.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;

import com.esliceu.maze.services.GameService;
import com.esliceu.maze.services.UserService;

import com.esliceu.maze.models.Game;
import com.esliceu.maze.models.Map;
import com.esliceu.maze.models.Score;

import java.util.List;

@Controller
public class GameController {
    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @GetMapping("/start")
    public String getStartMenu(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute("mapId");
        List<Game> games = gameService.getGames(userService.getUserIdByUsername(req));
        List<Map> maps = gameService.getMaps();
        model.addAttribute("games", games);
        model.addAttribute("maps", maps);
        return "start";
    }

    @PostMapping("/start")
    public String startGame(@RequestParam Long mapId, Model model, HttpServletRequest req) {
        try {
            Map map = gameService.getMap(mapId);
            HttpSession session = req.getSession();
            Long userId = userService.getUserIdByUsername(req);
            session.setAttribute("mapId", mapId);
            gameService.createGame(userId, map.getInitialRoomId(), map.getId());
            Game game = gameService.getGame(userId, mapId);
            String roomJson = gameService.getRoomJson(game.getCurrentRoomId(), userId, map.getId());
            model.addAttribute("keys", gameService.getGameKeys(userId, mapId));
            model.addAttribute("coinBalance", game.getCoins());
            model.addAttribute("room", roomJson);
            return "game";
        } catch (Exception e) {
            return "redirect:/start";
        }
    }

    @PostMapping("/delete")
    public String deleteGame(@RequestParam Long gameId, Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        gameService.deleteGame(gameId);
        session.removeAttribute("mapId");
        return "redirect:/start";
    }
    
    @GetMapping("/reset")
    public String resetGame(HttpServletRequest req, Model model) {
        Long userId = userService.getUserIdByUsername(req);
        Long mapId = (Long) req.getSession().getAttribute("mapId");
        try {
            gameService.resetGame(userId, mapId);
        } catch (Exception e) {
            return "redirect:/start";
        }
        Game game = gameService.getGame(userId, mapId);
        Map map = gameService.getMap(game.getMapId());
        String roomJson = gameService.getRoomJson(game.getCurrentRoomId(), userId, map.getId());
        model.addAttribute("room", roomJson);
        model.addAttribute("coinBalance", 0);
        return "game";
    }

    @GetMapping("/getcoin")
    public String collectCoin(@RequestParam Long roomId, Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Long mapId = (Long) session.getAttribute("mapId");
        Long userId = userService.getUserIdByUsername(req);
        try {
            gameService.collectCoin(roomId, userId, mapId);
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
        }
        Game game = gameService.getGame(userId, mapId);
        int coinBalance = gameService.getGame(userId, mapId).getCoins();
        String roomJson = gameService.getRoomJson(game.getCurrentRoomId(), userId, mapId);
        model.addAttribute("keys", gameService.getGameKeys(userId, mapId));
        model.addAttribute("room", roomJson);
        model.addAttribute("coinBalance", coinBalance);
        return "game";
    }

    @GetMapping("/getkey")
    public String collectKey(@RequestParam Long roomId, Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Long mapId = (Long) session.getAttribute("mapId");
        Long userId = userService.getUserIdByUsername(req);
        Game game = gameService.getGame(userId, mapId);
        try {
            gameService.collectKey(roomId, userId, mapId);
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
        }
        String roomJson = gameService.getRoomJson(game.getCurrentRoomId(), userId, mapId);
        model.addAttribute("keys", gameService.getGameKeys(userId, mapId));
        model.addAttribute("room", roomJson);
        model.addAttribute("coinBalance", game.getCoins());
        return "game";
    }

    @GetMapping("/nav")
    public String navigate(@RequestParam String dir, Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Long mapId = (Long) session.getAttribute("mapId");
        Long userId = userService.getUserIdByUsername(req);
        try {
            if (gameService.isEnd(dir, req)) {
                gameService.saveGame(userId, mapId);
                return "redirect:/scores";
            }
            gameService.navigate(dir, req);
            Game game = gameService.getGame(userId, mapId);
            String roomJson = gameService.getRoomJson(game.getCurrentRoomId(), userId, mapId);
            model.addAttribute("room", roomJson);
        } catch (Exception e) {
            model.addAttribute("room", gameService.getRoomJson(gameService.getGame(userId, mapId).getCurrentRoomId(), userId, mapId));
            model.addAttribute("errorMsg", e.getMessage());
        }
        model.addAttribute("keys", gameService.getGameKeys(userId, mapId));
        model.addAttribute("coinBalance", gameService.getGame(userId, mapId).getCoins());
        return "game";
    }

    @GetMapping("/open") 
    public String openDoor(@RequestParam String dir, Model model, HttpServletRequest req) {
        Long userId = userService.getUserIdByUsername(req);
        Long mapId = (Long) req.getSession().getAttribute("mapId");
        try {
            gameService.openDoor(dir, userId, mapId);
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
        }
        Game game = gameService.getGame(userId, (Long) req.getSession().getAttribute("mapId"));
        String roomJson = gameService.getRoomJson(game.getCurrentRoomId(), userId, game.getMapId());
        model.addAttribute("keys", gameService.getGameKeys(userId, mapId));
        model.addAttribute("room", roomJson);
        model.addAttribute("coinBalance", game.getCoins());
        return "game";
    }

    @GetMapping("/scores")
    public String getScores(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute("mapId");
        List<Score> leaderboard = gameService.getLeaderBoard();
        gameService.sortScores(leaderboard);
        for (Score score : leaderboard) {
            score.setPosition(leaderboard.indexOf(score) + 1L);
            gameService.formatTime(score);
        }
        model.addAttribute("scores", leaderboard);
        return "scores";
    }

    @PostMapping("/scores")
    public String filterScores(@RequestParam Long mapId, Model model) {
        try {
            if (mapId == 0) {
                return "redirect:/scores";
            }
            List<Score> leaderboard = gameService.getLeaderBoardByMap(mapId);
            gameService.sortScores(leaderboard);
            for (Score score : leaderboard) {
                score.setPosition(leaderboard.indexOf(score) + 1L);
                gameService.formatTime(score);
            }
            model.addAttribute("scores", leaderboard);
            return "scores";
        } catch (Exception e) {
            return "redirect:/scores";
        }
    }
}
