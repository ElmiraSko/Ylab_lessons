package com.erasko.lns2.loggers;

import com.erasko.lns2.DTO.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JSONGameLogger extends GameLogger {

    ObjectMapper objectMapper = new ObjectMapper();

    // Метод записывает в json-файл
    @Override
    protected void saveDataInFile() {
        date = new Date();
        String dateSuffix = dateFormat.format(date);
        // Собираем имя файла, добавили временное значение
        String jsonFile = firstPartOfFile + dateSuffix + ".json";
        currentNewRecordedFile = jsonFile;

        ArrayList<PlayerDTO> players = new ArrayList<>(2);
        players.add(new PlayerDTO( "1", allData.get(0), "X"));
        players.add(new PlayerDTO( "2", allData.get(1), "O"));

        GameResultDTO resultDTO = new GameResultDTO();
        ArrayList<StepDTO> playerStepDTOS = new ArrayList<>(9);
        for (int i = 2, k = allData.size()-1; i < k; i++) {
            String[] data = allData.get(i).split(" ");
            playerStepDTOS.add(new StepDTO(data[0], data[1], Integer.parseInt(data[2])));
        }
        String result = allData.get(allData.size()-1);
        String[] data = result.split(" ");
        if (!result.equals("Draw!")) {
            PlayerDTO playerDTO = new PlayerDTO(data[1], data[0], data[2]);
            resultDTO.setPlayer(playerDTO);
        }

        GamePlayDTO gamePlayDTO = new GamePlayDTO(players, new GameDTO(playerStepDTOS), resultDTO);
        GameProcessDTO processDTO = new GameProcessDTO(gamePlayDTO);

        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(new File(jsonFile), processDTO);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    // Метод для чтения json-файла
    @Override
    public void readFile(String fileName) {
        //Вспомогательный набор, хранит распарсенную информацию
        helpField = new int[3][3];
        helpPayerList = new ArrayList<>(2);
        playerStepArray = new ArrayList<>(9);
        winnerOrDraw = new StringBuilder();
        try {
            GameProcessDTO gameProcessDTO = objectMapper.readValue(new File(fileName), GameProcessDTO.class);
            GamePlayDTO playDTO = gameProcessDTO.getGameplay();
            GameDTO game = playDTO.getGame();
            GameResultDTO resultDTO = playDTO.getGameResult();

            // Прочитали имена играков
            ArrayList<PlayerDTO> playerDTOS = playDTO.getPlayers();
            helpPayerList.add(playerDTOS.get(0).getId() + " игрок: " +
                    playerDTOS.get(0).getName() + ", ходит " + playerDTOS.get(0).getSymbol());
            helpPayerList.add(playerDTOS.get(1).getId() + " игрок: " +
                    playerDTOS.get(1).getName() + ", ходит " + playerDTOS.get(1).getSymbol());

            // подготовили последовательность ходов на поле
            for (StepDTO stp: game.getSteps()) {
                writeCoordsInHelpField(String.valueOf(stp.getCoors()), Integer.parseInt(stp.getPlayerId()));
            }
            // Прочитали результат
            PlayerDTO playerDTO = resultDTO.getPlayer();
            if (playerDTO != null) {
                winnerOrDraw.append("Player ")
                        .append(playerDTO.getId())
                        .append(" -> ")
                        .append(playerDTO.getName())
                        .append(" is winner as '")
                        .append(playerDTO.getSymbol())
                        .append("'!");
            } else {
                winnerOrDraw.append("Draw!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
