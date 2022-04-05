package com.erasko.utils;

import com.fasterxml.jackson.core.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;

import static com.fasterxml.jackson.core.JsonToken.VALUE_STRING;

public class JSONStreamingAPIGameLogger extends GameLogger {

    // Метод записывает в json-файл
    @Override
    protected void saveDataInFile() {

        date = new Date();
        String dateSuffix = dateFormat.format(date);
        // Собираем имя файла, добавили временное значение
        String jsonFile = firstPartOfFile + dateSuffix + ".json";
        currentNewRecordedFile = jsonFile;

        JsonFactory jsonFactory = new JsonFactory();

        try {
            FileWriter writer = new FileWriter(new File(jsonFile));
            JsonGenerator jsonGenerator = jsonFactory.createGenerator(writer);
            jsonGenerator.writeStartObject();
            jsonGenerator.writeFieldName("gameplay");

            jsonGenerator.writeStartObject();

            jsonGenerator.writeFieldName("players");
            jsonGenerator.writeStartArray();
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("id", "1");
            jsonGenerator.writeStringField("name", allData.get(0));
            jsonGenerator.writeStringField("symbol", "X");
            jsonGenerator.writeEndObject();

            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("id", "2");
            jsonGenerator.writeStringField("name", allData.get(1));
            jsonGenerator.writeStringField("symbol", "О");
            jsonGenerator.writeEndObject();
            jsonGenerator.writeEndArray();

            jsonGenerator.writeFieldName("game");
            jsonGenerator.writeStartObject();
            jsonGenerator.writeFieldName("step");
            jsonGenerator.writeStartArray();
            for (int i = 2, k = allData.size()-1; i < k; i++) {
                String[] data = allData.get(i).split(" ");
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("num", data[0]);
                jsonGenerator.writeStringField("playerId", data[1]);
                jsonGenerator.writeStringField("coors", data[2]);
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject(); // закрыли game

            jsonGenerator.writeFieldName("gameResult");

            String result = allData.get(allData.size()-1);
            System.out.println("--- ---- ----  ----");
            System.out.println(result);
            String[] data = result.split(" ");
            if (!result.equals("Draw!")) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeFieldName("player");
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("id", data[1]);
                jsonGenerator.writeStringField("name", data[0]);
                jsonGenerator.writeStringField("symbol", data[2]);
                jsonGenerator.writeEndObject();
                jsonGenerator.writeEndObject();
            } else {
                jsonGenerator.writeNull();
            }

            jsonGenerator.writeEndObject();
            jsonGenerator.writeEndObject();
            jsonGenerator.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void readFile(String fileName) {
        //Вспомогательный набор, хранит распарсенную информацию
        helpField = new int[3][3];
        helpPayerList = new ArrayList<>(2);
        playerStepArray = new ArrayList<>(9);
        winnerOrDraw = new StringBuilder();

        try (JsonParser jParser = new JsonFactory()
                .createParser(new File(fileName))) {
            jParser.nextToken();

            while(jParser.hasCurrentToken()) {
                jParser.nextToken();

                String fieldName = jParser.getCurrentName();

                if ("players".equals(fieldName)) {
                    String[] helpS = new String[6];
                    int i = 0;
                    // если текущий токен "player",
                    // переходим к следующему токену, проверяем, начинается ли массив
                    if (jParser.nextToken() == JsonToken.START_ARRAY) {

                        while (jParser.nextToken() != JsonToken.END_ARRAY) {
                            String tokenName = jParser.getCurrentName();
                            if ("id".equals(tokenName)) {
                                jParser.nextToken();
                                helpS[i] = jParser.getText();
                                i++;
                            }
                            if ("name".equals(tokenName)) {
                                jParser.nextToken();
                                helpS[i] = jParser.getText();
                                i++;
                            }
                            if ("symbol".equals(tokenName)) {
                                jParser.nextToken();
                                helpS[i] = jParser.getText();
                                i++;
                            }
                        }
                        helpPayerList.add(helpS[0] + " игрок: " + helpS[1] + ", ходит " + helpS[2]);
                        helpPayerList.add(helpS[3] + " игрок: " + helpS[4] + ", ходит " + helpS[5]);
                    }
                }
                if ("step".equals(fieldName)) {
                    if (jParser.nextToken() == JsonToken.START_ARRAY) {

                        ArrayList<String> helpS = new ArrayList<>(18);
                        while (jParser.nextToken() != JsonToken.END_ARRAY){
                            String fName = jParser.getCurrentName();
                            if ("playerId".equals(fName)) {
                                jParser.nextToken();
                                helpS.add(jParser.getText());
                            }
                            if ("coors".equals(fName)) {
                                jParser.nextToken();
                                helpS.add(jParser.getText());
                            }
                        }
                        for (int i = 0; i < helpS.size(); i = i+2) {
                            writeCoordsInHelpField(helpS.get(i+1), Integer.parseInt(helpS.get(i)));
                        }
                    }
                }
                if ("gameResult".equals(fieldName)) {
                    if (jParser.nextToken() == JsonToken.START_OBJECT) {
                        jParser.nextToken();
                        if ("player".equals(jParser.getCurrentName())) {
                            StringBuilder str = new StringBuilder();
                            JsonToken jsonToken = jParser.nextToken();
                            while(jParser.hasCurrentToken()) {
                                if(jsonToken == VALUE_STRING) {
                                    str.append(jParser.getText()).append(" ");
                                }
                                jsonToken = jParser.nextToken();
                            }
                            String[] helpStr = str.toString().split(" ");
                            winnerOrDraw.append("Player ")
                                    .append(helpStr[0])
                                    .append(" -> ")
                                    .append(helpStr[1])
                                    .append(" is winner as '")
                                    .append(helpStr[2])
                                    .append("'!");
                        }
                    } else {
                        winnerOrDraw.append("Draw!");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
